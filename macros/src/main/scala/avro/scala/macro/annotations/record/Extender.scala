package com.julianpeeters.avro.annotations

package record
import scala.reflect.macros.Context
import scala.language.experimental.macros




import org.apache.avro.specific.{SpecificRecord, SpecificRecordBase}

object Extender {

  def generateNewBaseTypes(c: Context) = {
    import c.universe._
    import Flag._

    List( tq"SpecificRecordBase", tq"SpecificRecord" )
  }

}
