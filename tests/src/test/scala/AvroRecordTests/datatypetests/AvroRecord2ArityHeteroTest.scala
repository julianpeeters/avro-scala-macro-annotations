package test

import org.specs2.mutable.Specification

class AvroRecord2ArityHeteroTest extends Specification {

  "A case class with an `Int` field coexisting with a non-`Int` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest48(1, "bonjour")
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `String` field coexisting with a non-`Int` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest49("bueno", 2)
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `Option[String]` field coexisting with an `Option[Int]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest50(Some("tropics"), Some(3))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `Option[Int]` field coexisting with an `Option[String]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest51(Some(4), Some("level"))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with a `List[String]` field coexisting with a `List[Int]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest52(List("am", "pm"), List(5,6))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `List[Int]` field coexisting with a `List[String]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest53(List(7, 8), List("bon", "sois"))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `Option[List[Option[String]]]` field coexisting with a `Option[List[Option[Int]]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest54(Some(List(Some("bronco"), None)), Some(List(Some(9), None)))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `Option[List[Option[Int]]]` field coexisting with a `Option[List[Option[String]]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest55(Some(List(Some(10), None)), Some(List(Some("bronca"), None)))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `List[Option[List[Option[String]]]]` field coexisting with a `List[Option[List[Option[Int]]]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest56(List(Some(List(Some("tibetan"), None)), None), List(Some(List(Some(11), None)), None))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `Int` field coexisting with a non-`Int` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest57(List(Some(List(Some(12), None)), None), List(Some(List(Some("fire"), None)), None))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with two differing nested Map fields" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTestMap11(Map("one"->Map("two"->3)), List(Map("state"->Map("knowledge"->"power"))))
      TestUtil.verifyWriteAndRead(record)
    }
  }
}
