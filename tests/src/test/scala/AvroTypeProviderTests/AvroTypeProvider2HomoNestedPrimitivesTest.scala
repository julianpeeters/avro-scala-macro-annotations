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


class AvroTypeProvider38Test extends Specification {

  "A case class with more than one `List[List[String]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest38(
        List(
          List("hi.bye"), 
          List("yay.nay")
        ), 
        List(
          List("one.two"),
          List("three.four")
        )
      )

      val file = new File("tests/src/test/resources/AvroTypeProviderTest38.avro")      

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest38](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest38](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider39Test extends Specification {

  "A case class with more than one `List[List[String]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest39(
        List(
          List(1, 2), 
          List(3, 4)
        ), 
        List(
          List(5, 6),
          List(7, 8)
        )
      )

      val file = new File("tests/src/test/resources/AvroTypeProviderTest39.avro")
        
      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest39](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest39](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider40Test extends Specification {

  "A case class with more than one `Option[List[String]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest40(Some(List("up.down")), Some(List("left.right")))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest40.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest40](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest40](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider41Test extends Specification {

  "A case class with more than one `Option[List[Int]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest41(Some(List(1, 2)), Some(List(3, 4)))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest41.avro")
        

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest41](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest41](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider42Test extends Specification {

  "A case class with more than one `List[Option[String]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest42(List(None, Some("red")), List(Some("blue"), None))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest42.avro")
        

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest42](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest42](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider43Test extends Specification {

  "A case class with more than one `List[Option[Int]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest43(List(Some(1), None), List(Some(3), None))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest43.avro")
        

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest43](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest43](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider44Test extends Specification {

  "A case class with more than one `Option[List[Option[String]]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest44(Some(List(Some("gold"), None)), Some(List(Some("silver"), None)))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest44.avro")
        

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest44](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest44](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider45Test extends Specification {

  "A case class with more than one `Option[List[Option[Int]]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest45(Some(List(Some(8), None)), Some(List(Some(10), None)))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest45.avro")
        

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest45](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest45](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider46Test extends Specification {

  "A case class with more than one `List[Option[List[String]]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest46(List(None, Some(List(Some("green"), None))), List(None, Some(List(None, Some("yellow")))) )

      val file = new File("tests/src/test/resources/AvroTypeProviderTest46.avro")
        

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest46](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest46](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}
/* //TODO make readable file for this class - not very urgent since this field type is tested in other contexts also
class AvroTypeProvider47Test extends Specification {

  "A case class with more than one `List[Option[List[Int]]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTest47(List(None, Some(List(Some(2), None))), List(None, Some(List(None, Some(4)))) )

      val file = new File("tests/src/test/resources/AvroTypeProviderTest473.avro")
        

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest47](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest47](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}
*/


