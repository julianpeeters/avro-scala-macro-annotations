//This code was adapted from the original example in the Macro Paradise plugin, and from the refletion docs at http://docs.scala-lang.org/overviews/reflection/overview.html

import com.novus.salat._
import com.novus.salat.global._
import com.mongodb.casbah.Imports._

import com.gensler.scalavro.types.AvroType
import com.gensler.scalavro.io.AvroTypeIO
import scala.util.{Try, Success, Failure}

import models._

import scala.tools.scalap.scalax.rules.scalasig._

object Test extends App {

//Macro Annotations works!
  println(MyRec().x)


//Test as a type parameter in Salat
  val myRec =  MyRec()
  val dbo = grater[MyRec].asDBObject(myRec)
    println(dbo)//empty! because MyRec is too.  How do I get an updated consturctor?

  val obj = grater[MyRec].asObject(dbo)
    println(obj)

  println(myRec == obj)


//Test as type-parameter in Scalavro
  val myRecType = AvroType[MyRec]
    println("schema: " + myRecType.schema)//no fields! because the constructor hasn't yet been updated?  

}


