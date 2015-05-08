package test
import org.specs2.mutable.Specification

import java.io._

import org.apache.avro.generic._
import org.apache.avro.specific._
import org.apache.avro.Schema
import org.apache.avro.file._

import collection.JavaConversions._

class AvroRecordDataFileStreamTest extends Specification {

  "A case class with in the default package (i.e. without a namespace)" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest00(1)
      val schema = AvroRecordTest00.SCHEMA$

      val baos   = new ByteArrayOutputStream() 

      
      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest00]
      val dataFileWriter = new DataFileWriter[AvroRecordTest00](userDatumWriter)
      dataFileWriter.create(schema, baos)
      dataFileWriter.append(record)
      dataFileWriter.close

      val bais = new ByteArrayInputStream( baos.toByteArray )
      val userDatumReader = new SpecificDatumReader[AvroRecordTest00](schema)
      val dataFileReader = new DataFileStream[AvroRecordTest00](bais, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

