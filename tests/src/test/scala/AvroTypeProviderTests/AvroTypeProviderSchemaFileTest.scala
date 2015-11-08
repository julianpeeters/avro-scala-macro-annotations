package com.miguno.avro

import org.specs2.mutable.Specification

import test.TestUtil

import com.julianpeeters.avro.annotations._

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestSchemaFile.avsc")
@AvroRecord
case class twitter_schema()

class AvroTypeProviderSchemaFileTest extends Specification {

  "A case class with types provided from a .avsc avro schema file" should {
    "serialize and deserialize correctly" in {
      val record = twitter_schema("Achilles", "ow", 2L)
      TestUtil.verifyWriteAndRead(record)
    }
  }
}
