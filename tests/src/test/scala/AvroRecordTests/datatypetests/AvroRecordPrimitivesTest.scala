package test

import org.specs2.mutable.Specification

class AvroRecordPrimitivesTest extends Specification {


    "A case class with an `Int` field" should {
      "deserialize correctly" in {
        val record1 = AvroRecordTest00(1)
        val record2 = AvroRecordTest00(2)
        val records = List(record1, record2)
        TestUtil.verifyWriteAndRead(records)
      }
    }

    "A case class with an `Float` field" should {
      "deserialize correctly" in {
        val record1 = AvroRecordTest01(1F)
        val record2 = AvroRecordTest01(2F)
        val records = List(record1, record2)
        TestUtil.verifyWriteAndRead(records)
      }
    }

    "A case class with an `Long` field" should {
      "deserialize correctly" in {
        val record1 = AvroRecordTest02(1L)
        val record2 = AvroRecordTest02(2L)
        val records = List(record1, record2)
        TestUtil.verifyWriteAndRead(records)
      }
    }

    "A case class with an `Double` field" should {
      "deserialize correctly" in {
        val record1 = AvroRecordTest03(1D)
        val record2 = AvroRecordTest03(2D)
        val records = List(record1, record2)
        TestUtil.verifyWriteAndRead(records)
      }
    }

    "A case class with an `Boolean` field" should {
      "deserialize correctly" in {
        val record1 = AvroRecordTest04(true)
        val record2 = AvroRecordTest04(false)
        val records = List(record1, record2)
        TestUtil.verifyWriteAndRead(records)
      }
    }

    "A case class with an `String` field" should {
      "deserialize correctly" in {
        val record1 = AvroRecordTest05("hello world")
        val record2 = AvroRecordTest05("hello galaxy")
        val records = List(record1, record2)
        TestUtil.verifyWriteAndRead(records)
      }
    }

    "A case class with an `Null` field" should {
      "deserialize correctly" in {
        val record1 = AvroRecordTest06(null)
        val record2 = AvroRecordTest06(null)
        val records = List(record1, record2)
        TestUtil.verifyWriteAndRead(records)
      }
    }


}
