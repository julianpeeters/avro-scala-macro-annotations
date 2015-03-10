package com.julianpeeters.avro.annotations

import matchers.DefaultParamMatcher
import util.{ClassFieldStore, SchemaStore}

import scala.reflect.macros.Context

import scala.language.experimental.macros
import scala.annotation.StaticAnnotation

import org.apache.avro.util.Utf8
import org.apache.avro.Schema
import org.apache.avro.Schema.Field

import java.util.concurrent.ConcurrentHashMap

import org.apache.avro.Schema.{Type => AvroType}

import java.util.{Arrays => JArrays}


object AvroRecordMacro {

  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    
    import c.universe._
    import Flag._

    case class FieldData(nme: TermName, tpt: Tree, idx: Int)//holds info about the fields of the annotee

    //Extender
    def generateNewBaseTypes =  List( tq"org.apache.avro.specific.SpecificRecordBase")

    //CtorGen
    def generateNewCtors(indexedFields: List[FieldData]) = {
      def asCtorParam(typeName: String, c: Context): c.universe.Tree = {
        if (typeName.endsWith("]")) DefaultParamMatcher.asParameterizedDefaultParam(typeName, c)
        else  DefaultParamMatcher.asDefaultParam(typeName, c)
      }
      val defaultParams = indexedFields.map(field => asCtorParam(field.tpt.toString, c)) //toString to reuse DefaultParamMatcher
      val newCtorDef = q"""def this() = this(..$defaultParams)"""
      val defaultCtorPos = c.enclosingPosition //thanks to Eugene Burmako for the workaround to position the ctor correctly
      val newCtorPos = defaultCtorPos
        .withEnd(defaultCtorPos.endOrPoint + 1)
        .withStart(defaultCtorPos.startOrPoint + 1)
        .withPoint(defaultCtorPos.point + 1)
      List( atPos(newCtorPos)(newCtorDef) ) //Return a list of new CtorDefs
    }

    //NamespaceGen                                    
    val freshName = c.fresh(newTypeName("Probe$")) 
    val probe = c.typeCheck(q""" {class $freshName; ()} """)  // Thanks again to Eugene Burmako 
    val freshSymbol = probe match {
      case Block(List(t), r) => t.symbol
    }
    val fullFreshName = freshSymbol.fullName.toString
    val namespace = c.prefix.tree match { 
      case Apply(_, List(Literal(Constant(x)))) => null //if there's an arg, force the omission of a namespace in the schema
      case _ => {      
        if (fullFreshName.contains('.')) { fullFreshName.replace("." + freshName.toString, "")} //strips dot and class name
        else null
      }
    }

    //SchemaGen - generates schemas and stores them
    def generateSchema(className: String, namespace: String, first: List[c.universe.ValDef]): Schema = {  
      //map is from https://github.com/radlab/avro-scala-compiler-plugin/blob/master/src/main/scala/plugin/SchemaGen.scala
      val primitiveClasses: Map[Type, Schema] = Map(
        /** Primitives in the Scala and Avro sense */
        typeOf[Int]     -> Schema.create(AvroType.INT),
        typeOf[Float]   -> Schema.create(AvroType.FLOAT),
        typeOf[Long]    -> Schema.create(AvroType.LONG),
        typeOf[Double]  -> Schema.create(AvroType.DOUBLE),
        typeOf[Boolean] -> Schema.create(AvroType.BOOLEAN),
        typeOf[String]  -> Schema.create(AvroType.STRING),
        typeOf[Null]    -> Schema.create(AvroType.NULL),
        /** Primitives in the Avro sense */
        typeOf[java.nio.ByteBuffer] -> Schema.create(AvroType.BYTES),
        typeOf[Utf8]       -> Schema.create(AvroType.STRING)
      )
    
      def createSchema(tpt: c.universe.Type) : Schema = {
        tpt match {
          case x if (primitiveClasses.contains(x)) => primitiveClasses(x)
          case x @ TypeRef(pre, symbol, args) if (x <:< typeOf[List[Any]] && args.length == 1)  => {
            Schema.createArray(createSchema(args.head))
          }
          case x @ TypeRef(pre, symbol, args) if (x <:< typeOf[Option[Any]] && args.length == 1) => {
            if (args.head <:< typeOf[Option[Any]]) { 
              throw new UnsupportedOperationException("Implementation limitation: Cannot immediately nest Option types")
            } 
            else Schema.createUnion(JArrays.asList(Array(createSchema(typeOf[Null]), createSchema(args.head)):_*))
          }
          //if a record is found
          case x @ TypeRef(pre, symbol, args) if (x <:< typeOf[Product with Serializable] ) => { 
            SchemaStore.schemas(x.toString)
          }
          case x => throw new UnsupportedOperationException("Cannot support yet: " + x )
        }
      }  
 
      val avroFields = first.map(v =>{
        // Since macro annotations are untyped, we must typecheck each field so the 
        // tree's tpe field is not null. The typecheck also has the virtue of   
        // expanding a field's type on demand.
        val typeCheckedField = c.typeCheck(q"type T = ${v.tpt}") match {
          case x @ TypeDef(mods, name, tparams, rhs)  => rhs
        }    
        new Field(v.name.toString.trim, createSchema(typeCheckedField.tpe), "Auto-Generated Field", null)
      })
      val avroSchema = Schema.createRecord(className, "Auto-generated schema", namespace, false)
      avroSchema.setFields(JArrays.asList(avroFields.toArray:_*))
      ClassFieldStore.storeClassFields(avroSchema) //store the field data from the new schema
      SchemaStore.schemas += ((namespace + "." + className) -> avroSchema)
      avroSchema
    }


