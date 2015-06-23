package test

import org.specs2.mutable.Specification
import com.julianpeeters.avro.annotations._

import java.io.File

import org.apache.avro.io.{DecoderFactory, EncoderFactory}
import org.apache.avro.generic.GenericData.Record
import org.apache.avro.generic._
import org.apache.avro.specific._
import org.apache.avro.Schema
import org.apache.avro.Schema.{Type => AvroType}
import org.apache.avro.file._

@AvroTypeProvider("tests/src/test/resources/evolution/AvroTypeProviderTestEvolution00.avro")
@AvroRecord
case class AvroTypeProviderTestEvolution00(var y: String = "NONE") //field x: Int is provided

class AvroTypeProviderEvolutionTest extends Specification {

  "A case class that was serialized with a single field" should {
    "deserialize correctly if an additional field is added with a default value" in {

      val record = AvroTypeProviderTestEvolution00(1, "NONE")

      val file = new File("tests/src/test/resources/evolution/AvroTypeProviderTestEvolution00.avro")
        
      val schema = AvroTypeProviderTestEvolution00.SCHEMA$
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTestEvolution00](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTestEvolution00](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)

    }
  }


}