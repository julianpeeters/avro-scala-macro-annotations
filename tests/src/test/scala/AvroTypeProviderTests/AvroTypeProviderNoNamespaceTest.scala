// no package

import org.specs2.mutable.Specification

import com.julianpeeters.avro.annotations._

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestNoNamespace.avro")
@AvroRecord
case class AvroTypeProviderTestNoNamespace()

class AvroTypeProviderNoNamespaceTest extends Specification {

  "A case class with in the default package (i.e. without a namespace)" should {
    "serialize and deserialize correctly" in {
      val record = AvroTypeProviderTestNoNamespace(1)
      test.TestUtil.verifyRead(record)
    }
  }
}
