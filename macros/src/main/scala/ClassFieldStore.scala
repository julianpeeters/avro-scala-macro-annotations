package avro.provider

package models

import java.util.concurrent.ConcurrentHashMap

object ClassFieldStore {

   val fields: scala.collection.concurrent.Map[String, List[FieldData]] = scala.collection.convert.Wrappers.JConcurrentMapWrapper(new ConcurrentHashMap[String, List[FieldData]]())

}
