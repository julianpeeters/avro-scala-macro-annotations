
package org.json4s

import models._

import org.specs2.mutable.Specification
import text.Document

import com.novus.salat._
import com.novus.salat.global._
import com.mongodb.casbah.Imports._

import com.gensler.scalavro.types.AvroType
import com.gensler.scalavro.io.AvroTypeIO
import scala.util.{Try, Success, Failure}

@valProvider
case class rec

@valProvider
class MyRecord



class AvroTypeProviderSpec extends Specification {

    val classMaps = JSONParser.storeClassFields(jsonSchema)


 "if a class' members were added via macro annotation, it" should {
    "serialize and deserialize correctly with a scala-sig parsing library such as Salat" in {

      val myRecord =  MyRecord()

      val dbo = grater[MyRecord].asDBObject(myRecord)
      val obj = grater[MyRecord].asObject(dbo)

      myRecord === obj
    }


    "serialize and deserialize correctly with a Scala reflection-based library such as Scalavro" in {

    val myRecordType = AvroType[MyRecord]
      println("schema: " + myRecordType.schema)
 
    mapContainsClasses === true
    }
  }

  lazy val jsonSchema = """
{"name":"MyRecord","type":"record","fields":[{"name":"x","type":{"name":"rec","type":"record","fields":[{"name":"i","type":"int"}],"namespace":"models"}}],"namespace":"models"}
  """
}
