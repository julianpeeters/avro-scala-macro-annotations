
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

