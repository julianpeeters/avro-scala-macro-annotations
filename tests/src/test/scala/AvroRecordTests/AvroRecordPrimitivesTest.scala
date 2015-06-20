package test

import org.specs2.mutable.Specification

import java.io.File

import org.apache.avro.generic._
import org.apache.avro.specific._
import org.apache.avro.Schema
import org.apache.avro.Schema.{Type => AvroType}
import org.apache.avro.file._

class AvroRecord00Test extends Specification {

  "A case class with an `Int` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest00(1)

      val file = File.createTempFile("AvroRecordTest00", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest00]
      val dataFileWriter = new DataFileWriter[AvroRecordTest00](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest00](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest00](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord01Test extends Specification {

  "A case class with an `Float` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest01(1F)

      val file = File.createTempFile("AvroRecordTest01", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest01]
      val dataFileWriter = new DataFileWriter[AvroRecordTest01](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest01](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest01](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}


class AvroRecord02Test extends Specification {

  "A case class with an `Long` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest02(1L)

      val file = File.createTempFile("AvroRecordTest02", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest02]
      val dataFileWriter = new DataFileWriter[AvroRecordTest02](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest02](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest02](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord03Test extends Specification {

  "A case class with an `Double` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest03(1D)

      val file = File.createTempFile("AvroRecordTest03", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest03]
      val dataFileWriter = new DataFileWriter[AvroRecordTest03](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest03](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest03](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}


class AvroRecord04Test extends Specification {

  "A case class with an `Boolean` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest04(true)

      val file = File.createTempFile("AvroRecordTest04", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest04]
      val dataFileWriter = new DataFileWriter[AvroRecordTest04](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest04](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest04](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord05Test extends Specification {

  "A case class with an `String` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest05("hello world")

      val file = File.createTempFile("AvroRecordTest05", "avro")
        

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest05]
      val dataFileWriter = new DataFileWriter[AvroRecordTest05](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest05](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest05](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord06Test extends Specification {

  "A case class with an `Null` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest06(null)

      val file = File.createTempFile("AvroRecordTest06", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest06]
      val dataFileWriter = new DataFileWriter[AvroRecordTest06](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest06](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest06](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord07Test extends Specification {

  "A case class with an empty `Option[String]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest07(None)

      val file = File.createTempFile("AvroRecordTest07", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest07]
      val dataFileWriter = new DataFileWriter[AvroRecordTest07](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest07](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest07](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord08Test extends Specification {

  "A case class with an empty `Option[Int]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest08(None)

      val file = File.createTempFile("AvroRecordTest08", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest08]
      val dataFileWriter = new DataFileWriter[AvroRecordTest08](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest08](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest08](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}









class AvroRecord10Test extends Specification {

  "A case class with an `List[String]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest10(List("head", "tail"))

      val file = File.createTempFile("AvroRecordTest10", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest10]
      val dataFileWriter = new DataFileWriter[AvroRecordTest10](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest10](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest10](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}


class AvroRecord11Test extends Specification {

  "A case class with an `List[Int]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest11(List(1, 2))

      val file = File.createTempFile("AvroRecordTest11", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest11]
      val dataFileWriter = new DataFileWriter[AvroRecordTest11](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest11](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest11](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord12Test extends Specification {

  "A case class with an `Option[String]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest12(Some("I'm here"))

      val file = File.createTempFile("AvroRecordTest12", "avro")
        file.deleteOnExit()

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest12]
      val dataFileWriter = new DataFileWriter[AvroRecordTest12](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest12](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest12](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecord13Test extends Specification {

  "A case class with an `Option[Int]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTest13(Some(1))

      val file = File.createTempFile("AvroRecordTest13", "avro")
        

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTest13]
      val dataFileWriter = new DataFileWriter[AvroRecordTest13](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroRecordTest13](schema)
      val dataFileReader = new DataFileReader[AvroRecordTest13](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecordMap01Test extends Specification {

  "A case class with a `Map[String, Int]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTestMap01(Map("bongo"->2))

      val file = File.createTempFile("AvroRecordTestMap01", "avro")
        

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTestMap01]
      val dataFileWriter = new DataFileWriter[AvroRecordTestMap01](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val userDatumReader = new SpecificDatumReader[AvroRecordTestMap01](AvroRecordTestMap01.SCHEMA$)
      val dataFileReader = new DataFileReader[AvroRecordTestMap01](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecordMap02Test extends Specification {

  "A case class with a `Map[String, String]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTestMap02(Map("4"->"four"))

      val file = File.createTempFile("AvroRecordTestMap02", "avro")
        

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTestMap02]
      val dataFileWriter = new DataFileWriter[AvroRecordTestMap02](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val userDatumReader = new SpecificDatumReader[AvroRecordTestMap02](AvroRecordTestMap02.SCHEMA$)
      val dataFileReader = new DataFileReader[AvroRecordTestMap02](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}

class AvroRecordMap03Test extends Specification {

  "A case class with a `Map[String, List[Int]]` field" should {
    "serialize and deserialize correctly" in {

      val record = AvroRecordTestMap03(Map("sherpa"->Some(List(5,6))))

      val file = File.createTempFile("AvroRecordTestMap03", "avro")
        

      val userDatumWriter = new SpecificDatumWriter[AvroRecordTestMap03]
      val dataFileWriter = new DataFileWriter[AvroRecordTestMap03](userDatumWriter)
        dataFileWriter.create(record.getSchema(), file);
        dataFileWriter.append(record);
        dataFileWriter.close();

      val userDatumReader = new SpecificDatumReader[AvroRecordTestMap03](AvroRecordTestMap03.SCHEMA$)
      val dataFileReader = new DataFileReader[AvroRecordTestMap03](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }
}