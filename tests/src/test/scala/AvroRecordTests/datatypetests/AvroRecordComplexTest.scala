package test

import org.specs2.mutable.Specification

class AvroRecordComplexTest extends Specification {

  "A case class with an empty `Option[String]` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest07(None)
      val record2 = AvroRecordTest07(None)
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an empty `Option[Int]` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest08(None)
      val record2 = AvroRecordTest08(None)
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an `List[String]` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest10(List("head", "tail"))
      val record2 = AvroRecordTest10(List("top", "bottom"))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an `List[Int]` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest11(List(1, 2))
      val record2 = AvroRecordTest11(List(3, 4))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an `Option[String]` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest12(Some("I'm here"))
      val record2 = AvroRecordTest12(Some("I'm there"))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an `Option[Int]` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest13(Some(1))
      val record2 = AvroRecordTest13(Some(2))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with a `Map[String, Int]` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTestMap01(Map("bongo"->2))
      val record2 = AvroRecordTestMap01(Map("mongo"->3))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with a `Map[String, String]` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTestMap02(Map("4"->"four"))
      val record2 = AvroRecordTestMap02(Map("5"->"five"))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with a `Map[String, List[Int]]` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTestMap03(Map("sherpa"->Some(List(5,6))))
      val record2 = AvroRecordTestMap03(Map("autobus"->Some(List(8,9))))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }
}
