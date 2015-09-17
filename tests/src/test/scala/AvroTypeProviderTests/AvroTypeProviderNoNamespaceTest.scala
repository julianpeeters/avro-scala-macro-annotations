
import org.specs2.mutable.Specification

import java.io.File

import org.apache.avro.generic._
import org.apache.avro.specific._
import org.apache.avro.Schema
import org.apache.avro.Schema.{Type => AvroType}
import org.apache.avro.file._

import com.julianpeeters.avro.annotations._

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestNoNamespace.avro")
@AvroRecord
case class AvroTypeProviderTestNoNamespace()

class AvroTypeProviderNoNamespaceTest extends Specification {

  "A case class with in the default package (i.e. without a namespace)" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTestNoNamespace(1)

      val file = new File("tests/src/test/resources/AvroTypeProviderTestNoNamespace.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTestNoNamespace](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTestNoNamespace](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}
