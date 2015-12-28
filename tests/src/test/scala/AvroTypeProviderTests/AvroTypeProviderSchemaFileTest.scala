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
      val record1 = twitter_schema("Achilles", "ow", 2L)
      val record2 = twitter_schema("Tortoise", "ho", 3L)
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }
}
