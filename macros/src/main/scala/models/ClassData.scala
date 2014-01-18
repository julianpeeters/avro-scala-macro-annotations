
package models

case class ClassData(
  classNamespace: Option[String], 
  className: String, 
  classFields: List[FieldData])


