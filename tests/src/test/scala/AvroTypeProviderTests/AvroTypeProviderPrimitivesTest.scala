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



class AvroTypeProvider00Test extends Specification {

  "A case class with an `Int` field" should {
    "deserialize correctly" in {

      val record = AvroTypeProviderTest00(1)

      val file = new File("tests/src/test/resources/AvroTypeProviderTest00.avro")
        
      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest00](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest00](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider01Test extends Specification {

  "A case class with an `Float` field" should {
    "deserialize correctly" in {

      val record = AvroTypeProviderTest01(1F)

      val file = new File("tests/src/test/resources/AvroTypeProviderTest01.avro")
        
      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest01](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest01](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}


class AvroTypeProvider02Test extends Specification {

  "A case class with an `Long` field" should {
    "deserialize correctly" in {

      val record = AvroTypeProviderTest02(1L)

      val file = new File("tests/src/test/resources/AvroTypeProviderTest02.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest02](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest02](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider03Test extends Specification {

  "A case class with an `Double` field" should {
    "deserialize correctly" in {

      val record = AvroTypeProviderTest03(1D)

      val file = new File("tests/src/test/resources/AvroTypeProviderTest03.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest03](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest03](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}


class AvroTypeProvider04Test extends Specification {

  "A case class with an `Boolean` field" should {
    "deserialize correctly" in {

      val record = AvroTypeProviderTest04(true)

      val file = new File("tests/src/test/resources/AvroTypeProviderTest04.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest04](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest04](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider05Test extends Specification {

  "A case class with an `String` field" should {
    "deserialize correctly" in {

      val record = AvroTypeProviderTest05("hello world")

      val file = new File("tests/src/test/resources/AvroTypeProviderTest05.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest05](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest05](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider06Test extends Specification {

  "A case class with an `Null` field" should {
    "deserialize correctly" in {

      val record = AvroTypeProviderTest06(null)

      val file = new File("tests/src/test/resources/AvroTypeProviderTest06.avro")
        
      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest06](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest06](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider07Test extends Specification {

  "A case class with an empty `Option[String]` field" should {
    "deserialize correctly" in {

      val record = AvroTypeProviderTest07(None)

      val file = new File("tests/src/test/resources/AvroTypeProviderTest07.avro")
        

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest07](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest07](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider08Test extends Specification {

  "A case class with an empty `Option[Int]` field" should {
    "deserialize correctly" in {

      val record = AvroTypeProviderTest08(None)

      val file = new File("tests/src/test/resources/AvroTypeProviderTest08.avro")
        
      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest08](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest08](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}



class AvroTypeProvider10Test extends Specification {

  "A case class with an `List[String]` field" should {
    "deserialize correctly" in {

      val record = AvroTypeProviderTest10(List("head.tail"))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest10.avro")
        
      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest10](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest10](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}


class AvroTypeProvider11Test extends Specification {

  "A case class with an `List[Int]` field" should {
    "deserialize correctly" in {

      val record = AvroTypeProviderTest11(List(1, 2))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest11.avro")
        
      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest11](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest11](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider12Test extends Specification {

  "A case class with an `Option[String]` field" should {
    "deserialize correctly" in {

      val record = AvroTypeProviderTest12(Some("I'm here"))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest12.avro")
        
      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest12](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest12](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroTypeProvider13Test extends Specification {

  "A case class with an `Option[Int]` field" should {
    "deserialize correctly" in {

      val record = AvroTypeProviderTest13(Some(1))

      val file = new File("tests/src/test/resources/AvroTypeProviderTest13.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTest13](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTest13](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

