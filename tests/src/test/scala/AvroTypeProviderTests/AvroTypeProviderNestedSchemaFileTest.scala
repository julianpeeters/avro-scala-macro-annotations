
package test
// Specs2
import org.specs2.mutable.Specification

import java.io.File

import com.julianpeeters.avro.annotations._

class AvroTypeProviderNestedSchemaFileTest extends Specification {

  "A case class with types provided from a .avsc avro schema file" should {
    "serialize and deserialize correctly" in {
      val record = TestMessage("Achilles", MetaData("ow", "12345"))
      TestUtil.verifyWriteAndRead(record)
    }
  }
}
