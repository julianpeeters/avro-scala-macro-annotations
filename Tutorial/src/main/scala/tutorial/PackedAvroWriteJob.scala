/*
 * Copyright (c) 2012 Twitter, Inc.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package tutorial

import com.twitter.scalding._
import com.twitter.scalding.avro.{PackedAvroSource, UnpackedAvroSource}

import org.apache.avro.Schema
import org.apache.avro.specific.{SpecificRecord, SpecificRecordBase}

import TDsl._


case class Twitter_Schema(var username: String, var tweet: String, var timestamp: Long) extends SpecificRecordBase with SpecificRecord {

  def this() = this("","", 0L)


  def getSchema: Schema = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"twitter_schema\",\"namespace\":\"com.miguno.avro\",\"fields\":[{\"name\":\"username\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"doc\":\"Name of the user account on Twitter.com\"},{\"name\":\"tweet\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"doc\":\"The content of the user's Twitter message\"},{\"name\":\"timestamp\",\"type\":\"long\",\"doc\":\"Unix epoch time in seconds\"}],\"doc:\":\"A basic schema for storing Twitter messages\"}")//new Schema.Parser().parse(AvroType[Person].schema.toString)

  def get(field: Int): AnyRef = {
    if (field == 1)
      username
    if (field == 2)
      tweet
    else
      timestamp
  val fields = this.getClass.getDeclaredFields()
  fields(field).get(this)
  }

  def put(field: Int, value: scala.Any) = {
    val fields = (this.getClass.getDeclaredFields())
    fields(field).set(this,value)
  }

}


/**
 * hadoop jar chapter3-0-jar-with-dependencies.jar com.twitter.scalding.Tool -Dmapred.output.compress=true AvroExample --hdfs
 *
 */
class PackedAvroWriteJob(args: Args) extends Job(args) {

  /**
   * Dummy data
   */
  val testList = List(
    Twitter_Schema("name1", "tweet1", 10L),
    Twitter_Schema("name2", "tweet2", 20L),
    Twitter_Schema("name3", "tweet3", 30L))

  /**
    * Write dummy data to PackedAvro
    */
  val twitter_schemas: TypedPipe[Twitter_Schema] = TypedPipe.from(testList)//getTwitter_SchemaPipe 
  val writeToPackedAvro = 
    twitter_schemas
      .map{twitter_schema => twitter_schema.copy(username = "My new name is " + twitter_schema.username) }
      .debug
      .write(PackedAvroSource[Twitter_Schema]("data/PackedAvroOutput.avro"))

}
