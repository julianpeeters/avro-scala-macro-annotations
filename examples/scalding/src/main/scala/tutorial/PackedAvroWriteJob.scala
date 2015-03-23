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
import com.twitter.scalding.avro.PackedAvroSource
import TDsl._

class PackedAvroWriteJob(args: Args) extends Job(args) {

  /**
   * Dummy data
   */
 val testList = List(
    Person("Oberon", 425),
    Person("Miranda", 419),
    Person("Titania", 424) )

  /**
    * Write dummy data to PackedAvro
    */
  val persons: TypedPipe[Person] = TypedPipe.from(testList)//getPersonPipe 
  val writeToPackedAvro = 
    persons
      .map{Person => Person.copy(name = "Dr. " + Person.name) }
      .debug
      .write(PackedAvroSource[Person]("data/PackedAvroOutput.avro"))

}
