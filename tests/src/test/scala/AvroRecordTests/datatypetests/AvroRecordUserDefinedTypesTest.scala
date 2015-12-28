package test

import org.specs2.mutable.Specification

class AvroRecordUserDefinedTypesTest extends Specification {

  "A case class with another record as a field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest58(AvroRecordTest00(1))
      val record2 = AvroRecordTest58(AvroRecordTest00(2))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an `Float` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest59(AvroRecordTest58(AvroRecordTest00(1)))
      val record2 = AvroRecordTest59(AvroRecordTest58(AvroRecordTest00(2)))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with an `Long` field" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest60(AvroRecordTest00(1), AvroRecordTest58(AvroRecordTest00(2)))
      val record2 = AvroRecordTest60(AvroRecordTest00(3), AvroRecordTest58(AvroRecordTest00(4)))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with a field that is list of a user-defined type" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest61(List(AvroRecordTest00(1), AvroRecordTest00(2)))
      val record2 = AvroRecordTest61(List(AvroRecordTest00(3), AvroRecordTest00(4)))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with a field that is list of a nested user-defined type" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest62(List(AvroRecordTest58(AvroRecordTest00(1)), AvroRecordTest58(AvroRecordTest00(2))))
      val record2 = AvroRecordTest62(List(AvroRecordTest58(AvroRecordTest00(3)), AvroRecordTest58(AvroRecordTest00(4))))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }



/* //TODO make readable file for this class - not very urgent since this field type is tested in other contexts also
  "A case class with a field that is list of a nested user-defined type in the second position" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest63(List(AvroRecordTest00(1), AvroRecordTest00(2)), List(AvroRecordTest60(AvroRecordTest00(3), AvroRecordTest58(AvroRecordTest00(2)))))
      val record2 = AvroRecordTest63(List(AvroRecordTest00(3), AvroRecordTest00(2)), List(AvroRecordTest60(AvroRecordTest00(3), AvroRecordTest58(AvroRecordTest00(2)))))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)

    }
  }
*/

  "A case class with a field that is list of a nested user-defined type in the second position" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest64(Some(AvroRecordTest00(1)))
      val record2 = AvroRecordTest64(Some(AvroRecordTest00(2)))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with a field that is list of a nested user-defined type in the second position" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest65(None)
      val record2 = AvroRecordTest65(None)
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with a field that is list of a nested user-defined type in the second position" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest66(Some(AvroRecordTest58(AvroRecordTest00(1))))
      val record2 = AvroRecordTest66(Some(AvroRecordTest58(AvroRecordTest00(2))))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with a field that is list of a nested user-defined type in the second position" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest67(Some(AvroRecordTest00(1)), Some(AvroRecordTest60(AvroRecordTest00(4), AvroRecordTest58(AvroRecordTest00(1)))))
      val record2 = AvroRecordTest67(Some(AvroRecordTest00(7)), Some(AvroRecordTest60(AvroRecordTest00(8), AvroRecordTest58(AvroRecordTest00(7)))))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with a field that is list of a nested user-defined type in the second position" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTest68(Some(List(Some(AvroRecordTest00(1)), None)), List(None, Some(List(AvroRecordTest01(1F), AvroRecordTest01(2F)))))
      val record2 = AvroRecordTest68(Some(List(Some(AvroRecordTest00(3)), None)), List(None, Some(List(AvroRecordTest01(3F), AvroRecordTest01(4F)))))
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }

  "A case class with two differeing Map fields that contain user-defined types" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTestMap12(
        Map("socialist"->Map("capitalist"->AvroRecordTest00(1))),
        Map("private"->AvroRecordTest58(AvroRecordTest00(1)))
      )
      val record2 = AvroRecordTestMap12(
        Map("mixed"->Map("communist"->AvroRecordTest00(2))),
        Map("public"->AvroRecordTest58(AvroRecordTest00(2)))
      )
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }
}
