
package test
// Specs2
import org.specs2.mutable.Specification

import java.io.File

import com.julianpeeters.avro.annotations._

class AvroTypeProviderNestedSeparateSchemaFilesTest extends Specification {

  "A case class with types provided from two separate .avsc files" should {
    "serialize and deserialize correctly" in {
      val record = SeparateTestMessage("Achilles", SeparateMetaData("ow", "12345"))
      TestUtil.verifyWriteAndRead(List(record))
    }
  }
}
