
package test

import org.specs2.mutable.Specification

class AvroRecord2ArityHomoTest extends Specification {

  "A case class with an `Int` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest14(1, 2)
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `Float` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest15(1F, 2F)
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `Long` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest16(1L, 2L)
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `Double` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest17(1D, 2D)
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `Boolean` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest18(true, false)
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `String` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest19("1", "2")
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `Null` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest20(null, null)
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `List[String]` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest24(
        List("mekka", "lekka", "hi"),
        List("mekka", "hiney", "ho")
      )
      TestUtil.verifyWriteAndRead(record)
    }
  }


  "A case class with an `List[Int]` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest25(List(1, 2), List(3,4))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `Option[String]` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest26(Some("sun"), Some("moon"))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `Option[Int]` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest27(Some(1), Some(2))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with two Map[Int, Int] fields" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTestMap04(Map("Gorgonzola"->2), Map("Cheddar"->4))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with two Map[Int, String] fields" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTestMap05(
        Map("Havana"->"Cuba"),
        Map("Blue World"->"series")
      )
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with two Map[String, Option[List[Int]]] fields" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTestMap06(
        Map("Olala"->Some(List(1,4))),
        Map("Rumpole"->None)
      )
      TestUtil.verifyWriteAndRead(record)
    }
  }
}
