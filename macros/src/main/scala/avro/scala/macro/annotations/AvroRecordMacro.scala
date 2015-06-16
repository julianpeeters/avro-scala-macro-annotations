package com.julianpeeters.avro.annotations

import util.SchemaStore

import scala.reflect.macros.Context
import scala.language.experimental.macros
import scala.annotation.StaticAnnotation

import org.apache.avro.util.Utf8
import org.apache.avro.Schema
import org.apache.avro.Schema.Field
import org.apache.avro.Schema.{Type => AvroType}
import org.codehaus.jackson.JsonNode
import org.codehaus.jackson.node._

import collection.JavaConversions._
import java.util.concurrent.ConcurrentHashMap
import java.util.{Arrays => JArrays}

object AvroRecordMacro {

  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    
    import c.universe._
    import Flag._

    //holds info about the fields of the annotee
    case class IndexedField(nme: TermName, tpe: Type, dv: Tree, idx: Int)

    // from Connor Doyle, per http://stackoverflow.com/questions/16079113/scala-2-10-reflection-how-do-i-extract-the-field-values-from-a-case-class
    def caseClassParamsOf(tpe: Type): scala.collection.immutable.ListMap[TermName, Type] = {
      val constructorSymbol = tpe.declaration(nme.CONSTRUCTOR)
      val defaultConstructor =
        if (constructorSymbol.isMethod) constructorSymbol.asMethod
        else {
          val ctors = constructorSymbol.asTerm.alternatives
          ctors.map { _.asMethod }.find { _.isPrimaryConstructor }.get
        }

      scala.collection.immutable.ListMap[TermName, Type]() ++ defaultConstructor.paramss.reduceLeft(_ ++ _).map {
        sym => newTermName(sym.name.toString) -> tpe.member(sym.name).asMethod.returnType
      }
    }

    def asDefaultCtorParam(fieldType: c.universe.Type): c.universe.Tree = {
      fieldType match {
        case x if x =:= typeOf[Unit]    => q"()"
        case x if x =:= typeOf[Boolean] => q""" true """
        case x if x =:= typeOf[Int]     => q"1"
        case x if x =:= typeOf[Long]    => q"1L"
        case x if x =:= typeOf[Float]   => q"1F"
        case x if x =:= typeOf[Double]  => q"1D"
        case x if x =:= typeOf[String]  => q""" "" """
        case x if x =:= typeOf[Null]    => q"null"
        // List
        case x @ TypeRef(pre, symbol, args) if (x <:< typeOf[List[Any]] && args.length == 1)  => {
          q"""List(${asDefaultCtorParam(args.head)})"""
        }
        // Option
        case x @ TypeRef(pre, symbol, args) if (x <:< typeOf[Option[Any]] && args.length == 1)  => {
          q"""None"""
        }
        // User-Defined
        case x @ TypeRef(pre, symbol, args) if (x <:< typeOf[Product with Serializable] ) => { 
          val defaultParams = caseClassParamsOf(x).map(p => asDefaultCtorParam(p._2))
          q"""${newTermName(symbol.name.toString)}(..$defaultParams)"""
        }
        case x => sys.error("cannot support yet: " + x)
      }
    }

    //Extender
    def generateNewBaseTypes =  List( tq"org.apache.avro.specific.SpecificRecordBase")

