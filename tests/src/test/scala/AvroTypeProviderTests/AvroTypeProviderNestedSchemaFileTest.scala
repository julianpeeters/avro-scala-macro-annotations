package test
// Specs2
import org.specs2.mutable.Specification

import java.io.File

import org.apache.avro.generic._
import org.apache.avro.specific._
import org.apache.avro.Schema
import org.apache.avro.Schema.{Type => AvroType}
import org.apache.avro.file._

import com.julianpeeters.avro.annotations._



class AvroTypeProviderNestedSchemaFileTest extends Specification {

  "A case class with types provided from a .avsc avro schema file" should {
    "serialize and deserialize correctly" in {

      val record = TestMessage("Achilles", MetaData("ow", "12345"))

      val file = File.createTempFile("AvroTypeProviderNestedSchemaFileTest", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[TestMessage]
      val dataFileWriter = new DataFileWriter[TestMessage](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[TestMessage](schema)
      val dataFileReader = new DataFileReader[TestMessage](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)

    }
  }
}