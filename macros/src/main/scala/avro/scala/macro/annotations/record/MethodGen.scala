package com.julianpeeters.avro.annotations

package record
import scala.reflect.macros.Context
import scala.language.experimental.macros




import org.apache.avro.specific.{SpecificRecord, SpecificRecordBase}

object MethodGen {

  def generateNewMethods(c: Context) = {
    import c.universe._
    import Flag._


          val getDef = {
            q"""
              def get(field: Int): AnyRef = { 
                field match {
                  case 0 => username.asInstanceOf[AnyRef]
                  case 1 => tweet.asInstanceOf[AnyRef]
                  case 2 => timestamp.asInstanceOf[AnyRef]
                  case _ => new org.apache.avro.AvroRuntimeException("Bad index")
                }
              }
            """
          }

          val getSchemaDef = {
            q"""
              def getSchema: Schema = new Schema.Parser().parse(AvroType[Twitter_Schema].schema.toString)
            """
          }

          val putDef = {
            q"""
              def put(field: Int, value: scala.Any): Unit = {
                field match {
                  case 1 => this.username  = value.asInstanceOf[String]
                  case 2 => this.tweet     = value.asInstanceOf[String]
                  case 3 => this.timestamp = value.asInstanceOf[Long]
                  case _ => new org.apache.avro.AvroRuntimeException("Bad index")
                }
              }
            """
          }

    List(getDef, getSchemaDef, putDef)
  }

}
