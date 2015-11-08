package test

import org.specs2.mutable.Specification

class AvroTypeProvider2ArityHomoNestedTest extends Specification {

  "A case class with more than one `List[List[String]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest38(
        List(
          List("hi.bye"),
          List("yay.nay")
        ),
        List(
          List("one.two"),
          List("three.four")
        )
      )
      TestUtil.verifyRead(record)
    }
  }

  "A case class with more than one `List[List[String]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest39(
        List(
          List(1, 2),
          List(3, 4)
        ),
        List(
          List(5, 6),
          List(7, 8)
        )
      )
      TestUtil.verifyRead(record)
    }
  }

  "A case class with more than one `Option[List[String]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest40(Some(List("up.down")), Some(List("left.right")))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with more than one `Option[List[Int]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest41(Some(List(1, 2)), Some(List(3, 4)))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with more than one `List[Option[String]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest42(List(None, Some("red")), List(Some("blue"), None))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with more than one `List[Option[Int]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest43(List(Some(1), None), List(Some(3), None))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with more than one `Option[List[Option[String]]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest44(Some(List(Some("gold"), None)), Some(List(Some("silver"), None)))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with more than one `Option[List[Option[Int]]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest45(Some(List(Some(8), None)), Some(List(Some(10), None)))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with more than one `List[Option[List[String]]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest46(List(None, Some(List(Some("green"), None))), List(None, Some(List(None, Some("yellow")))) )
      TestUtil.verifyRead(record)
    }
  }

/* //TODO make readable file for this class - not very urgent since this field type is tested in other contexts also
class AvroTypeProvider47Test extends Specification {

  "A case class with more than one `List[Option[List[Int]]]` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTest47(List(None, Some(List(Some(2), None))), List(None, Some(List(None, Some(4)))) )
      TestUtil.verifyRead(record)
    }
  }
  */
}
