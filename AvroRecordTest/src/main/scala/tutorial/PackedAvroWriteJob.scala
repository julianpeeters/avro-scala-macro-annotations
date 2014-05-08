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
//import scala.collection.JavaConversions._
import com.julianpeeters.avro.annotations._
import com.twitter.scalding._
import com.twitter.scalding.avro.{PackedAvroSource, UnpackedAvroSource}
import TDsl._

//import com.gensler.scalavro.types._

import org.apache.avro.Schema
import org.apache.avro.specific.{SpecificRecord, SpecificRecordBase}
//import conversions._

//@AvroRecord
//case class Twitter_Schema(var tweet: Int)

@AvroRecord
case class A(var i: Int)

@AvroRecord
case class B(var a: Option[A])


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
val aa = A(1)
val bb = B(Some(aa))
val testList = List(bb )
   // Twitter_Schema( Some(List(Some(List(Some(1), Some(2))))) ),
    //Twitter_Schema(Some(List(None) ))   )

  /**
    * Write dummy data to PackedAvro
    */
//  val twitter_schemas: TypedPipe[Twitter_Schema] = TypedPipe.from(testList)//gettwitter_schemaPipe 
  val twitter_schemas: TypedPipe[B] = TypedPipe.from(testList)//gettwitter_schemaPipe 
  val writeToPackedAvro = 
    twitter_schemas
      .map{twitter_schema => twitter_schema}//.copy(username = List("My new name is " + twitter_schema.username.head)) }
//      .map{twitter_schema => twitter_schema.copy(username = ("My new name is " + twitter_schema.username.get)) }
      .debug
//      .write(PackedAvroSource[Twitter_Schema]("data/twitter.avro"))
      .write(PackedAvroSource[B]("data/B.avro"))

}
