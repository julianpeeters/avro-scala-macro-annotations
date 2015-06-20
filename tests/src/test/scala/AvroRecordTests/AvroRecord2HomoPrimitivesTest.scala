
package test

import org.specs2.mutable.Specification

import java.io.File

import org.apache.avro.generic._
import org.apache.avro.specific._
import org.apache.avro.Schema
import org.apache.avro.Schema.{Type => AvroType}
import org.apache.avro.file._

class AvroRecord14Test extends Specification {

  "A case class with an `Int` field in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest14(1, 2)

      val file = File.createTempFile("AvroRecordTest14", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest14]
      val dataFileWriter = new DataFileWriter[AvroRecordTest14](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest14](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest14](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord15Test extends Specification {

  "A case class with an `Float` field in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest15(1F, 2F)

      val file = File.createTempFile("AvroRecordTest15", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest15]
      val dataFileWriter = new DataFileWriter[AvroRecordTest15](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest15](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest15](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord16Test extends Specification {

  "A case class with an `Long` field in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest16(1L, 2L)

      val file = File.createTempFile("AvroRecordTest16", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest16]
      val dataFileWriter = new DataFileWriter[AvroRecordTest16](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest16](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest16](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord17Test extends Specification {

  "A case class with an `Double` field in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest17(1D, 2D)

      val file = File.createTempFile("AvroRecordTest17", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest17]
      val dataFileWriter = new DataFileWriter[AvroRecordTest17](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest17](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest17](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord18Test extends Specification {

  "A case class with an `Boolean` field in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest18(true, false)

      val file = File.createTempFile("AvroRecordTest18", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest18]
      val dataFileWriter = new DataFileWriter[AvroRecordTest18](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest18](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest18](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord19Test extends Specification {

  "A case class with an `String` field in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest19("1", "2")

      val file = File.createTempFile("AvroRecordTest19", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest19]
      val dataFileWriter = new DataFileWriter[AvroRecordTest19](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest19](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest19](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord20Test extends Specification {

  "A case class with an `Null` field in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest20(null, null)

      val file = File.createTempFile("AvroRecordTest20", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest20]
      val dataFileWriter = new DataFileWriter[AvroRecordTest20](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest20](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest20](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord24Test extends Specification {

  "A case class with an `List[String]` field in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest24(List("mekka", "lekka", "hi"), List("mekka", "hiney", "ho"))

      val file = File.createTempFile("AvroRecordTest24", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest24]
      val dataFileWriter = new DataFileWriter[AvroRecordTest24](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest24](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest24](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord25Test extends Specification {

  "A case class with an `List[Int]` field in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest25(List(1, 2), List(3,4))

      val file = File.createTempFile("AvroRecordTest25", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest25]
      val dataFileWriter = new DataFileWriter[AvroRecordTest25](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest25](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest25](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord26Test extends Specification {

  "A case class with an `Option[String]` field in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest26(Some("sun"), Some("moon"))

      val file = File.createTempFile("AvroRecordTest26", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest26]
      val dataFileWriter = new DataFileWriter[AvroRecordTest26](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest26](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest26](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord27Test extends Specification {

  "A case class with an `Option[Int]` field in the second position" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest27(Some(1), Some(2))

      val file = File.createTempFile("AvroRecordTest27", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest27]
      val dataFileWriter = new DataFileWriter[AvroRecordTest27](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest27](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest27](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecordMap04Test extends Specification {

  "A case class with two Map[Int, Int] fields" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTestMap04(Map("Gorgonzola"->2), Map("Cheddar"->4))

      val file = File.createTempFile("AvroRecordTestMap04", "avro")
        

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTestMap04]
      val dataFileWriter = new DataFileWriter[AvroRecordTestMap04](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val userDatumReader = new SpecificDatumReader[AvroRecordTestMap04](AvroRecordTestMap04.SCHEMA$)
      val dataFileReader = new DataFileReader[AvroRecordTestMap04](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecordMap05Test extends Specification {

  "A case class with two Map[Int, String] fields" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTestMap05(Map("Havana"->"Cuba"), Map("Blue World"->"series"))

      val file = File.createTempFile("AvroRecordTestMap05", "avro")
        

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTestMap05]
      val dataFileWriter = new DataFileWriter[AvroRecordTestMap05](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val userDatumReader = new SpecificDatumReader[AvroRecordTestMap05](AvroRecordTestMap05.SCHEMA$)
      val dataFileReader = new DataFileReader[AvroRecordTestMap05](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecordMap06Test extends Specification {

  "A case class with two Map[String, Option[List[Int]]] fields" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTestMap06(Map("Olala"->Some(List(1,4))), Map("Rumpole"->None))

      val file = File.createTempFile("AvroRecordTestMap06", "avro")
        

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTestMap06]
      val dataFileWriter = new DataFileWriter[AvroRecordTestMap06](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val userDatumReader = new SpecificDatumReader[AvroRecordTestMap06](AvroRecordTestMap06.SCHEMA$)
      val dataFileReader = new DataFileReader[AvroRecordTestMap06](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}