    //CtorGen
    def generateNewCtors(indexedFields: List[IndexedField]) = {
      val defaultParams = indexedFields.map(field => {
        asDefaultCtorParam(field.tpe)
      }) 
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
    def generateSchema(className: String, namespace: String, indexedFields: List[IndexedField]): Schema = { 

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
        typeOf[Utf8]    -> Schema.create(AvroType.STRING)
      )
    
      def createSchema(tpe: c.universe.Type) : Schema = {
        tpe match {
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
          case x @ TypeRef(pre, symbol, args) if (x <:< typeOf[Product with Serializable] ) => {
            // if a case class (a nested record) is found, reuse the schema that was made and stored when its macro was expanded.
            // unsuccessful alternatives: reflectively getting the schema from its companion (can't get a tree from a Symbol),
            // or regenerating the schema (no clean way to get default params, hack default accessor appears empty in this case).
            SchemaStore.schemas(x.toString)
          }
          case x => throw new UnsupportedOperationException("Cannot support yet: " + x )
        }
      } 

      def toJsonDefault(dv: Tree) : JsonNode = {
        lazy val jsonNodeFactory = JsonNodeFactory.instance 
        dv match {
          case EmptyTree                               => null // builds Avro FieldConstructor w/o default
          case Literal(Constant(x: Unit))              => jsonNodeFactory.nullNode
          case Literal(Constant(x: Boolean))           => jsonNodeFactory.booleanNode(x)
          case Literal(Constant(x: Int))               => jsonNodeFactory.numberNode(x)
          case Literal(Constant(x: Long))              => jsonNodeFactory.numberNode(x)
          case Literal(Constant(x: Float))             => jsonNodeFactory.numberNode(x)
          case Literal(Constant(x: Double))            => jsonNodeFactory.numberNode(x)
          case Literal(Constant(x: String))            => jsonNodeFactory.textNode(x)
          case Literal(Constant(x)) if x == null       => jsonNodeFactory.nullNode
          case Ident(TermName("None"))                 => jsonNodeFactory.nullNode
          case Apply(Ident(TermName("Some")), List(x)) => toJsonDefault(x)
          case Apply(Ident(TermName("List")), xs)      => {
            val jsonArray = jsonNodeFactory.arrayNode
            xs.map(x => toJsonDefault(x)).map(v => jsonArray.add(v))
            jsonArray
          }
          // if the default value is another (i.e. nested) record/case class
          case Apply(Ident(TermName(name)), xs) if SchemaStore.schemas.contains(namespace + "." + name) => {
            val jsonObject = jsonNodeFactory.objectNode
            xs.zipWithIndex.map( x => {
              val value = x._1
              val index = x._2
              val nestedRecordField = SchemaStore.schemas(namespace + "." + name).getFields()(index)
              // Values from the tree, field name comes from cross referencing trees pos with schema field pos 
              jsonObject.put(nestedRecordField.name, toJsonDefault(value))
            })
            jsonObject
          }
          case x => sys.error("Could not extract default value. What the heck kinda tree is this?" + x)
        }
      }   

      val avroFields = indexedFields.map(v =>{
        new Field(v.nme.toString.trim, createSchema(v.tpe), "Auto-Generated Field", toJsonDefault(v.dv))
      })
      val avroSchema = Schema.createRecord(className, "Auto-Generated Schema", namespace, false)
      avroSchema.setFields(JArrays.asList(avroFields.toArray:_*))
      SchemaStore.accept(avroSchema)
      avroSchema
    }

