package test

import org.specs2.mutable.Specification

class AvroRecordPrimitivesTest extends Specification {

  "A case class with an `Int` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest00(1)
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `Float` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest01(1F)
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `Long` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest02(1L)
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `Double` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest03(1D)
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `Boolean` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest04(true)
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `String` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest05("hello world")
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `Null` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest06(null)
      TestUtil.verifyWriteAndRead(record)
    }
  }

}
