package com.julianpeeters.avro.annotations

import matchers.DefaultParamMatcher
import scala.reflect.macros.Context


import scala.language.experimental.macros
import scala.annotation.StaticAnnotation

import com.gensler.scalavro.types._
import org.apache.avro.util.Utf8
import org.apache.avro.Schema
import org.apache.avro.Schema.Field
import scala.collection.JavaConversions._
import java.util.concurrent.ConcurrentHashMap

//import conversions._

//import scala.collection.mutable.{HashSet,ListBuffer}


import org.apache.avro.Schema.{Type => AvroType}

import java.util.{Arrays => JArrays}


object AvroRecordMacro {

  val schemas: scala.collection.concurrent.Map[String, Schema] = scala.collection.convert.Wrappers.JConcurrentMapWrapper(new ConcurrentHashMap[String, Schema]())


  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    
    import c.universe._
    import Flag._



    case class FieldData(nme: TermName, tpt: Tree, idx: Int)//holds info about the fields of the annotee

    //Extender
    def generateNewBaseTypes =  List( tq"SpecificRecordBase",  tq"HasAvroConversions")

    //CtorGen
    def generateNewCtors(indexedFields: List[FieldData]) = {
      def asCtorParam(typeName: String, c: Context): c.universe.Tree = {
        if (typeName.endsWith("]")) DefaultParamMatcher.asParameterizedDefaultParam(typeName, c)
        else  DefaultParamMatcher.asDefaultParam(typeName, c)}
      val defaultParams = indexedFields.map(field => asCtorParam(field.tpt.toString, c)) //toString to reuse DefaultParamMatcher
      val newCtorDef = q"""def this() = this(..$defaultParams)"""
      val defaultCtorPos = c.enclosingPosition //thanks to Eugene Burmako for the workaround to position the ctor correctly
      val newCtorPos = defaultCtorPos
        .withEnd(defaultCtorPos.endOrPoint + 1)
        .withStart(defaultCtorPos.startOrPoint + 1)
        .withPoint(defaultCtorPos.point + 1)
      List( atPos(newCtorPos)(newCtorDef) ) //Return a list of new CtorDefs
    }

    //SchemaGen

      val freshName = c.fresh(newTypeName("Probe$"))
      val probe = c.typeCheck(q""" {class $freshName; ()} """)
      val freshSymbol = probe match {
        case Block(List(t), r) => t.symbol
      }
      val namespace = freshSymbol.fullName.toString.replace("." + freshName.toString, "") //strips dot and class name

 val primitiveClasses = Map(
      /** Primitives in the Scala and Avro sense */
      "Int"     -> Schema.create(AvroType.INT),
      "Float"   -> Schema.create(AvroType.FLOAT),
      "Long"    -> Schema.create(AvroType.LONG),
      "Double"  -> Schema.create(AvroType.DOUBLE),
      "Boolean" -> Schema.create(AvroType.BOOLEAN),
      "String"  -> Schema.create(AvroType.STRING),
      "Null"    -> Schema.create(AvroType.NULL),

      /** Types which are primitives in Scala, but have no exact match in Avro */
      "Short"   -> Schema.create(AvroType.INT),
      "Byte"    -> Schema.create(AvroType.INT),
      "Char"    -> Schema.create(AvroType.INT),

      /** Primitives in the Avro sense */
      "ByteBuffer" -> Schema.create(AvroType.BYTES),
      "Utf8"       -> Schema.create(AvroType.STRING)

      /** Boxed primitives 
      BoxedIntClass       -> Schema.create(AvroType.INT),
      BoxedByteClass      -> Schema.create(AvroType.INT),
      BoxedShortClass     -> Schema.create(AvroType.INT),
      BoxedCharacterClass -> Schema.create(AvroType.INT),
      BoxedBooleanClass   -> Schema.create(AvroType.BOOLEAN),
      BoxedFloatClass     -> Schema.create(AvroType.FLOAT),
      BoxedLongClass      -> Schema.create(AvroType.LONG),
      BoxedDoubleClass    -> Schema.create(AvroType.DOUBLE)
      */
    )
    
