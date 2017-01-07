package com.miguno.avro.differentns

import org.specs2.mutable.Specification

import test.TestUtil

import com.julianpeeters.avro.annotations._

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestDifferentNamespace.avsc")
@AvroRecord
case class Person()

// Nested namespace
package twitter {
  @AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestDifferentNamespace.avsc")
  @AvroRecord
  case class Tweet()
}

class AvroTypeProviderDifferentNamespaceTest extends Specification {

  "A case class with types provided from a .avsc avro schema file referencing another class in a different namespace" should {
    "serialize and deserialize correctly" in {
      val record = twitter.Tweet(Person(id = 1, name = "John"), "Yo!")
      TestUtil.verifyWriteAndRead(List(record))
    }
  }
}
