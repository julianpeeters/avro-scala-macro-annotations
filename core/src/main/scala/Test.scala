//This code was adapted from the original example in the Macro Paradise plugin, and from the refletion docs at http://docs.scala-lang.org/overviews/reflection/overview.html

import com.novus.salat._
import com.novus.salat.global._
import com.mongodb.casbah.Imports._

import com.gensler.scalavro.types.AvroType
import com.gensler.scalavro.io.AvroTypeIO
import scala.util.{Try, Success, Failure}

import models._

import scala.tools.scalap.scalax.rules.scalasig._
//import scala.reflect.runtime.universe._
import scala.reflect.runtime.{ universe => ru}




object Test extends App {

//In the first step we obtain a mirror m which makes all classes and types available that are loaded by the current classloader, including class Person.
  val m = ru.runtimeMirror(getClass.getClassLoader)

//The second step involves obtaining a ClassMirror for class MyRecord using the reflectClass method. The ClassMirror provides access to the constructor of class Person.
  val classMyRecord = ru.typeOf[MyRecord].typeSymbol.asClass
  val cm = m.reflectClass(classMyRecord)

//The symbol for MyRecord constructor can be obtained using only the runtime universe ru by looking it up in the declarations of type MyRecord.
  val ctor = ru.typeOf[MyRecord].declaration(ru.nme.CONSTRUCTOR).asMethod


  val ctorm = cm.reflectConstructor(ctor)
 // val p = ctorm("")


//can parse the sig, and it's modified, shorter, no constructors, skips from serializable to <init> 
  val myRecord =  MyRecord()//.x.getClass.newInstance()
  val parser = ScalaSigParser.parse(myRecord.getClass)
    println(parser)


//Test as a type parameter in Salat
  val dbo = grater[MyRecord].asDBObject(myRecord)
    println(dbo)//empty! because myRecord is too.  How do I get an updated consturctor?

  val obj = grater[MyRecord].asObject(dbo)
    println(obj)

  println(myRecord == obj)


//Test as type-parameter in Scalavro
//Dynamically generated class does not work with Scalavro:  no Java class corresponding to fake_type_providers.Test.proxyIn.type found
  val myRecordType = AvroType[MyRecord]
    println("schema: " + myRecordType.schema)//no fields! because the constructor hasn't yet been updated?  

}


