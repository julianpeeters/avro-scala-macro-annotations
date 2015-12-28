
package test

import org.specs2.mutable.Specification

class AvroRecord2ArityHomoTest extends Specification {

  "A case class with an `Int` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest14(1, 2)
      val record2 = AvroRecordTest14(3, 4)
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an `Float` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest15(1F, 2F)
      val record2 = AvroRecordTest15(3F, 4F)
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an `Long` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest16(1L, 2L)
      val record2 = AvroRecordTest16(3L, 4L)
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an `Double` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest17(1D, 2D)
      val record2 = AvroRecordTest17(3D, 4D)
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an `Boolean` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest18(true, false)
      val record2 = AvroRecordTest18(false, true)
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an `String` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest19("1", "2")
      val record2 = AvroRecordTest19("1", "2")
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an `Null` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest20(null, null)
      val record2 = AvroRecordTest20(null, null)
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an `List[String]` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest24(List("mekka.lekka.hi"), List("mekka.hiney.ho"))
      val record2 = AvroRecordTest24(List("time"), List("travel"))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an `List[Int]` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest25(List(1, 2), List(3,4))
      val record2 = AvroRecordTest25(List(5, 6), List(7,8))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an `Option[String]` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest26(Some("sun"), Some("moon"))
      val record2 = AvroRecordTest26(Some("day"), Some("night"))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an `Option[Int]` field in the second position" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest27(Some(1), Some(2))
      val record2 = AvroRecordTest27(Some(3), Some(4))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with two Map[Int, Int] fields" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTestMap04(Map("Gorgonzola"->2), Map("Cheddar"->4))
      val record2 = AvroRecordTestMap04(Map("Gouda"->5), Map("Swiss"->6))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with two Map[Int, String] fields" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTestMap05(Map("Havana"->"Cuba"), Map("World"->"series"))
      val record2 = AvroRecordTestMap05(Map("Bogota"->"Colombia"), Map("time"->"series"))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with two Map[String, Option[List[Int]]] fields" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTestMap06(Map("Olala"->Some(List(1,4))), Map("Rumpole"->None))
      val record2 = AvroRecordTestMap06(Map("Cran"->Some(List(3,5))), Map("Doc"->None))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }
}
