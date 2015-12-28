package test

import org.specs2.mutable.Specification

class AvroRecordNestedTest extends Specification {

  "A case class with a `List[List[String]]` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest28(List(List("blackbird.grackle")))
      val record2 = AvroRecordTest28(List(List("pheasant.turkey")))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with a `List[List[Int]]` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest29(List(List(1, 2)))
      val record2 = AvroRecordTest29(List(List(3, 4)))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an `Option[List[String]]` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest30(Some(List("starling.oriole")))
      val record2 = AvroRecordTest30(Some(List("buzzard.hawk")))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an `Option[List[Int]]` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest31(Some(List(5, 6)))
      val record2 = AvroRecordTest31(Some(List(7, 8)))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with a `List[Option[String]]` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest32(List(Some("cowbird")))
      val record2 = AvroRecordTest32(List(Some("cuckoo")))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with a `List[Option[Int]]` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest33(List(Some(1)))
      val record2 = AvroRecordTest33(List(Some(2)))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with a `Option[List[Option[String]]]` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest34(Some(List(Some("cowbird"), None)))
      val record2 = AvroRecordTest34(Some(List(Some("lark"), None)))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with a `Option[List[Option[Int]]]` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest35(Some(List(Some(1), None)))
      val record2 = AvroRecordTest35(Some(List(Some(2), None)))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with a `List[Option[List[Option[String]]]]` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest36(List(None, Some(List(Some("cowbird"), None))))
      val record2 = AvroRecordTest36(List(None, Some(List(Some("goldfinch"), None))))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with a `List[Option[List[Option[Int]]]]` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest37(List(None, Some(List(Some(1), None))))
      val record2 = AvroRecordTest37(List(None, Some(List(Some(2), None))))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with a Map[Int, Map[Int, Int]] field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTestMap07(Map("art"->Map("explode"->4)))
      val record2 = AvroRecordTestMap07(Map("science"->Map("define"->4)))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with a List[Map[String, Map[Int, String]]] field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTestMap08(List(Map("hare"->Map("serpent"->"eagle"))))
      val record2 = AvroRecordTestMap08(List(Map("snake"->Map("bear"->"deer"))))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with a Option[Map[String, Option[List[String]]]] field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTestMap09(Some(Map("Eje"->None)))
      val record2 = AvroRecordTestMap09(Some(Map("Rayo"->None)))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }
}
