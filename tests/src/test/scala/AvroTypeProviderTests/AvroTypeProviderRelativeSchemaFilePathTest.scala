package com.miguno.avro.relative

import org.specs2.mutable.Specification
import java.io.File


import org.apache.avro.generic._
import org.apache.avro.specific._
import org.apache.avro.Schema
import org.apache.avro.file._

import com.julianpeeters.avro.annotations._

@AvroTypeProvider("../../resources/AvroTypeProviderTestRelativeSchemaFilePath.avsc")
@AvroRecord
case class Tweet()

class AvroTypeProviderRelativeSchemaFilePathTest extends Specification {

  "A case class with types provided from a .avsc avro schema file with path relative to the class' position" should {
    "serialize and deserialize correctly" in {
      val record = Tweet("Achilles", "ow", 2L)
      
      val file = File.createTempFile("AvroTypeProviderTestRelativeSchemaFilePath", "avro")
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

