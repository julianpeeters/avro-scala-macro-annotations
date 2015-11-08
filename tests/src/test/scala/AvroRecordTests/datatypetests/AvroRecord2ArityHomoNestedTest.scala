package test

import org.specs2.mutable.Specification

class AvroRecord2ArityHomoNestedTest extends Specification {

  "A case class with more than one `List[List[String]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest38(
        List(
          List("hi", "bye"),
          List("yay", "nay")
        ),
        List(
          List("one", "two"),
          List("three", "four")
        )
      )
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with more than one `List[List[String]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest39(
        List(
          List(1, 2),
          List(3, 4)
        ),
        List(
          List(5, 6),
          List(7, 8)
        )
      )
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with more than one `Option[List[String]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest40(
        Some(List("up", "down")),
        Some(List("left", "right"))
      )
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with more than one `Option[List[Int]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest41(Some(List(1, 2)), Some(List(3, 4)))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with more than one `List[Option[String]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest42(
        List(None, Some("red")),
        List(Some("blue"), None)
      )
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with more than one `List[Option[Int]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest43(List(Some(1), None), List(Some(3), None))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with more than one `Option[List[Option[String]]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest44(
        Some(List(Some("gold"), None)),
        Some(List(Some("silver"), None))
      )
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with more than one `Option[List[Option[Int]]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest45(
        Some(List(Some(8), None)),
        Some(List(Some(10), None))
      )
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with more than one `List[Option[List[String]]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest46(
        List(None, Some(List(Some("green"), None))),
        List(None, Some(List(None, Some("yellow"))))
      )
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with more than one `List[Option[List[Int]]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest47(
        List(None, Some(List(Some(2), None))),
        List(None, Some(List(None, Some(4))))
      )
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with two Map[String, Map[String, Int]] fields" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTestMap10(
        Map("glory"->Map("kitty"->3)),
        Map("pride"->Map("doggy"->4))
      )
      TestUtil.verifyWriteAndRead(record)
    }
  }
}
