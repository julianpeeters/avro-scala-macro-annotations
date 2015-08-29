package test

import org.specs2.mutable.Specification

import java.io.File

import org.apache.avro.generic._
import org.apache.avro.specific._
import org.apache.avro.Schema
import org.apache.avro.Schema.{Type => AvroType}
import org.apache.avro.file._

class AvroRecordExtendedTest extends Specification {

  "A case class that extends an arbitrary trait" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordExtendedTest01(1)

      val file = File.createTempFile("AvroRecordExtendedTest01", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordExtendedTest01]
      val dataFileWriter = new DataFileWriter[AvroRecordExtendedTest01](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordExtendedTest01](schema)
      val dataFileReader = new DataFileReader[AvroRecordExtendedTest01](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord.e must ===(10)

      sameRecord must ===(record)
    }
  }
}