    def createSchema(tpt: String) : Schema = { //TODO prefer no Strings, but how to match on the value of c.universe.Tree
      val fieldTypeName = tpt.toString 

 

      if (primitiveClasses.contains(fieldTypeName)) {

        primitiveClasses(fieldTypeName)
//}
     // } else if (tpe == ArrayClass) {
        //if (tpe.normalize.typeArgs.head != ByteClass.tpe)
        //  throw new UnsupportedOperationException("Bad Array Found: " + tpe + ". Use scala collections for lists")
       // createSchema(byteBufferClass.tpe)
/*
      } else if (tpe.typeSymbol.isSubClass(TraversableClass)) {
        tpe.typeArgs.size match {
          case 1 =>
            Schema.createArray(createSchema(tpe.typeArgs.head))
          case 2 =>
            if (tpe.typeArgs.head.typeSymbol != StringClass && 
                tpe.typeArgs.head.typeSymbol != utf8Class)
              throw new UnsupportedOperationException("Avro maps require string/utf8 keys")
            Schema.createMap(createSchema(tpe.typeArgs.tail.head))
          case i =>
            throw new AssertionError("Oops, %d type args found".format(i))
        }
*/

      } else if (fieldTypeName.startsWith("List[")) { 
        val boxed = DefaultParamMatcher.getBoxed(fieldTypeName) //TODO move `getBoxed` to a Utility object?


      //  tpe.typeArgs.size match {
      //    case 1 =>
            Schema.createArray(createSchema("Null"))
       //   case 2 =>
       //     if (tpe.typeArgs.head.typeSymbol != StringClass && 
       //         tpe.typeArgs.head.typeSymbol != utf8Class)
        //      throw new UnsupportedOperationException("Avro maps require string/utf8 keys")
         //   Schema.createMap(createSchema(tpe.typeArgs.tail.head))
         // case i =>
         //   throw new AssertionError("Oops, %d type args found".format(i))
    //    }


      } else if (fieldTypeName.startsWith("Option[")) { 
      val boxed = DefaultParamMatcher.getBoxed(fieldTypeName) //TODO move `getBoxed` to a Utility object?
      //  if(DefaultParamMatcher.getBoxed(fieldTypeName).endsWith("]")) createSchema()
       // else q"""Some(${asDefaultParam(getBoxed(o), c)})"""
/*
    def boxTypeTrees(typeName: String) = {//"boxing" in this case is wrapping the string in [] so it looks correct for splicing
      val unboxedStrings = typeName.dropRight(typeName.count( c => c == ']')).split('[')
      val types = unboxedStrings.map(g => newTypeName(g)).toList  
      val typeTrees: List[Tree] = types.map(t => tq"$t")
      typeTrees.reduceRight((a, b) => tq"$a[$b]")
    }

fieldTypeNameName match {
      //List
      case  l: String if l.startsWith("List[") => {
        if (getBoxed(l).endsWith("]")) q"""List(${asParameterizedDefaultParam(getBoxed(l), c)})"""
        else q"""List(${asDefaultParam(getBoxed(l), c)})"""
      }
      //Option
      case  o: String if o.startsWith("Option[") => { 
        if(getBoxed(o).endsWith("]")) q"""Some(${asParameterizedDefaultParam(getBoxed(o), c)})"""
        else q"""Some(${asDefaultParam(getBoxed(o), c)})"""
      }
      //User-Defined
      case  x: String if x.startsWith(x + "[") => { 
        if(getBoxed(x).endsWith("]")) q"""${newTermName(x)}(${asParameterizedDefaultParam(getBoxed(x), c)})"""
        else q"""${newTermName(x)}(${asDefaultParam(getBoxed(x), c)})"""
      }
*/

  //      val boxed = DefaultParamMatcher.getBoxed(fieldTypeName) //TODO move `getBoxed` to a Utility object?

        if (boxed.startsWith("Option[")) { 
          throw new UnsupportedOperationException("Implementation limitation: Cannot immediately nest Option types")
       // }
      //  if (isUnion(listParam.typeSymbol)) {
          // special case when you do Option[A], where A is an AvroUnion
         // val innerSchemas = createSchema(listParam).getTypes.toArray(Array[Schema]())
        //  Schema.createUnion(JArrays.asList(
        //    (Array(createSchema(NullClass.tpe)) ++ innerSchemas):_*))
        } else
          Schema.createUnion(JArrays.asList(Array(createSchema("Null"), createSchema(boxed)):_*))

/* 
        val listParam = tpe.typeArgs.head
        if (listParam.typeSymbol == OptionClass)
          throw new UnsupportedOperationException("Implementation limitation: Cannot nest option types")
        if (isUnion(listParam.typeSymbol)) {
          // special case when you do Option[A], where A is an AvroUnion
          val innerSchemas = createSchema(listParam).getTypes.toArray(Array[Schema]())
          Schema.createUnion(JArrays.asList(
            (Array(createSchema(NullClass.tpe)) ++ innerSchemas):_*))
        } else
          Schema.createUnion(JArrays.asList(
            Array(createSchema(NullClass.tpe), createSchema(listParam)):_*))

*/

      } else if (schemas.keys.contains(namespace + "." + fieldTypeName.toString) ) { //if it's a record type
        schemas(namespace + "." + fieldTypeName.toString)
      }
/*

      } else if (isUnion(tpe.typeSymbol)) {
        getOrCreateUnionSchema(tpe.typeSymbol, Schema.createUnion(JArrays.asList(
          retrieveUnionRecords(tpe.typeSymbol).
          map(_.tpe).
          map(t => createSchema(t)).toArray:_*)))
      } 
*/
      else throw new UnsupportedOperationException("Cannot support yet: " + tpt)

 //   }
//val classToSchema: Map[String, Schema] = Map.empty
    
//addRecordSchema(tpt.symbol, (Schema.createRecord(name.toString, "Auto-generated schema", namespace.orNull, false)).setFields(JArrays.asList(fields.toArray:_*)))
//}



//def addRecordSchema(name: Symbol, schema: Schema) {
 //   assert(schema.getType == AvroType.RECORD)
 //   classToSchema += name -> schema
 // }


}




def generateSchema(className: String, namespace: String, first: List[c.universe.ValDef]) = {  
  val avroFields = first.map(v => new Field(v.name.toString.trim, createSchema(v.tpt.toString), "Auto-Generated Field", null))  
  val avroSchema = Schema.createRecord(className, "Auto-generated schema", namespace, false)
  avroSchema.setFields(JArrays.asList(avroFields.toArray:_*))
  schemas += ((namespace + "." + className) -> avroSchema)
  val newVal = q""" val schema = ${avroSchema.toString} """ //TODO add liftable[Schema] so we don't have to call .toString
  List(newVal)
}


/*
    def generateSchema: Schema = {
      val schemaVal = Schema.createRecord(name.toString, "Auto-generated schema", namespace.orNull, false)).setFields(JArrays.asList(fields.toArray:_*)
    } 
*/


