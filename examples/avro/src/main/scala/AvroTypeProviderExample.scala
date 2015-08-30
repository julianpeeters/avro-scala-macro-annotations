/*
 * Copyright 2014 Julian Peeters
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



 package test

 import com.julianpeeters.avro.annotations._

import org.apache.avro.specific._
import org.apache.avro.generic._
import org.apache.avro.file._

import java.io.File

//Primitive Types
@AvroTypeProvider("src/main/avro/AvroTypeProviderTest00.avro")
@AvroRecord
case class AvroTypeProviderTest00()

object AvroTypeProviderExample extends App {
  val record = AvroTypeProviderTest00(1)

 val file = new File("src/main/avro/AvroTypeProviderTest00.avro")

 val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
 val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest00](schema)
 val dataFileReader = new DataFileReader[AvroTypeProviderTest00](file, userDatumReader)
 val sameRecord = dataFileReader.next()

  println("deserialized record is the same as a new record based on the schema in the file?: " + (sameRecord == record) )

}
