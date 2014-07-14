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

// Specs2
import org.specs2.mutable.Specification

import java.io.File

import org.apache.avro.generic._
import org.apache.avro.specific._
import org.apache.avro.Schema
import org.apache.avro.Schema.{Type => AvroType}
import org.apache.avro.file._

import com.julianpeeters.avro.annotations._

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestSchemaFile.avsc")
@AvroRecord
case class twitter_schema()

class AvroTypeProviderSchemaFileTest extends Specification {

  "A case class with types provided from a .avsc avro schema file" should {
    "serialize and deserialize correctly" in {

      val record = twitter_schema("Achilles", "ow", 2L)

      val file = File.createTempFile("AvroTypeProviderSchemaFileTest", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[twitter_schema]
      val dataFileWriter = new DataFileWriter[twitter_schema](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[twitter_schema](schema)
      val dataFileReader = new DataFileReader[twitter_schema](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