    //MethodGen - generates put, get, and getSchema needed to implement SpecificRecord for serialization
    def generateNewMethods(name: TypeName, indexedFields: List[FieldData]) = {
      //expands to cases for a pattern match, e.g. case 1 => username.asInstanceOf[AnyRef]
      val getDef = { 
        def asGetCase(fd: FieldData) = {
          def convertToJava(typeTree: Tree, convertable: Tree): Tree = {
            if (!typeTree.children.isEmpty) { 
              val container = typeTree.children.head.toString
              val boxedTree = typeTree.children.last

              container match {
                case "Option" => convertToJava(boxedTree, q"""$convertable match {
                  case Some(x) => x
                  case None    => null
                }"""  ) 
                case "List" => {

                  def convertElements(typeTree: Tree, listArg: Tree): Tree  = {
                    if (!typeTree.children.isEmpty) { 
                      typeTree.children.head.toString match {
                        case "Option" => q"""$listArg.map(x=> x match {
                            case Some(x) => ${convertToJava(typeTree.children.last, q"x")}
                            case None    => null
                          })"""   
                        case "List"   => { 
                          //the map function's input parameter with the same name as the field makes codegen easier, cleaner
                          q"""$listArg.map( (${fd.nme}:$typeTree) => {
                              java.util.Arrays.asList(( ${convertElements(typeTree.children.last, q"${fd.nme}: $typeTree" )} ):_* )
                          }) """
                        }
                      }                      
                    }
                    else q""" $listArg """
                  } 

                  //null case covers a None
                  q"""$convertable match {
                    case null => null
                    case _ => java.util.Arrays.asList( ${convertElements(boxedTree, convertable)} :_*)
                  }"""
                }
              }  
            }
            else convertable
          }
          val convertedToJava = convertToJava(fd.tpt, q"${fd.nme}") //recursively checks tree and unboxes appropriately
          cq"""pos if (pos == ${fd.idx}) => $convertedToJava.asInstanceOf[AnyRef]"""
        }

        val getCases = indexedFields.map(f => asGetCase(f)) :+ cq"""_ => new org.apache.avro.AvroRuntimeException("Bad index")"""
        q"""def get(field: Int): AnyRef = field match {case ..$getCases}"""
      }
      //TODO make liftable[Schema] so we don't have to toString.
      val getSchemaDef = q"""def getSchema: Schema = new Schema.Parser().parse(${SchemaStore.schemas(namespace + "." + name).toString})""" 

      val putDef = {//expands to cases used in a pattern match, e.g. case 1 => this.username = value.asInstanceOf[String]
        def asPutCase(fd: FieldData) = {

          if (!fd.tpt.children.isEmpty) { //handle paramerterized types (e.g. Option, List, or Map) separately 
            val toContainer = newTermName(fd.tpt.children.head.toString match {
              case "Option"   => "toList" //kind of a hack, serves as a dummy type
              case "List"     => "toList"
           //   case "Seq"      => "toSeq"//TODO
              case _ =>  sys.error("Cannot support parameterized yet: " + fd.tpt)
            } )
 
            val parameterizedTypeTrees: List[Tree] = fd.tpt.children
            val splicableTypeTrees = parameterizedTypeTrees.reduceRight((a, b) => tq"$a[$b]")

            val utf8ResultExpr = q"utf8.toString"
            val identityResultExpr = q"x"

            val utf8Case     = cq"""utf8: org.apache.avro.util.Utf8 => $utf8ResultExpr"""
            val defaultCase  = cq"""x => $identityResultExpr"""
            val baseConversionCases  = List(utf8Case, defaultCase)

            def listContainerTypes(typeTree: Tree, types: List[Tree]): List[Tree] = {
              if (!typeTree.children.isEmpty) {
                val updatedTypes = types :+ typeTree.children.head
                listContainerTypes(typeTree.children.last, updatedTypes)
              }
              else types
            }

            val reversed = listContainerTypes(fd.tpt, List()).map(f => f.toString).reverse
            val reversedTypes = reversed.iterator

            def convertToScala(container: String, conversionCases: List[Tree]): List[Tree] = {  
              val updatedCases  = container match {
                case "Option" => {
                  conversionCases.map(x => x match{
                    case cq"$pattern => $returnExpr" => cq"$pattern => Option($returnExpr)"
                  })
                }
                case "List"   => {
                  List(
                    cq"""null => null""",  //a guard in case the list is an optional/nullable list
                    cq"""array: org.apache.avro.generic.GenericData.Array[_] => {
                      scala.collection.JavaConversions.asScalaIterator(array.iterator).${toContainer}.map(n => n match { 
                        case ..$conversionCases 
                      })
                    }"""
                  )
                }
              } 

              if (reversedTypes.hasNext) { 
                convertToScala(reversedTypes.next, updatedCases) 
              }
              else updatedCases
            }

            val putConversionCases = convertToScala(reversedTypes.next, baseConversionCases)
            cq"""pos if (pos == ${fd.idx}) => this.${fd.nme} = (value match { 
              case ..$putConversionCases
            }).asInstanceOf[$splicableTypeTrees] """  
          }

          else { //children empty, Oh, to be a simple type
            cq"""pos if (pos == ${fd.idx}) => this.${fd.nme} = (value match {
              case utf8: org.apache.avro.util.Utf8 => utf8.toString
              case x => x
            }).asInstanceOf[${fd.tpt}] """ 
          }    

        }
        val putCases = indexedFields.map(f => asPutCase(f)) :+ cq"""_ => new org.apache.avro.AvroRuntimeException("Bad index")"""
        q"""def put(field: Int, value: scala.Any): Unit = field match {case ..$putCases}"""
      }

      List(getDef, getSchemaDef, putDef)
    }