    //Update ClassDef and ModuleDef
    val result = { 
      annottees.map(_.tree).toList match {

        //Update ClassDef
        case classDef @ q"$mods class $name[..$tparams](..$first)(...$rest) extends ..$parents { $self => ..$body }" :: Nil => {

         // val avroFields = first.map(f => f.tpe match {case Int @ c.universe.Type => println(x)})//;case _ => println("eh")})

          def indexed(fields: List[ValDef]) = { //adds index to the field name and type, loads into a FieldData case class
            (fields.map(_.name), first.map(_.tpt), 0 to fields.length-1)
              .zipped 
              .toList 
              .map(f => FieldData(f._1, f._2, f._3)) //(name, type, index)
          } 

          val newVals = generateSchema(name.toString, namespace, first) //TODO add to module?

    //MethodGen
    def generateNewMethods(indexedFields: List[FieldData]) = {

      val getDef = { //expands to cases that are to be used in a pattern match, e.g. case 1 => username.asInstanceOf[AnyRef]
        def asGetCase(fd: FieldData) = {
          if (!fd.tpt.children.isEmpty) cq"""pos if (pos == ${fd.idx}) => (${fd.nme}).get.asInstanceOf[AnyRef]"""    
          else cq"""pos if (pos == ${fd.idx}) => (${fd.nme}).asInstanceOf[AnyRef]"""
        }
        val getCases = indexedFields.map(f => asGetCase(f)) :+ cq"""_ => new org.apache.avro.AvroRuntimeException("Bad index")"""
        q"""def get(field: Int): AnyRef = field match {case ..$getCases}"""
      }

      //TODO make liftable[Schema] so we don't have to toString.
      val getSchemaDef = q"""def getSchema: Schema = new Schema.Parser().parse(${schemas(namespace + "." + name).toString})""" 

      val putDef = {//expands to cases used in a pattern match, e.g. case 1 => this.username = value.asInstanceOf[String]
        def asPutCase(fd: FieldData) = {
          if (!fd.tpt.children.isEmpty) { //handle paramerterized types (e.g. Option, List, or Map) separately 
            val containerType =  fd.tpt.children.head.toString match {
              case "Option" => q"Some"
            //  case _ =>  new UnsupportedOperationException("Cannot support parameterized yet: " + fd.tpt)
            }  
            val parameterizedTypeTrees: List[Tree] = fd.tpt.children
            val splicableTypeTrees = parameterizedTypeTrees.reduceRight((a, b) => tq"$a[$b]")
            cq"""pos if (pos == ${fd.idx}) => this.${fd.nme} = (value match {
              case utf8: org.apache.avro.util.Utf8 => $containerType(utf8.toString)
              case x                               => $containerType(x)
            }).asInstanceOf[$splicableTypeTrees] """ 
          }  


          else { 
            cq"""pos if (pos == ${fd.idx}) => this.${fd.nme} = (value match {
              case utf8: org.apache.avro.util.Utf8 => utf8.toString
              case x =>  println("children found + " + x); x
            }).asInstanceOf[${fd.tpt}] """ 
          }    
        }
        val putCases = indexedFields.map(f => asPutCase(f)) :+ cq"""_ => new org.apache.avro.AvroRuntimeException("Bad index")"""
        q"""def put(field: Int, value: scala.Any): Unit = field match {case ..$putCases}"""
      }

      List(getDef, getSchemaDef, putDef)
    }

          val newCtors       = generateNewCtors(indexed(first))   //a no-arge ctor so `newInstance()` can be used
          val newDefs        = generateNewMethods(indexed(first)) //`get`, `put`, and `getSchema` methods 


          val newParents     = parents ::: generateNewBaseTypes   //extend SpecificRecord and SpecificRecordBase
          val newBody        = body ::: newCtors ::: newVals ::: newDefs      //add new members to the body

          //return an updated class def
          q"$mods class $name[..$tparams](..$first)(...$rest) extends ..$newParents { $self => ..$newBody }" 
        }
      } 
    }

    c.Expr[Any](result)
  }
  schemas.clear() //remove schemas so we can reuse the object on a new record(s)
}

class AvroRecord extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro AvroRecordMacro.impl
}
