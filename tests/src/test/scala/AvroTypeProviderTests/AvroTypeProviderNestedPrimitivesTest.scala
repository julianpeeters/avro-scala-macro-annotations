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



class AvroTypeProvider28Test extends Specification {

  "A case class with a `List[List[String]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest28(List(List("blackbird.grackle")))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest28.avro")


      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest28](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest28](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider29Test extends Specification {

  "A case class with a `List[List[Int]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest29(List(List(1, 2)))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest29.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest29](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest29](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider30Test extends Specification {

  "A case class with an `Option[List[String]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest30(Some(List("starling.oriole")))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest30.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest30](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest30](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider31Test extends Specification {

  "A case class with an `Option[List[Int]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest31(Some(List(5, 6)))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest31.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest31](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest31](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider32Test extends Specification {

  "A case class with a `List[Option[String]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest32(List(Some("cowbird")))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest32.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest32](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest32](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider33Test extends Specification {

  "A case class with a `List[Option[Int]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest33(List(Some(1)))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest33.avro")
        
      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest33](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest33](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}


class AvroTypeProvider34Test extends Specification {

  "A case class with a `Option[List[Option[String]]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest34(Some(List(Some("cowbird"), None)))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest34.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest34](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest34](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider35Test extends Specification {

  "A case class with a `Option[List[Option[Int]]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest35(Some(List(Some(1), None)))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest35.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest35](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest35](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}


class AvroTypeProvider36Test extends Specification {

  "A case class with a `List[Option[List[Option[String]]]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest36(List(None, Some(List(Some("cowbird"), None))))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest36.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest36](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest36](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider37Test extends Specification {

  "A case class with a `List[Option[List[Option[Int]]]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest37(List(None, Some(List(Some(1), None))))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest37.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest37](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest37](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}



