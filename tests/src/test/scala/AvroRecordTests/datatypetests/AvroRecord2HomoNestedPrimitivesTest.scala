
package test

import org.specs2.mutable.Specification

import java.io.File

import org.apache.avro.generic._
import org.apache.avro.specific._
import org.apache.avro.Schema
import org.apache.avro.Schema.{Type => AvroType}
import org.apache.avro.file._

class AvroRecord38Test extends Specification {

  "A case class with more than one `List[List[String]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest38(
        List(
          List("hi", "bye"), 
          List("yay", "nay")
        ), 
        List(
          List("one", "two"),
          List("three", "four")
        )
      )

      val file = File.createTempFile("AvroRecordTest38", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest38]
      val dataFileWriter = new DataFileWriter[AvroRecordTest38](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest38](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest38](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord39Test extends Specification {

  "A case class with more than one `List[List[String]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest39(
        List(
          List(1, 2), 
          List(3, 4)
        ), 
        List(
          List(5, 6),
          List(7, 8)
        )
      )

      val file = File.createTempFile("AvroRecordTest39", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest39]
      val dataFileWriter = new DataFileWriter[AvroRecordTest39](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest39](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest39](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord40Test extends Specification {

  "A case class with more than one `Option[List[String]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest40(Some(List("up", "down")), Some(List("left", "right")))

      val file = File.createTempFile("AvroRecordTest40", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest40]
      val dataFileWriter = new DataFileWriter[AvroRecordTest40](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest40](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest40](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord41Test extends Specification {

  "A case class with more than one `Option[List[Int]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest41(Some(List(1, 2)), Some(List(3, 4)))

      val file = File.createTempFile("AvroRecordTest41", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest41]
      val dataFileWriter = new DataFileWriter[AvroRecordTest41](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest41](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest41](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord42Test extends Specification {

  "A case class with more than one `List[Option[String]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest42(List(None, Some("red")), List(Some("blue"), None))

      val file = File.createTempFile("AvroRecordTest42", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest42]
      val dataFileWriter = new DataFileWriter[AvroRecordTest42](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest42](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest42](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord43Test extends Specification {

  "A case class with more than one `List[Option[Int]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest43(List(Some(1), None), List(Some(3), None))

      val file = File.createTempFile("AvroRecordTest43", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest43]
      val dataFileWriter = new DataFileWriter[AvroRecordTest43](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest43](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest43](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord44Test extends Specification {

  "A case class with more than one `Option[List[Option[String]]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest44(Some(List(Some("gold"), None)), Some(List(Some("silver"), None)))

      val file = File.createTempFile("AvroRecordTest44", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest44]
      val dataFileWriter = new DataFileWriter[AvroRecordTest44](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest44](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest44](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord45Test extends Specification {

  "A case class with more than one `Option[List[Option[Int]]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest45(Some(List(Some(8), None)), Some(List(Some(10), None)))

      val file = File.createTempFile("AvroRecordTest45", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest45]
      val dataFileWriter = new DataFileWriter[AvroRecordTest45](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest45](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest45](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord46Test extends Specification {

  "A case class with more than one `List[Option[List[String]]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest46(List(None, Some(List(Some("green"), None))), List(None, Some(List(None, Some("yellow")))) )

      val file = File.createTempFile("AvroRecordTest46", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest46]
      val dataFileWriter = new DataFileWriter[AvroRecordTest46](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest46](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest46](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord47Test extends Specification {

  "A case class with more than one `List[Option[List[Int]]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest47(List(None, Some(List(Some(2), None))), List(None, Some(List(None, Some(4)))) )

      val file = File.createTempFile("AvroRecordTest47", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest47]
      val dataFileWriter = new DataFileWriter[AvroRecordTest47](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest47](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest47](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecordMap10Test extends Specification {

  "A case class with two Map[String, Map[String, Int]] fields" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTestMap10(Map("glory"->Map("kitty"->3)), Map("pride"->Map("doggy"->4)))

      val file = File.createTempFile("AvroRecordTestMap10", "avro")
        

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTestMap10]
      val dataFileWriter = new DataFileWriter[AvroRecordTestMap10](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val userDatumReader = new SpecificDatumReader[AvroRecordTestMap10](AvroRecordTestMap10.SCHEMA$)
      val dataFileReader = new DataFileReader[AvroRecordTestMap10](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}