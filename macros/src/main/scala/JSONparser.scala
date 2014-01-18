package models
import scala.util.parsing.json._


import org.json4s._
import org.json4s.native.JsonMethods._

object JSONParser {

  class jsonTypeConverter[T]{
    def unapply(a:Any): Option[T] = { Some(a.asInstanceOf[T])}
    def update(a:Any): Option[T] = {Some(a.asInstanceOf[T])}
  }

  object M extends jsonTypeConverter[Map[String, Any]] 
  object L extends jsonTypeConverter[List[Any]] 
  object S extends jsonTypeConverter[String]
  object P extends jsonTypeConverter[Option[String]]
  object I extends jsonTypeConverter[Int]
  object C extends jsonTypeConverter[java.lang.Class[Any]]
  object O extends jsonTypeConverter[Option[Any]]
  object U extends jsonTypeConverter[List[(Null, Any)]]

  def parseJsonString(jsonSchema: String) = {

    def getNamespace(jsonSchema: List[Any]): Option[String] = (for { 
      (M(map)) <- jsonSchema
      P(namespace) = {  
        if (map.keys.exists(k => k == "namespace")) Some(map("namespace"))
        else  None
      } 
    } yield namespace).head

    def getName(schema: Any): String = { 
      (for { 
         M(map) <- List(schema)
         S(name) = map("name")
      } yield name).head
    }
  
    val parsedJSON = List((JSON.parseFull(jsonSchema)).get)

    asSchemaList(parsedJSON)//extracts nested schemas to a list of schemas (parsed, so really a list of ClassData objects)
      .filter( s => s != List.empty)
      .map(schema => ClassData( 
        getNamespace(parsedJSON), 
        getName(schema), 
        getFields(schema)) )
  }

  def asSchemaList(jsonSchema: List[Any]): List[Any]= { 

    def getRecordMap(fieldType: Any) = fieldType match {
      case s: String => s 
      case m: Map[String,_] => m
      case l: List[Any] =>  {
        if (l.filter(c => c != "null").collect{ case x @ Tuple2(_: String,  _: Any) => x }.isEmpty) l
        else { 
          val entry = l.filter(c => c != "null").collect{ case x: Map[String, Any]=> x }.head
          entry("type") match {
            case "record" => entry
            case "array"  => entry("items").asInstanceOf[List[Any]].filter(c => c != "null").head
          }
        }
      }
    }

    def getNestedSchemas(jsonSchema: List[Any]): List[Any] =  { 
      for {
        M(record) <- jsonSchema
        L(fields) =  record("fields")
        M(field) <- fields
        fieldType = field("type")
        val recordMap = getRecordMap(fieldType)
        if (recordMap match {
          case m: Map[String, Any] => true //yield only if its a map
          case _                   => false
        })
      } yield fieldType
    }

    if (jsonSchema == List.empty)  List(List.empty)
    else {
      val nestedSchemas = asSchemaList(getNestedSchemas(jsonSchema).map(j => getRecordMap(j)))
      List(jsonSchema, nestedSchemas).flatten
    } 

  }
   
  def getFields(schema: Any): List[FieldData]= { 

    def getBoxed(typeName: String) = {
      typeName.dropWhile( c => (c != '[') ).drop(1).dropRight(1)
    }

    def matchTypes(JSONfieldType: Any): Any = {  
      JSONfieldType match { 
        case u: List[_] if u.length == 2 =>   U(u(0)) = u(0);  "Option[" + avroToScalaType(matchTypes(u.find(t => t != "null").get).toString) + "]" 
        case s: String             => S(s) = s; s 
        case m: Map[String, _]   => { 
          m("type") match {
            case "array" => "List[" + matchTypes(m("items")) + "]"
            case "record" => m("name")
            case _       => error("could not parse a valid avro type") 
          }
        }
        case c: Class[_]         => C(c) = c
        case _                     => error("JSON field type not yet supported")
      }
    }

    def avroToScalaType(fieldType: String): String = {

      fieldType match {
      //Thanks to @ConnorDoyle for the mapping: https://github.com/GenslerAppsPod/scalavro
      case "null"    => "Unit"
      case "boolean" => "Boolean"
      case "int"     => "Int"
      case "long"    => "Long"
      case "float"   => "Float"
      case "double"  => "Double"
      case "string"  => "String"
      case x: String => x
      case _         => error("JSON parser found an unsupported type")
      }
    }
/*
  def asDefaultParam(fieldType: String): Object = {
    fieldType match {
      case "Null"    => null.asInstanceOf[Object]
      case "Boolean" => true.asInstanceOf[Object]
      case "Int"     => 1.asInstanceOf[Object]
      case "Long"    => 1L.asInstanceOf[Object]
      case "Float"   => 1F.asInstanceOf[Object]
      case "Double"  => 1D.asInstanceOf[Object]
      case "String"  => ""
      case "Byte"    => 1.toByte.asInstanceOf[Object]
      case "Short"   => 1.toShort.asInstanceOf[Object]
      case "Char"    => 'k'.asInstanceOf[Object]
      case "Any"     => "".asInstanceOf[Any].asInstanceOf[Object]
      case "AnyRef"  => "".asInstanceOf[AnyRef].asInstanceOf[Object]
      case "Unit"    => ().asInstanceOf[scala.runtime.BoxedUnit]
      case "Nothing" => null
      case "Object"  => new Object

      case l: String if l.startsWith("List[")   =>   List(asDefaultParam(getBoxed(l))).asInstanceOf[List[Any]].asInstanceOf[Object]
      case o: String if o.startsWith("Option[") => Option(asDefaultParam(getBoxed(o))).asInstanceOf[Option[Any]].asInstanceOf[Object]
      case u: String => u.asInstanceOf[Object]// Class.forName(u)// CaseClassGenerator.generatedClasses.get(typeName).get.instantiated$ 
    }
  }
*/
    for {
      M(schema) <- List(schema)
      L(fields) = schema("fields")
      M(field) <- fields
      S(fieldName) = field("name")
      S(fieldType) = matchTypes(field("type"))
    } yield FieldData(fieldName, avroToScalaType(fieldType))//, asDefaultParam(avroToScalaType(fieldType)))
  }
}
