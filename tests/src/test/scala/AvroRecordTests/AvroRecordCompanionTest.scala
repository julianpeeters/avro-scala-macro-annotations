package test

// Specs2
import org.specs2.mutable.Specification

import java.io.File

import org.apache.avro.generic._
import org.apache.avro.specific._
import org.apache.avro.Schema
import org.apache.avro.Schema.{Type => AvroType}
import org.apache.avro.file._


class AvroRecordCompanionTest extends Specification {

  "A case class' companion object" should {
    "be updated to have a SCHEMA$ field" in {

      val record = AvroRecordTest58(AvroRecordTest00(1))
      val schema = AvroRecordTest58.SCHEMA$

      schema must ===(record.getSchema())
    }
  }
}