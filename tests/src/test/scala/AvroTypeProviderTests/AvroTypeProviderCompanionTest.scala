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

      val record = AvroTypeProviderPreexistingCompanionTest00(1)

      val file = File.createTempFile("AvroTypeProviderPreexistingCompanionTest00", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroTypeProviderPreexistingCompanionTest00]
      val dataFileWriter = new DataFileWriter[AvroTypeProviderPreexistingCompanionTest00](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderPreexistingCompanionTest00](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderPreexistingCompanionTest00](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      AvroTypeProviderPreexistingCompanionTest00.o must ===(5)

      sameRecord must ===(record)
      
    }
  }
}