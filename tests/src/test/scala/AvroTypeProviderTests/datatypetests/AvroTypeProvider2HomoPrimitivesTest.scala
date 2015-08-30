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


class AvroTypeProvider14Test extends Specification {

  "A case class with an `Int` field in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest14(1, 2)

      val file = new File("tests/src/test/resources/AvroTypeProviderTest14.avro")
        
      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest14](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest14](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider15Test extends Specification {

  "A case class with an `Float` field in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest15(1F, 2F)

      val file = new File("tests/src/test/resources/AvroTypeProviderTest15.avro")
        
      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest15](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest15](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider16Test extends Specification {

  "A case class with an `Long` field in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest16(1L, 2L)

      val file = new File("tests/src/test/resources/AvroTypeProviderTest16.avro")
        
      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest16](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest16](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider17Test extends Specification {

  "A case class with an `Double` field in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest17(1D, 2D)

      val file = new File("tests/src/test/resources/AvroTypeProviderTest17.avro")
        
      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest17](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest17](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider18Test extends Specification {

  "A case class with an `Boolean` field in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest18(true, false)

      val file = new File("tests/src/test/resources/AvroTypeProviderTest18.avro")
        
      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest18](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest18](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider19Test extends Specification {

  "A case class with an `String` field in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest19("1", "2")

      val file = new File("tests/src/test/resources/AvroTypeProviderTest19.avro")
        

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest19](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest19](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider20Test extends Specification {

  "A case class with an `Null` field in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest20(null, null)

      val file = new File("tests/src/test/resources/AvroTypeProviderTest20.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest20](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest20](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider24Test extends Specification {

  "A case class with an `List[String]` field in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest24(List("mekka.lekka.hi"), List("mekka.hiney.ho"))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest24.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest24](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest24](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider25Test extends Specification {

  "A case class with an `List[Int]` field in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest25(List(1, 2), List(3,4))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest25.avro")
        

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest25](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest25](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider26Test extends Specification {

  "A case class with an `Option[String]` field in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest26(Some("sun"), Some("moon"))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest26.avro")
        
      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest26](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest26](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider27Test extends Specification {

  "A case class with an `Option[Int]` field in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest27(Some(1), Some(2))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest27.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest27](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest27](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

