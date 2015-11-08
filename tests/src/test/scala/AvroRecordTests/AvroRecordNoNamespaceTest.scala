import org.specs2.mutable.Specification

import test.TestUtil

import com.julianpeeters.avro.annotations.AvroRecord

@AvroRecord
case class AvroRecordTestNoNamespace(var x: Int)

class AvroRecordNoNamespaceTest extends Specification {

  "A case class with in the default package (i.e. without a namespace)" should {
    "serialize and deserialize correctly" in {
      val record = AvroRecordTestNoNamespace(1)
      TestUtil.verifyWriteAndRead(record)
    }
  }
}
