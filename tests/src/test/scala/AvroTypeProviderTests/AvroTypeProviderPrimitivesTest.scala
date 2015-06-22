package test

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

class AvroTypeProviderMapTest extends Specification {

  "A case class with an `Map[String,Int]` field" should {
    "deserialize correctly" in {

      val record =  AvroTypeProviderTestMap01(Map("justice"->1))

      val file = new File("tests/src/test/resources/AvroTypeProviderTestMap01.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTestMap01](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTestMap01](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }


  "A case class with an `Map[String,String]` field" should {
    "deserialize correctly" in {

      val record =  AvroTypeProviderTestMap02(Map("justice"->"law"))

      val file = new File("tests/src/test/resources/AvroTypeProviderTestMap02.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTestMap02](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTestMap02](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }


  "A case class with an `Map[String,String]` field" should {
    "deserialize correctly" in {

      val record =  AvroTypeProviderTestMap03(Map("justice"->Some(List(1,2))))

      val file = new File("tests/src/test/resources/AvroTypeProviderTestMap03.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTestMap03](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTestMap03](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }



  "A case class with two `Map[String,Int]` fields" should {
    "deserialize correctly" in {

      val record = AvroTypeProviderTestMap04(Map("justice"->2, "law"->4), Map("sweet"->1))

      val file = new File("tests/src/test/resources/AvroTypeProviderTestMap04.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTestMap04](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTestMap04](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }


  "A case class with two `Map[String,String]` fields" should {
    "deserialize correctly" in {

      val record = AvroTypeProviderTestMap05(Map("justice"->"crime", "law"->"order"), Map("sweet"->"sour"))

      val file = new File("tests/src/test/resources/AvroTypeProviderTestMap05.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTestMap05](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTestMap05](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }


  "A case class with fields `x: Map[String, Option[List[Int]]], y: Map[String, Option[List[Int]]]`" should {
    "deserialize correctly" in {

      val record = AvroTypeProviderTestMap06(Map("justice"->None, "law"->Some(List(1,2))), Map("sweet"->Some(List(3,4))))

      val file = new File("tests/src/test/resources/AvroTypeProviderTestMap06.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTestMap06](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTestMap06](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }


  "A case class with a `Map[String, Map[String, Int]]` field" should {
    "deserialize correctly" in {

      val record = AvroTypeProviderTestMap07(Map("pepper"->Map("onion"->6)))

      val file = new File("tests/src/test/resources/AvroTypeProviderTestMap07.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      println(schema)
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTestMap07](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTestMap07](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }

  "A case class with a `List[Map[String, Map[String, String]]]` field" should {
    "deserialize correctly" in {

      val record = AvroTypeProviderTestMap08(List(Map("pepper"->Map("onion"->"garlic")), Map("bongo"->Map("tabla"->"conga"))))

      val file = new File("tests/src/test/resources/AvroTypeProviderTestMap08.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTestMap08](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTestMap08](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }

  "A case class with a `Option[Map[String, Option[List[String]]]]` field" should {
    "deserialize correctly" in {

      val record = AvroTypeProviderTestMap09(Some(Map("pepper"->Some(List("howdy", "doody")))))

      val file = new File("tests/src/test/resources/AvroTypeProviderTestMap09.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTestMap09](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTestMap09](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }

  "A case class with `x: Map[String, Map[String, Int]], y: Map[String, Map[String, Int]]` fields" should {
    "deserialize correctly" in {

      val record = AvroTypeProviderTestMap10(Map("pepper"->Map("onion"->6)), Map("salt"->Map("garlic"->7)))

      val file = new File("tests/src/test/resources/AvroTypeProviderTestMap10.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTestMap10](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTestMap10](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }

  "A case class with `x: Map[String, Map[String, Int]], y: List[Map[String, Map[String, String]]]` fields" should {
    "deserialize correctly" in {

      val record = AvroTypeProviderTestMap11(Map("pepper"->Map("onion"->6)), List(Map("salt"->Map("garlic"->"oil")), Map("sriracha"->Map("chili"->"oil"))))

      val file = new File("tests/src/test/resources/AvroTypeProviderTestMap11.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTestMap11](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTestMap11](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }

  "A case class with `x: Map[String, Map[String, AvroTypeProviderTest00]], y: Map[String, AvroTypeProviderTest58]` fields" should {
    "deserialize correctly" in {

      val record = AvroTypeProviderTestMap12(Map("c1"->Map("00"-> AvroTypeProviderTest00(2))), Map("58"->AvroTypeProviderTest58(AvroTypeProviderTest00(4))))

      val file = new File("tests/src/test/resources/AvroTypeProviderTestMap12.avro")

      val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTestMap12](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTestMap12](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }

}

