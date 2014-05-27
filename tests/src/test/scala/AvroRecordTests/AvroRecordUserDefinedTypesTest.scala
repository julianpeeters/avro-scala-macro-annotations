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


class AvroRecord58Test extends Specification {

  "A case class with another record as a field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest58(AvroRecordTest00(1))

      val file = File.createTempFile("AvroRecordTest58", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest58]
      val dataFileWriter = new DataFileWriter[AvroRecordTest58](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest58](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest58](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord59Test extends Specification {

  "A case class with an `Float` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest59(AvroRecordTest58(AvroRecordTest00(1)))

      val file = File.createTempFile("AvroRecordTest59", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest59]
      val dataFileWriter = new DataFileWriter[AvroRecordTest59](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest59](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest59](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}


class AvroRecord60Test extends Specification {

  "A case class with an `Long` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest60(AvroRecordTest00(1), AvroRecordTest58(AvroRecordTest00(2)))

      val file = File.createTempFile("AvroRecordTest60", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest60]
      val dataFileWriter = new DataFileWriter[AvroRecordTest60](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest60](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest60](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord61Test extends Specification {

  "A case class with a field that is list of a user-defined type" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest61(List(AvroRecordTest00(1), AvroRecordTest00(2)))

      val file = File.createTempFile("AvroRecordTest61", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest61]
      val dataFileWriter = new DataFileWriter[AvroRecordTest61](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest61](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest61](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord62Test extends Specification {

  "A case class with a field that is list of a nested user-defined type" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest62(List(AvroRecordTest58(AvroRecordTest00(1)), AvroRecordTest58(AvroRecordTest00(2))))

      val file = File.createTempFile("AvroRecordTest62", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest62]
      val dataFileWriter = new DataFileWriter[AvroRecordTest62](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest62](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest62](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}



class AvroRecord63Test extends Specification {

  "A case class with a field that is list of a nested user-defined type in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest63(List(AvroRecordTest00(1), AvroRecordTest00(2)), List(AvroRecordTest60(AvroRecordTest00(3), AvroRecordTest58(AvroRecordTest00(2)))))

      val file = File.createTempFile("AvroRecordTest63", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest63]
      val dataFileWriter = new DataFileWriter[AvroRecordTest63](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest63](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest63](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord64Test extends Specification {

  "A case class with a field that is list of a nested user-defined type in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest64(Some(AvroRecordTest00(1)))

      val file = File.createTempFile("AvroRecordTest64", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest64]
      val dataFileWriter = new DataFileWriter[AvroRecordTest64](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest64](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest64](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord65Test extends Specification {

  "A case class with a field that is list of a nested user-defined type in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest65(None)

      val file = File.createTempFile("AvroRecordTest65", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest65]
      val dataFileWriter = new DataFileWriter[AvroRecordTest65](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest65](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest65](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord66Test extends Specification {

  "A case class with a field that is list of a nested user-defined type in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest66(Some(AvroRecordTest58(AvroRecordTest00(1))))

      val file = File.createTempFile("AvroRecordTest66", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest66]
      val dataFileWriter = new DataFileWriter[AvroRecordTest66](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest66](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest66](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord67Test extends Specification {

  "A case class with a field that is list of a nested user-defined type in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest67(Some(AvroRecordTest00(1)), Some(AvroRecordTest60(AvroRecordTest00(4), AvroRecordTest58(AvroRecordTest00(1)))))

      val file = File.createTempFile("AvroRecordTest67", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest67]
      val dataFileWriter = new DataFileWriter[AvroRecordTest67](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest67](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest67](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord68Test extends Specification {

  "A case class with a field that is list of a nested user-defined type in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest68(Some(List(Some(AvroRecordTest00(1)), None)), List(None, Some(List(AvroRecordTest01(1F), AvroRecordTest01(2F)))))

      val file = File.createTempFile("AvroRecordTest68", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest68]
      val dataFileWriter = new DataFileWriter[AvroRecordTest68](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest68](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest68](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}
