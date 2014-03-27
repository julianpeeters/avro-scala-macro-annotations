//This code was adapted from the original example in the Macro Paradise plugin, and from the refletion docs at http://docs.scala-lang.org/overviews/reflection/overview.html

import avro.provider._
import models._
import com.novus.salat._
import com.novus.salat.global._
import com.mongodb.casbah.Imports._

import com.gensler.scalavro.types.AvroType
import com.gensler.scalavro.io.AvroTypeIO
import scala.util.{Try, Success, Failure}


import scala.tools.scalap.scalax.rules.scalasig._


object Test extends App {


//Test as a type parameter in Salat (Salat serialization of Option[List[_]] not supported)
  val myRecord =  TUPLE_0()
    println(myRecord)
 // val dbo = grater[TUPLE_0].asDBObject(myRecord)
 //   println(dbo)
 // val obj = grater[TUPLE_0].asObject(dbo)
 //   println(obj)

 //   println(myRecord == obj)


//Test as type-parameter in Scalavro
  val myRecordType = AvroType[TUPLE_0]
    println("schema: " + myRecordType.schema)
}



