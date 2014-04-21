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

import com.julianpeeters.avro.annotations._
import com.twitter.scalding._
import com.twitter.scalding.avro.{PackedAvroSource, UnpackedAvroSource}
import TDsl._

import com.gensler.scalavro.types._

import org.apache.avro.Schema
import org.apache.avro.specific.{SpecificRecord, SpecificRecordBase}
import conversions._
@AvroRecord
case class Twitter_Schema(var username: String, var tweet: Option[String], var timestamp: Option[Long])

//@AvroTypeProvider("data/twitter.avro")
//@AvroRecord
//case class twitter_schema(var username: String, var tweet: String, var timestamp: Long) 

/**
 * hadoop jar chapter3-0-jar-with-dependencies.jar com.twitter.scalding.Tool -Dmapred.output.compress=true AvroExample --hdfs
 *
 */
class PackedAvroWriteJob(args: Args) extends Job(args) {

  /**
   * Dummy data
   */
/*
  val testList = List(
    twitter_schema("name1", "tweet1", 10L),
    twitter_schema("name2", "tweet2", 20L),
    twitter_schema("name3", "tweet3", 30L))
*/
val testList = List(
    Twitter_Schema("name1", Some("tweet1"), Some(10L)),
    Twitter_Schema("name2", Some("tweet2"), Some(20L)),
    Twitter_Schema("name3", Some("tweet3"), Some(30L)))

  /**
    * Write dummy data to PackedAvro
    */
  val twitter_schemas: TypedPipe[Twitter_Schema] = TypedPipe.from(testList)//gettwitter_schemaPipe 
  val writeToPackedAvro = 
    twitter_schemas
      .map{twitter_schema => twitter_schema.copy(username = "My new name is " + twitter_schema.username) }
//      .map{twitter_schema => twitter_schema.copy(username = ("My new name is " + twitter_schema.username.get)) }
      .debug
      .write(PackedAvroSource[Twitter_Schema]("data/twitter.avro"))

}
