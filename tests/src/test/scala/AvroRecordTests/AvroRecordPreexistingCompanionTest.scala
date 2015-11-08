package test

import org.specs2.mutable.Specification

class AvroRecordCompanionTest extends Specification {

  "A case class that has a preexisting companion object" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordPreexistingCompanionTest00(1)
      val sameRecord = TestUtil.writeThenRead(record)
      AvroRecordPreexistingCompanionTest00.o must ===(5)
      sameRecord must ===(record)
    }
  }
}
