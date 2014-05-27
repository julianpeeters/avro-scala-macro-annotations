/*
 * Copyright (c) 2012 Twitter, Inc.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package test

// Specs2
import org.specs2.mutable.Specification

import java.io.File

import org.apache.avro.generic._
import org.apache.avro.specific._
import org.apache.avro.Schema
import org.apache.avro.Schema.{Type => AvroType}
import org.apache.avro.file._




class AvroTypeProvider58Test extends Specification {

  "A case class with another record as a field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest58(AvroTypeProviderTest00(1))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest58.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest58](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest58](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider59Test extends Specification {

  "A case class with an `Float` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest59(AvroTypeProviderTest58(AvroTypeProviderTest00(1)))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest59.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest59](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest59](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}


class AvroTypeProvider60Test extends Specification {

  "A case class with an `Long` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest60(AvroTypeProviderTest00(1), AvroTypeProviderTest58(AvroTypeProviderTest00(2)))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest60.avro")
        
      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest60](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest60](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider61Test extends Specification {

  "A case class with a field that is list of a user-defined type" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest61(List(AvroTypeProviderTest00(1), AvroTypeProviderTest00(2)))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest61.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest61](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest61](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider62Test extends Specification {

  "A case class with a field that is list of a nested user-defined type" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest62(List(AvroTypeProviderTest58(AvroTypeProviderTest00(1)), AvroTypeProviderTest58(AvroTypeProviderTest00(2))))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest62.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest62](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest62](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}


/* //TODO make readable file for this class - not very urgent since this field type is tested in other contexts also
class AvroTypeProvider63Test extends Specification {

  "A case class with a field that is list of a nested user-defined type in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest63(List(AvroTypeProviderTest00(1), AvroTypeProviderTest00(2)), List(AvroTypeProviderTest60(AvroTypeProviderTest00(3), AvroTypeProviderTest58(AvroTypeProviderTest00(2)))))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest63.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest63](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest63](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}
*/
class AvroTypeProvider64Test extends Specification {

  "A case class with a field that is list of a nested user-defined type in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest64(Some(AvroTypeProviderTest00(1)))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest64.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest64](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest64](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider65Test extends Specification {

  "A case class with a field that is list of a nested user-defined type in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest65(None)

      val file = new File("tests/src/test/resources/AvroTypeProviderTest65.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest65](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest65](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider66Test extends Specification {

  "A case class with a field that is list of a nested user-defined type in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest66(Some(AvroTypeProviderTest58(AvroTypeProviderTest00(1))))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest66.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest66](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest66](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider67Test extends Specification {

  "A case class with a field that is list of a nested user-defined type in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest67(Some(AvroTypeProviderTest00(1)), Some(AvroTypeProviderTest60(AvroTypeProviderTest00(4), AvroTypeProviderTest58(AvroTypeProviderTest00(1)))))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest67.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest67](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest67](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider68Test extends Specification {

  "A case class with a field that is list of a nested user-defined type in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest68(Some(List(Some(AvroTypeProviderTest00(1)), None)), List(None, Some(List(AvroTypeProviderTest01(1F), AvroTypeProviderTest01(2F)))))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest68.avro")
        

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest68](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest68](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}
