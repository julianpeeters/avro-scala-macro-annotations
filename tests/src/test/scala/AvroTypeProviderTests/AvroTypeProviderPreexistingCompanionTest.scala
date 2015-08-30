package test

import org.specs2.mutable.Specification

import java.io.File

import org.apache.avro.generic._
import org.apache.avro.specific._
import org.apache.avro.Schema
import org.apache.avro.Schema.{Type => AvroType}
import org.apache.avro.file._

class AvroTypeProviderCompanionTest extends Specification {

  "A case class that has a preexisting companion object" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordPreexistingCompanionTest00(1)

      val file = File.createTempFile("AvroRecordPreexistingCompanionTest00", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordPreexistingCompanionTest00]
      val dataFileWriter = new DataFileWriter[AvroRecordPreexistingCompanionTest00](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordPreexistingCompanionTest00](schema)
      val dataFileReader = new DataFileReader[AvroRecordPreexistingCompanionTest00](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      AvroRecordPreexistingCompanionTest00.o must ===(5)

      sameRecord must ===(record)
      
    }
  }
}