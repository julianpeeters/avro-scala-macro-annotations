package com.miguno.avro

import com.julianpeeters.avro.annotations._

import com.gensler.scalavro.types.AvroType
import com.gensler.scalavro.io.AvroTypeIO
import scala.util.{Try, Success, Failure}

import java.io.File

@AvroTypeProvider("src/main/avro/twitter_schema.avsc")
case class twitter_schema()

object Example extends App {

  val tweet = twitter_schema("Flicker, flicker, flicker, blam, pow", "Floyd", 72L)
  
  val tweetsType = AvroType[twitter_schema]

  val outStream: java.io.ByteArrayOutputStream = new java.io.ByteArrayOutputStream // some stream...

  tweetsType.io.write(tweet, outStream)

  val inStream: java.io.InputStream = new java.io.ByteArrayInputStream(outStream.toByteArray)// some stream...

  tweetsType.io.read(inStream) match {
    case Success(readResult) => println("deserialized record is the same as the pre-serialized record?: "+ (readResult == tweet)) // true
    case Failure(cause)      => // handle failure...
  }
 
}
