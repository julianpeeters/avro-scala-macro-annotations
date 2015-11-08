package test

import org.specs2.mutable.Specification

class AvroTypeProvider28Test extends Specification {

  "A case class with a `List[List[String]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest28(List(List("blackbird.grackle")))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with a `List[List[Int]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest29(List(List(1, 2)))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an `Option[List[String]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest30(Some(List("starling.oriole")))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an `Option[List[Int]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest31(Some(List(5, 6)))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with a `List[Option[String]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest32(List(Some("cowbird")))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with a `List[Option[Int]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest33(List(Some(1)))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with a `Option[List[Option[String]]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest34(Some(List(Some("cowbird"), None)))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with a `Option[List[Option[Int]]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest35(Some(List(Some(1), None)))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with a `List[Option[List[Option[String]]]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest36(List(None, Some(List(Some("cowbird"), None))))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with a `List[Option[List[Option[Int]]]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest37(List(None, Some(List(Some(1), None))))
      TestUtil.verifyRead(record)
    }
  }
}
