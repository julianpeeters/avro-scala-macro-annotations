package test

import org.specs2.mutable.Specification

class AvroTypeProvider2ArityHomoTest extends Specification {

  "A case class with an `Int` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest14(1, 2)
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an `Float` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest15(1F, 2F)
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an `Long` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest16(1L, 2L)
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an `Double` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest17(1D, 2D)
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an `Boolean` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest18(true, false)
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an `String` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest19("1", "2")
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an `Null` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest20(null, null)
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an `List[String]` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest24(List("mekka.lekka.hi"), List("mekka.hiney.ho"))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an `List[Int]` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest25(List(1, 2), List(3,4))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an `Option[String]` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest26(Some("sun"), Some("moon"))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an `Option[Int]` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest27(Some(1), Some(2))
      TestUtil.verifyRead(record)
    }
  }
}