    //MethodGen - generates put, get, and getSchema needed to implement SpecificRecord for serialization
    def generateNewMethods(name: TypeName, indexedFields: List[IndexedField]) = {
      //expands to cases for a pattern match, e.g. case 1 => username.asInstanceOf[AnyRef]
      val getDef = { 
        def asGetCase(fd: IndexedField) = {
          def convertToJava(typeTree: Type, convertable: Tree): Tree = {
            typeTree match { 
              case o @ TypeRef(pre, symbol, args) if (o <:< typeOf[Option[Any]] && args.length == 1) => {
                if (args.head <:< typeOf[Option[Any]]) { 
                  throw new UnsupportedOperationException("Implementation limitation: Cannot immediately nest Option types")
                } 
                else q"""$convertable match {
                      case Some(x) => ${convertToJava(args.head, q"x")}
                      case None    => null
                    }"""  
              }
              case x @ TypeRef(pre, symbol, args) if (x <:< typeOf[List[Any]] && args.length == 1) => {
                q"""java.util.Arrays.asList($convertable.map(x => ${convertToJava(args.head, q"x")}):_*)"""
              }
              case x => convertable
            }
          } 
          val convertedToJava = convertToJava(fd.tpe, q"${fd.nme}") 
          cq"""pos if (pos == ${fd.idx}) => $convertedToJava.asInstanceOf[AnyRef]"""
        }
        val getCases = indexedFields.map(f => asGetCase(f)) :+ cq"""_ => new org.apache.avro.AvroRuntimeException("Bad index")"""
        q"""def get(field: Int): AnyRef = field match {case ..$getCases}"""
      }

      val getSchemaDef = q""" def getSchema: Schema = ${name.toTermName}.SCHEMA$$ """

      val putDef = {//expands to cases used in a pattern match, e.g. case 1 => this.username = value.asInstanceOf[String]
        def asPutCase(fd: IndexedField) = {
          def convertToScala(fieldType: Type, tree: Tree): Tree = {  
            fieldType match {
              case s @ TypeRef(pre, symbol, args) if (s =:= typeOf[String]) => {
                q"""$tree match {
                  case x: org.apache.avro.util.Utf8 => $tree.toString
                  case _     => $tree
                } """
              }
              case o @ TypeRef(pre, symbol, args) if (o <:< typeOf[Option[Any]] && args.length == 1) => {
                if (args.head <:< typeOf[Option[Any]]) { 
                  throw new UnsupportedOperationException("Implementation limitation: Cannot immediately nest Option types")
                } 
                else  q"""Option(${convertToScala(args.head, tree)})""" 
              }
              case o @ TypeRef(pre, symbol, args) if (o <:< typeOf[List[Any]] && args.length == 1) => {
                q"""$tree match {
                  case null => null
                  case array: org.apache.avro.generic.GenericData.Array[_] => {
                    scala.collection.JavaConversions.asScalaIterator(array.iterator).toList.map(e => ${convertToScala(args.head, q"e")}) 
                  }
                }"""
              }
              case _ => tree
            }
          }
          cq"""pos if (pos == ${fd.idx}) => this.${fd.nme} = ${convertToScala(fd.tpe, q"value")}.asInstanceOf[${fd.tpe}] """
        }
        val putCases = indexedFields.map(f => asPutCase(f)) :+ cq"""_ => new org.apache.avro.AvroRuntimeException("Bad index")"""
        q"""def put(field: Int, value: scala.Any): Unit = field match {case ..$putCases}"""
      }

      List(getDef, getSchemaDef, putDef)
    }

    // Update ClassDef and Add Companion Object
    val result = { 
      
      // match the annotated class
      annottees.map(_.tree).toList match {

        // Update ClassDef and add companion object
        case classDef @ q"$mods class $name[..$tparams](..$first)(...$rest) extends ..$parents { $self => ..$body }" :: Nil => {

          def indexFields(fields: List[ValDef]) = {
            fields.map(f => {
              val fieldName = f.name
              val fieldType = c.typeCheck(q"type T = ${f.tpt}") match {
                case x @ TypeDef(mods, name, tparams, rhs)  => rhs.tpe
              }
              val defaultValue = f.rhs//extractValue(f.rhs) 
              val position = fields.indexWhere(f => f.name == fieldName)
              IndexedField(fieldName, fieldType, defaultValue, position)
            })
          }
        
          //prep fields from annotee
          val indexedFields = indexFields(first)

          // updates to the annotated class
          val newImports = List(q"import org.apache.avro.Schema")
          val newCtors   = generateNewCtors(indexedFields)   //a no-arg ctor so `newInstance()` can be used
          val newDefs    = generateNewMethods(name, indexedFields) // `get`, `put`, and `getSchema` methods 
          val newParents = parents ::: generateNewBaseTypes        // extend SpecificRecordBase
          val newBody    = body ::: newImports ::: newCtors ::: newDefs      //add new members to the body

          // updates to the companion object
          val schema     = q"${generateSchema(name.toString, namespace, indexFields(first)).toString}"
          val newVal     = q"lazy val SCHEMA$$ = new org.apache.avro.Schema.Parser().parse($schema)"

          // return an updated class def and companion def
          q"""$mods class $name[..$tparams](..$first)(...$rest) extends ..$newParents { $self => ..$newBody };
              object ${name.toTermName} {$newVal}""" 
        }
      } 
    }
    c.Expr[Any](result)
  }
}

class AvroRecord extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro AvroRecordMacro.impl
}
