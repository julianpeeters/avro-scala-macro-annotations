
package com.miguno.avro

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
