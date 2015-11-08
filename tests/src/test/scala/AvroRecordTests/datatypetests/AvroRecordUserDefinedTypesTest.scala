package test

import org.specs2.mutable.Specification

class AvroRecordUserDefinedTypesTest extends Specification {

  "A case class with another record as a field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest58(AvroRecordTest00(1))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `Float` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest59(AvroRecordTest58(AvroRecordTest00(1)))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with an `Long` field" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest60(AvroRecordTest00(1), AvroRecordTest58(AvroRecordTest00(2)))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with a field that is list of a user-defined type" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest61(
        List(
          AvroRecordTest00(1),
          AvroRecordTest00(2)
        )
      )
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with a field that is list of a nested user-defined type" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest62(
        List(AvroRecordTest58(AvroRecordTest00(1)),
        AvroRecordTest58(AvroRecordTest00(2)))
      )
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with a field that is list of a nested user-defined type in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest63(
        List(
          AvroRecordTest00(1),
          AvroRecordTest00(2)),
          List(
            AvroRecordTest60(
              AvroRecordTest00(3),
              AvroRecordTest58(AvroRecordTest00(2))
            )
          )
        )

      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with a field that is list of a nested user-defined type in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest64(Some(AvroRecordTest00(1)))
      TestUtil.verifyWriteAndRead(record)
    }
  }


  "A case class with a field that is list of a nested user-defined type in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest65(None)
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with a field that is list of a nested user-defined type in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest66(Some(AvroRecordTest58(AvroRecordTest00(1))))
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with a field that is list of a nested user-defined type in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest67(
        Some(AvroRecordTest00(1)),
        Some(AvroRecordTest60(
          AvroRecordTest00(4),
          AvroRecordTest58(AvroRecordTest00(1))
        ))
      )
      TestUtil.verifyWriteAndRead(record)
    }
  }


  "A case class with a field that is list of a nested user-defined type in the second position" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTest68(
        Some(List(
          Some(AvroRecordTest00(1)),
          None)
        ),
        List(
          None,
          Some(List(AvroRecordTest01(1F), AvroRecordTest01(2F)))
        )
      )
      TestUtil.verifyWriteAndRead(record)
    }
  }

  "A case class with two differeing Map fields that contain user-defined types" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTestMap12(
        Map("socialist"->Map("capitalist"->AvroRecordTest00(1))),
        Map("private"->AvroRecordTest58(AvroRecordTest00(1)))
      )
      TestUtil.verifyWriteAndRead(record)
    }
  }
}
