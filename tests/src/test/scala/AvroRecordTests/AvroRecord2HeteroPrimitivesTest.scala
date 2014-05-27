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

class AvroRecord48Test extends Specification {

  "A case class with an `Int` field coexisting with a non-`Int` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest48(1, "bonjour")

      val file = File.createTempFile("AvroRecordTest48", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest48]
      val dataFileWriter = new DataFileWriter[AvroRecordTest48](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest48](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest48](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord49Test extends Specification {

  "A case class with an `String` field coexisting with a non-`Int` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest49("bueno", 2)

      val file = File.createTempFile("AvroRecordTest49", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest49]
      val dataFileWriter = new DataFileWriter[AvroRecordTest49](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest49](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest49](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord50Test extends Specification {

  "A case class with an `Option[String]` field coexisting with an `Option[Int]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest50(Some("tropics"), Some(3))

      val file = File.createTempFile("AvroRecordTest50", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest50]
      val dataFileWriter = new DataFileWriter[AvroRecordTest50](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest50](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest50](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord51Test extends Specification {

  "A case class with an `Option[Int]` field coexisting with an `Option[String]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest51(Some(4), Some("level"))

      val file = File.createTempFile("AvroRecordTest51", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest51]
      val dataFileWriter = new DataFileWriter[AvroRecordTest51](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest51](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest51](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord52Test extends Specification {

  "A case class with a `List[String]` field coexisting with a `List[Int]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest52(List("am", "pm"), List(5,6))

      val file = File.createTempFile("AvroRecordTest52", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest52]
      val dataFileWriter = new DataFileWriter[AvroRecordTest52](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest52](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest52](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord53Test extends Specification {

  "A case class with an `List[Int]` field coexisting with a `List[String]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest53(List(7, 8), List("bon", "sois"))

      val file = File.createTempFile("AvroRecordTest53", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest53]
      val dataFileWriter = new DataFileWriter[AvroRecordTest53](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest53](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest53](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord54Test extends Specification {

  "A case class with an `Option[List[Option[String]]]` field coexisting with a `Option[List[Option[Int]]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest54(Some(List(Some("bronco"), None)), Some(List(Some(9), None)))

      val file = File.createTempFile("AvroRecordTest54", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest54]
      val dataFileWriter = new DataFileWriter[AvroRecordTest54](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest54](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest54](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord55Test extends Specification {

  "A case class with an `Option[List[Option[Int]]]` field coexisting with a `Option[List[Option[String]]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest55(Some(List(Some(10), None)), Some(List(Some("bronca"), None)))

      val file = File.createTempFile("AvroRecordTest55", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest55]
      val dataFileWriter = new DataFileWriter[AvroRecordTest55](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest55](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest55](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord56Test extends Specification {

  "A case class with an `List[Option[List[Option[String]]]]` field coexisting with a `List[Option[List[Option[Int]]]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest56(List(Some(List(Some("tibetan"), None)), None), List(Some(List(Some(11), None)), None))

      val file = File.createTempFile("AvroRecordTest56", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest56]
      val dataFileWriter = new DataFileWriter[AvroRecordTest56](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest56](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest56](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord57Test extends Specification {

  "A case class with an `Int` field coexisting with a non-`Int` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest57(List(Some(List(Some(12), None)), None), List(Some(List(Some("fire"), None)), None))

      val file = File.createTempFile("AvroRecordTest57", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest57]
      val dataFileWriter = new DataFileWriter[AvroRecordTest57](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest57](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest57](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}
