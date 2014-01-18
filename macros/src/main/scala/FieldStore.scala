package models

import java.util.concurrent.ConcurrentHashMap

object FieldStore {

   val fields: scala.collection.concurrent.Map[String, List[FieldData]] = scala.collection.convert.Wrappers.JConcurrentMapWrapper(new ConcurrentHashMap[String, List[FieldData]]())
/*
  def accept(field: FieldData) {
    if (!fields.contains(field.tpeName)) {
      fields += field.fieldName -> valueMember
    }
  }
*/
}
