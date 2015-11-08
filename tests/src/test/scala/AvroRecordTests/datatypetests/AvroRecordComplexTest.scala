package test

import org.specs2.mutable.Specification

class AvroRecordComplexTest extends Specification {

  "A case class with an empty `Option[String]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest07(None)
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an empty `Option[Int]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest08(None)
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `List[String]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest10(List("head", "tail"))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `List[Int]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest11(List(1, 2))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `Option[String]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest12(Some("I'm here"))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `Option[Int]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest13(Some(1))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with a `Map[String, Int]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTestMap01(Map("bongo"->2))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with a `Map[String, String]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTestMap02(Map("4"->"four"))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with a `Map[String, List[Int]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTestMap03(Map("sherpa"->Some(List(5,6))))
      TestUtil.verifyWriteAndRead(record)

    }
  }
}
