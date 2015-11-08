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
case class AvroRecordTestEncoder01(var i: Int, var j: Option[Int])

class AvroRecordEncoderTest extends Specification {


  "A case class " should {
    "serialize and deserialize correctly via encoder and decoder" in {

      val record = AvroRecordTest00(0)
      val schema = AvroRecordTest00.SCHEMA$

      val w = new SpecificDatumWriter[AvroRecordTest00](schema)

      val out = new java.io.ByteArrayOutputStream()
      val encoder = EncoderFactory.get().binaryEncoder(out, null)

      w.write(record, encoder)

      encoder.flush

      val ba = out.toByteArray

      ba.size must ===(1)
      ba(0) must ===(0)

      out.close

      val reader = new SpecificDatumReader[AvroRecordTest00](schema)

      val decoder = DecoderFactory.get().binaryDecoder(ba, null)
      val decoded = reader.read(null, decoder)

      decoded must ===(record)

    }
  }


  "A case class with two fields, Int and Option[Int]" should {
    "serialize and deserialize correctly via encoder and decoder" in {

      val record = AvroRecordTestEncoder01(0, None)
      val schema = AvroRecordTestEncoder01.SCHEMA$

      val w = new SpecificDatumWriter[AvroRecordTestEncoder01](schema)

      val out = new java.io.ByteArrayOutputStream()
      val encoder = EncoderFactory.get().binaryEncoder(out, null)

      w.write(record, encoder)

      encoder.flush

      val ba = out.toByteArray

      ba.size must ===(2)
      ba(0) must ===(0)
      ba(1) must ===(0)


      out.close

      val reader = new SpecificDatumReader[AvroRecordTestEncoder01](schema)

      val decoder = DecoderFactory.get().binaryDecoder(ba, null)
      val decoded = reader.read(null, decoder)

      decoded must ===(record)
    }
  }



}
