package avro.provider

package models

import java.util.concurrent.ConcurrentHashMap
import collection.JavaConversions._
 
import org.apache.avro.Schema

object ClassFieldStore {

  val fields: scala.collection.concurrent.Map[String, List[FieldData]] = scala.collection.convert.Wrappers.JConcurrentMapWrapper(new ConcurrentHashMap[String, List[FieldData]]())

  def storeClassFields(schema: Schema): Unit = {
    val fields = schema.getFields.map( field => AvroTypeMatcher.parseField(field) ).toList
    ClassFieldStore.fields += (schema.getName -> fields)
  }

}
