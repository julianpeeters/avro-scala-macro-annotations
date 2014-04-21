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
/*
@AvroTypeProvider("data/input.avro")
@AvroRecord
case class rec()

@AvroTypeProvider("data/input.avro")
@AvroRecord
case class MyRecord()


@AvroTypeProvider("data/enron_head.avro")
@AvroRecord
case class TUPLE_4()

@AvroTypeProvider("data/enron_head.avro")
@AvroRecord
case class TUPLE_3()

@AvroTypeProvider("data/enron_head.avro")
@AvroRecord
case class TUPLE_2()

@AvroTypeProvider("data/enron_head.avro")
@AvroRecord
case class TUPLE_1()

@AvroTypeProvider("data/enron_head.avro")
@AvroRecord
case class TUPLE_0()

*/

/**
 * hadoop jar chapter3-0-jar-with-dependencies.jar com.twitter.scalding.Tool -Dmapred.output.compress=true AvroExample --hdfs
 *
 */
class PackedAvroReadJob(args: Args) extends Job(args) {
//println(twitter_schema().schema)

  /**
    * Read data from PackedAvro
    */
//  PackedAvroSource[Twitter_Schema]( """data/PackedAvroOutput.avro""")
  PackedAvroSource[Twitter_Schema]( """data/twitter.avro""")
//  PackedAvroSource[twitter_schema]( """data/twitter.avro""")
 // PackedAvroSource[TUPLE_0]( """data/enron_head.avro""")
     .read.debug.write(Tsv("""data/TEST-READING-PACKED"""))
   //  .read.debug.write(TypedTsv[Option[String]]("""data/TEST-READING-PACKED"""))

}