    //Update ClassDef and ModuleDef
    val result = { 


      annottees.map(_.tree).toList match {


        //Update ClassDef
        case classDef @ q"$mods class $name[..$tparams](..$first)(...$rest) extends ..$parents { $self => ..$body }" :: Nil => {

          generateSchema(name.toString, namespace, first)

          def indexed(fields: List[ValDef]) = { //adds index to the field name and type, loads into a FieldData case class
            (fields.map(_.name), first.map(_.tpt), 0 to fields.length-1)
              .zipped 
              .toList 
              .map(f => FieldData(f._1, f._2, f._3)) //(name, type, index)
          } 

          val newImports = List(q" import org.apache.avro.Schema")
          val newCtors   = generateNewCtors(indexed(first))   //a no-arg ctor so `newInstance()` can be used
          val newDefs    = generateNewMethods(name, indexed(first)) //`get`, `put`, and `getSchema` methods 
          val newParents = parents ::: generateNewBaseTypes   //extend SpecificRecordBase
          val newBody    = body ::: newImports ::: newCtors ::: newDefs      //add new members to the body

          //return an updated class def
          q"""$mods class $name[..$tparams](..$first)(...$rest) extends ..$newParents { $self => ..$newBody }""" 
        }

      } 
    }

    c.Expr[Any](result)
  }
}

class AvroRecord extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro AvroRecordMacro.impl
}
