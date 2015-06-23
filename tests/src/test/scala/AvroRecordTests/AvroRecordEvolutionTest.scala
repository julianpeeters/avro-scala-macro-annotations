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

@AvroRecord
case class AvroRecordTestEvolution00(var x: Int, var y: String = "NONE")

@AvroRecord
case class AvroRecordTestEvolution01(var x: Int)

class AvroRecordEvolutionTest extends Specification {

  "A case class that was serialized with a single field" should {
    "deserialize correctly if an additional field is added with a default value" in {

      val record = AvroRecordTestEvolution00(1, "NONE")

      val file = new File("tests/src/test/resources/evolution/AvroRecordTestEvolution00.avro")
        
      val schema = AvroRecordTestEvolution00.SCHEMA$
      val userDatumReader = new SpecificDatumReader[AvroRecordTestEvolution00](schema)
      val dataFileReader = new DataFileReader[AvroRecordTestEvolution00](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)

    }
  }



  "A case class that was serialized with two fields with default values" should {
    "deserialize correctly if a field is removed from the record model" in {

      val record = AvroRecordTestEvolution01(1)

      val file = new File("tests/src/test/resources/evolution/AvroRecordTestEvolution00.avro")
        
      val schema = AvroRecordTestEvolution01.SCHEMA$
      val userDatumReader = new SpecificDatumReader[AvroRecordTestEvolution01](schema)
      val dataFileReader = new DataFileReader[AvroRecordTestEvolution01](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)

    }
  }

}