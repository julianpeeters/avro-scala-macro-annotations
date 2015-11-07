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
case class PictureSize()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestSchemaUnion.avsc")
@AvroRecord
case class Tweet()

class AvroTypeProviderSchemaUnionTest extends Specification {

  "Case classes generated from an .avsc file containing a union of schemas" should {
    "serialize and deserialize types referencing other items in the union" in {

      val record = Tweet("This is Hector. The fool who thought he killed Achilles.",
        user = User("Achilles", 1), user_mentions = List(User("Hector", 2), User("Achilles", 1)))

      verifyWriteAndRead(record)
    }

    "serialize and deserialize types which aren't referenced by any other item in the union" in {

      val size = PictureSize(300.0, 172.0)

      verifyWriteAndRead(size)
    }
  }

  def verifyWriteAndRead[T <: SpecificRecordBase](record: T) = {
    val file = File.createTempFile(s"AvroTypeProviderSchemaUnionTest${record.getClass.getName}", "avro")
        file.deleteOnExit()

    val userDatumWriter = new SpecificDatumWriter[T]
    val dataFileWriter = new DataFileWriter[T](userDatumWriter)
      dataFileWriter.create(record.getSchema(), file);
      dataFileWriter.append(record);
      dataFileWriter.close();

    val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
    val userDatumReader = new SpecificDatumReader[T](schema)
    val dataFileReader = new DataFileReader[T](file, userDatumReader)
    val sameRecord = dataFileReader.next()

    sameRecord must ===(record)
  }
}
