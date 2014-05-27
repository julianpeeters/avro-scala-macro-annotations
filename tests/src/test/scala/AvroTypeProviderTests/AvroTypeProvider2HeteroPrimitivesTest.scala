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

import org.specs2.mutable.Specification

import org.apache.avro.generic._
import org.apache.avro.specific._
import org.apache.avro.Schema
import org.apache.avro.Schema.{Type => AvroType}
import org.apache.avro.file._

import java.io.File

class AvroTypeProvider48Test extends Specification {

  "A case class with an `Int` field coexisting with a non-`Int` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest48(1, "bonjour")

      val file = new File("tests/src/test/resources/AvroTypeProviderTest48.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest48](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest48](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider49Test extends Specification {

  "A case class with an `String` field coexisting with a non-`Int` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest49("bueno", 2)

      val file = new File("tests/src/test/resources/AvroTypeProviderTest49.avro")
        

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest49](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest49](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider50Test extends Specification {

  "A case class with an `Option[String]` field coexisting with an `Option[Int]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest50(Some("tropics"), Some(3))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest50.avro")
        

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest50](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest50](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider51Test extends Specification {

  "A case class with an `Option[Int]` field coexisting with an `Option[String]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest51(Some(4), Some("level"))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest51.avro")
        

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest51](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest51](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider52Test extends Specification {

  "A case class with a `List[String]` field coexisting with a `List[Int]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest52(List("am.pm"), List(5,6))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest52.avro")
        
      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest52](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest52](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider53Test extends Specification {

  "A case class with an `List[Int]` field coexisting with a `List[String]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest53(List(7, 8), List("bon.sois"))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest53.avro")
        

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest53](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest53](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider54Test extends Specification {

  "A case class with an `Option[List[Option[String]]]` field coexisting with a `Option[List[Option[Int]]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest54(Some(List(Some("bronco"), None)), Some(List(Some(9), None)))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest54.avro")
        
      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest54](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest54](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider55Test extends Specification {

  "A case class with an `Option[List[Option[Int]]]` field coexisting with a `Option[List[Option[String]]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest55(Some(List(Some(10), None)), Some(List(Some("bronca"), None)))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest55.avro")
        

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest55](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest55](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider56Test extends Specification {

  "A case class with an `List[Option[List[Option[String]]]]` field coexisting with a `List[Option[List[Option[Int]]]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest56(List(Some(List(Some("tibetan"), None)), None), List(Some(List(Some(11), None)), None))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest56.avro")
        

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest56](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest56](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider57Test extends Specification {

  "A case class with an `Int` field coexisting with a non-`Int` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest57(List(Some(List(Some(12), None)), None), List(Some(List(Some("fire"), None)), None))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest57.avro")
        
      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest57](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest57](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}
