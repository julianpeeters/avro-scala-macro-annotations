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
import TDsl._


class PackedAvroReadJob(args: Args) extends Job(args) {

  /**
    * Read data from PackedAvro
    */
  PackedAvroSource[Person]("data/PackedAvroOutput.avro")
    .flatMap{rec: Person => tokenize(rec.name)}
    .groupBy(identity)
    .size
    .write(TypedTsv[(String, Long)]("data/AvroWordcountOutput.tsv"))

  // Split a piece of text into individual words.
  def tokenize(text : String) : Array[String] = {
    // Lowercase each word and remove punctuation.
    text.toLowerCase.replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+").filter(_.length > 0)
  }


}
