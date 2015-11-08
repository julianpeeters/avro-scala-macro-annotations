package test

import org.specs2.mutable.Specification

class AvroRecordExtendedTest extends Specification {

  "A case class that extends an arbitrary trait" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordExtendedTest00(1)
      val sameRecord = TestUtil.writeThenRead(record)

      sameRecord.e must ===(10)
      sameRecord must ===(record)
    }
  }
}
