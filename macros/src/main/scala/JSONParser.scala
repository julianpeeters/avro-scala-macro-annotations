package models

import org.json4s._
import org.json4s.native.JsonMethods._

object JSONParser {

  implicit val formats = org.json4s.DefaultFormats

  def storeClassFields(jsonSchema: String) = {

    for {
      JObject(entry) <- parse(jsonSchema)
      val entryMap = entry.toMap
      if entryMap("type") == JString("record")
      JField("name", JString(name)) <- entry
      JField("fields", JArray(fieldList)) <- entry

      val fields = for {
        JObject(fields)<- fieldList

        val fieldMap = fields.toMap
        val fieldName = fieldMap("name").extract[String]
        val fieldType = {fieldMap("type") match {
          case JString(x) => AvroTypeMatcher.avroToScalaType(x)
          case JObject(y) => {
            if ( (y.toMap.get("name").isDefined) && 
                 (y.toMap.get("type").isDefined) && 
                 (y.toMap.get("type").get.extract[String] == "record")) {
              y.toMap.get("name").get.extract[String]
            }
            else error("""JSONParser couldn't find a "name" or "type" field""")
          }
          case JArray(z) => {
            if (z.length == 2 && z.head.extract[String] == "null" ) { //if an array, we can turn it into an option type
              z.last  match {
                case JString(a) => "Option[" + AvroTypeMatcher.avroToScalaType(a) + "]" //such as Option[String]
                case JObject(b) => { 
                  if (b.toMap.get("type").isDefined) b.toMap.get("type").get.extract[String] match {
                    case "record" => { //or such as Option[USER_DEFINED]
                      if (b.toMap.get("name").isDefined) "Option[" + b.toMap.get("name").get.extractOpt[String].get + "]"
                      else error("JSONParser found a record with no name")
                    } 
                    case "array"  => { //map to List
                      val filterdArrays = {
                        for {
                          JArray(array) <- b.toMap.get("items").get
                          if array.length == 2 && {array.head match { //only interested in elements to be turned into options
                            case JString(i) => true 
                            case _          => false 
                          }}
                        } yield array
                      }

                      val listTypeParam = filterdArrays.map( array => array.last match {
                        case JString(x) => "Option[" + AvroTypeMatcher.avroToScalaType(x) + "]"
                        case JObject(y) => { 
                          if (y.toMap.get("name").isDefined) "Option[" + y.toMap.get("name").get.extract[String] + "]"
                          else error("""JSONParser couldn't find a "name" field""")
                        }  
                        case _ => error("JSONParser tried to make an Option[List[...]] but failed")                      
                      }).head
                      
                      "Option[List[" + listTypeParam + "]]"

                    }
                    case _ => error("JSONParser found an unknown avro type")
                  }
                  else error("""JSONParser couldn't find a "name" field""")
                }
                case _ => error("JSON Parser found an unknown type found in a JArray") 
              }
            }
            else error("JSON Parser found nothing good in that JArray")
          }  
          case x          => error("unsupported avro type")
        }}.toString
      } yield  FieldData(fieldName, fieldType)

      val set = ClassFieldStore.fields += (name -> fields)
    } yield Map(name -> fields)
  }
}
