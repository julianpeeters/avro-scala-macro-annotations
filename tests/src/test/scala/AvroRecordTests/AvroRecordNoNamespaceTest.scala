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

import com.julianpeeters.avro.annotations.AvroRecord

@AvroRecord
case class AvroRecordTestNoNamespace(var x: Int)
object AvroRecordTestNoNamespace {val y: Int = 3}

class AvroRecordNoNamespaceTest extends Specification {

  "A case class with in the default package (i.e. without a namespace)" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTestNoNamespace(1)

      val file = File.createTempFile("AvroRecordTestNoNamespace", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTestNoNamespace]
      val dataFileWriter = new DataFileWriter[AvroRecordTestNoNamespace](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTestNoNamespace](schema)
      val dataFileReader = new DataFileReader[AvroRecordTestNoNamespace](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

