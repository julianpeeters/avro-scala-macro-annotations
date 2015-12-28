import org.specs2.mutable.Specification

import test.TestUtil

import com.julianpeeters.avro.annotations.AvroRecord

@AvroRecord
case class AvroRecordTestNoNamespace(var x: Int)

class AvroRecordNoNamespaceTest extends Specification {

  "A case class with in the default package (i.e. without a namespace)" should {
    "serialize and deserialize correctly" in {
      val record1 = AvroRecordTestNoNamespace(1)
      val record2 = AvroRecordTestNoNamespace(2)
      val records = List(record1, record2)
      TestUtil.verifyWriteAndRead(records)
    }
  }
}
