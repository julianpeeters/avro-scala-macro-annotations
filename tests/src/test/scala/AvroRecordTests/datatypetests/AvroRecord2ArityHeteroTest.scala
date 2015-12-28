package test

import org.specs2.mutable.Specification

class AvroRecord2ArityHeteroTest extends Specification {

  "A case class with an `Int` field coexisting with a non-`Int` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest48(1, "bonjour")
      val record2 = AvroRecordTest48(2, "moshi")
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an `String` field coexisting with a non-`Int` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest49("bueno", 2)
      val record2 = AvroRecordTest49("hola", 3)
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an `Option[String]` field coexisting with an `Option[Int]` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest50(Some("tropics"), Some(3))
      val record2 = AvroRecordTest50(Some("equator"), Some(4))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an `Option[Int]` field coexisting with an `Option[String]` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest51(Some(4), Some("level"))
      val record2 = AvroRecordTest51(Some(5), Some("inclined"))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with a `List[String]` field coexisting with a `List[Int]` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest52(List("am.pm"), List(5,6))
      val record2 = AvroRecordTest52(List("time"), List(7,8))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an `List[Int]` field coexisting with a `List[String]` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest53(List(7, 8), List("bon.sois"))
      val record2 = AvroRecordTest53(List(9, 10), List("mon.amis"))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an `Option[List[Option[String]]]` field coexisting with a `Option[List[Option[Int]]]` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest54(Some(List(Some("bronco"), None)), Some(List(Some(9), None)))
      val record2 = AvroRecordTest54(Some(List(Some("bull"), None)), Some(List(Some(11), None)))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an `Option[List[Option[Int]]]` field coexisting with a `Option[List[Option[String]]]` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest55(Some(List(Some(10), None)), Some(List(Some("bronca"), None)))
      val record2 = AvroRecordTest55(Some(List(Some(12), None)), Some(List(Some("cow"), None)))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an `List[Option[List[Option[String]]]]` field coexisting with a `List[Option[List[Option[Int]]]]` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest56(List(Some(List(Some("tibetan"), None)), None), List(Some(List(Some(11), None)), None))
      val record2 = AvroRecordTest56(List(Some(List(Some("nepalese"), None)), None), List(Some(List(Some(13), None)), None))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an `Int` field coexisting with a non-`Int` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest57(List(Some(List(Some(12), None)), None), List(Some(List(Some("fire"), None)), None))
      val record2 = AvroRecordTest57(List(Some(List(Some(15), None)), None), List(Some(List(Some("ice"), None)), None))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with two differing nested Map fields" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTestMap11(Map("one"->Map("two"->3)), List(Map("state"->Map("knowledge"->"power"))))
      val record2 = AvroRecordTestMap11(Map("four"->Map("five"->6)), List(Map("country"->Map("truth"->"beauty"))))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }
}
