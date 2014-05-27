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

class AvroRecord28Test extends Specification {

  "A case class with a `List[List[String]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest28(List(List("blackbird", "grackle")))

      val file = File.createTempFile("AvroRecordTest28", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest28]
      val dataFileWriter = new DataFileWriter[AvroRecordTest28](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest28](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest28](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord29Test extends Specification {

  "A case class with a `List[List[Int]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest29(List(List(1, 2)))

      val file = File.createTempFile("AvroRecordTest29", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest29]
      val dataFileWriter = new DataFileWriter[AvroRecordTest29](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest29](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest29](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord30Test extends Specification {

  "A case class with an `Option[List[String]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest30(Some(List("starling", "oriole")))

      val file = File.createTempFile("AvroRecordTest30", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest30]
      val dataFileWriter = new DataFileWriter[AvroRecordTest30](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest30](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest30](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord31Test extends Specification {

  "A case class with an `Option[List[Int]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest31(Some(List(5, 6)))

      val file = File.createTempFile("AvroRecordTest31", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest31]
      val dataFileWriter = new DataFileWriter[AvroRecordTest31](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest31](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest31](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord32Test extends Specification {

  "A case class with a `List[Option[String]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest32(List(Some("cowbird")))

      val file = File.createTempFile("AvroRecordTest32", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest32]
      val dataFileWriter = new DataFileWriter[AvroRecordTest32](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest32](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest32](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord33Test extends Specification {

  "A case class with a `List[Option[Int]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest33(List(Some(1)))

      val file = File.createTempFile("AvroRecordTest33", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest33]
      val dataFileWriter = new DataFileWriter[AvroRecordTest33](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest33](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest33](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}


class AvroRecord34Test extends Specification {

  "A case class with a `Option[List[Option[String]]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest34(Some(List(Some("cowbird"), None)))

      val file = File.createTempFile("AvroRecordTest34", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest34]
      val dataFileWriter = new DataFileWriter[AvroRecordTest34](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest34](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest34](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord35Test extends Specification {

  "A case class with a `Option[List[Option[Int]]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest35(Some(List(Some(1), None)))

      val file = File.createTempFile("AvroRecordTest35", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest35]
      val dataFileWriter = new DataFileWriter[AvroRecordTest35](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest35](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest35](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}


class AvroRecord36Test extends Specification {

  "A case class with a `List[Option[List[Option[String]]]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest36(List(None, Some(List(Some("cowbird"), None))))

      val file = File.createTempFile("AvroRecordTest36", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest36]
      val dataFileWriter = new DataFileWriter[AvroRecordTest36](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest36](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest36](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord37Test extends Specification {

  "A case class with a `List[Option[List[Option[Int]]]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest37(List(None, Some(List(Some(1), None))))

      val file = File.createTempFile("AvroRecordTest37", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest37]
      val dataFileWriter = new DataFileWriter[AvroRecordTest37](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest37](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest37](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}
