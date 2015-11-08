package test

import org.specs2.mutable.Specification

class AvroRecordNestedTest extends Specification {

  "A case class with a `List[List[String]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest28(List(List("blackbird", "grackle")))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with a `List[List[Int]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest29(List(List(1, 2)))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `Option[List[String]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest30(Some(List("starling", "oriole")))
      TestUtil.verifyWriteAndRead(record)
    }
  }


  "A case class with an `Option[List[Int]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest31(Some(List(5, 6)))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with a `List[Option[String]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest32(List(Some("cowbird")))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with a `List[Option[Int]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest33(List(Some(1)))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with a `Option[List[Option[String]]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest34(Some(List(Some("cowbird"), None)))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with a `Option[List[Option[Int]]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest35(Some(List(Some(1), None)))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with a `List[Option[List[Option[String]]]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest36(List(None, Some(List(Some("cowbird"), None))))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with a `List[Option[List[Option[Int]]]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest37(List(None, Some(List(Some(1), None))))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with a Map[Int, Map[Int, Int]] field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTestMap07(Map("art"->Map("explode"->4)))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with a List[Map[String, Map[Int, String]]] field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTestMap08(List(Map("hare"->Map("serpent"->"eagle"))))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with a Option[Map[String, Option[List[String]]]] field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTestMap09(Some(Map("Eje"->None)))
      TestUtil.verifyWriteAndRead(record)
    }
  }
}
