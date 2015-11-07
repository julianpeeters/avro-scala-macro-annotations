package com.miguno.avro

import org.specs2.mutable.Specification

import java.io.File

import org.apache.avro.generic._
import org.apache.avro.specific._
import org.apache.avro.Schema
import org.apache.avro.Schema.{Type => AvroType}
import org.apache.avro.file._

import com.julianpeeters.avro.annotations._

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestSchemaUnion.avsc")
@AvroRecord
case class User()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestSchemaUnion.avsc")
@AvroRecord
case class Tweet()

class AvroTypeProviderSchemaUnionTest extends Specification {

  "Case classes generated from an .avsc file containing a union of schemas" should {
    "serialize and deserialize correctly types referencing other items in the union" in {

      val record = Tweet("This is Hector. The fool who thought he killed Achilles.",
        user = User("Achilles", 1), user_mentions = List(User("Hector", 2), User("Achilles", 1)))

      val file = File.createTempFile("AvroTypeProviderSchemaUnionTest1", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[Tweet]
      val dataFileWriter = new DataFileWriter[Tweet](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[Tweet](schema)
      val dataFileReader = new DataFileReader[Tweet](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}
