package com.miguno.avro.relative

import org.specs2.mutable.Specification

import test.TestUtil

import com.julianpeeters.avro.annotations._

@AvroTypeProvider("../../resources/AvroTypeProviderTestRelativeSchemaFilePath.avsc")
@AvroRecord
case class Tweet()

class AvroTypeProviderRelativeSchemaFilePathTest extends Specification {

  "A case class with types provided from a .avsc avro schema file with path relative to the class' position" should {
    "serialize and deserialize correctly" in {
      val record1 = Tweet("Achilles", "ow", 2L)
      val record2 = Tweet("Tortoise", "ho", 3L)
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }
}
