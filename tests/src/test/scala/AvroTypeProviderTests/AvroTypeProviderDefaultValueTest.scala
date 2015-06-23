package test

import org.specs2.mutable.Specification
import com.julianpeeters.avro.annotations._

import java.io.File

import org.apache.avro.io.{DecoderFactory, EncoderFactory}
import org.apache.avro.generic.GenericData.Record
import org.apache.avro.generic._
import org.apache.avro.specific._
import org.apache.avro.Schema
import org.apache.avro.Schema.{Type => AvroType}
import org.apache.avro.file._


@AvroTypeProvider("tests/src/test/resources/default/AvroTypeProviderTestDefaultValue00.avsc")
@AvroRecord
case class AvroTypeProviderTestDefaultValue00()//var x: Int =  0) 
@AvroTypeProvider("tests/src/test/resources/default/AvroTypeProviderTestDefaultValue01.avsc")
@AvroRecord
case class AvroTypeProviderTestDefaultValue01()//var x: Float = 1.0F) 
@AvroTypeProvider("tests/src/test/resources/default/AvroTypeProviderTestDefaultValue02.avsc")
@AvroRecord
case class AvroTypeProviderTestDefaultValue02()//var x: Long = 2L) 
@AvroTypeProvider("tests/src/test/resources/default/AvroTypeProviderTestDefaultValue03.avsc")
@AvroRecord
case class AvroTypeProviderTestDefaultValue03()//var x: Double = 2.0D) 
@AvroTypeProvider("tests/src/test/resources/default/AvroTypeProviderTestDefaultValue04.avsc")
@AvroRecord
case class AvroTypeProviderTestDefaultValue04()//var x: Boolean = false) 
@AvroTypeProvider("tests/src/test/resources/default/AvroTypeProviderTestDefaultValue05.avsc")
@AvroRecord
case class AvroTypeProviderTestDefaultValue05()//var x: String = "") 
@AvroTypeProvider("tests/src/test/resources/default/AvroTypeProviderTestDefaultValue06.avsc")
@AvroRecord
case class AvroTypeProviderTestDefaultValue06()//var x: Null = null) 

@AvroTypeProvider("tests/src/test/resources/default/AvroTypeProviderTestDefaultValue07.avsc")
@AvroRecord
case class AvroTypeProviderTestDefaultValue07()//var x: Option[String] = None)
@AvroTypeProvider("tests/src/test/resources/default/AvroTypeProviderTestDefaultValue08.avsc")
@AvroRecord
case class AvroTypeProviderTestDefaultValue08()//var x: Option[Int] = Some(1))

@AvroTypeProvider("tests/src/test/resources/default/AvroTypeProviderTestDefaultValue09.avsc")
@AvroRecord
case class AvroTypeProviderTestDefaultValue09()//var x: Map[String, Map[String, Int]] = Map("glory"->Map("kitty"->3)), var y: Map[String, Map[String, Int]] = Map("pride"->Map("doggy"->4)))



@AvroTypeProvider("tests/src/test/resources/default/AvroTypeProviderTestDefaultValue10.avsc")
@AvroRecord
case class AvroTypeProviderTestDefaultValue10()//var x: List[String] = List("Greta", "Lauren"))
@AvroTypeProvider("tests/src/test/resources/default/AvroTypeProviderTestDefaultValue11.avsc")
@AvroRecord
case class AvroTypeProviderTestDefaultValue11()//var x: List[Int] = List(1,2))



@AvroTypeProvider("tests/src/test/resources/default/AvroTypeProviderTestDefaultValue14.avsc")
@AvroRecord
case class AvroTypeProviderTestDefaultValue14()//var x: AvroTypeProviderTest00 = AvroTypeProviderTest00(4))

@AvroTypeProvider("tests/src/test/resources/default/AvroTypeProviderTestDefaultValue15.avsc")
@AvroRecord
case class AvroTypeProviderTestDefaultValue15()//var x: AvroTypeProviderTestDefaultValue00 = AvroTypeProviderTestDefaultValue00(4))
@AvroTypeProvider("tests/src/test/resources/default/AvroTypeProviderTestDefaultValue16.avsc")
@AvroRecord
case class AvroTypeProviderTestDefaultValue16()//var x: AvroTypeProviderTestDefaultValue14 = AvroTypeProviderTestDefaultValue14(AvroTypeProviderTest00(5)))
@AvroTypeProvider("tests/src/test/resources/default/AvroTypeProviderTestDefaultValue17.avsc")
@AvroRecord
case class AvroTypeProviderTestDefaultValue17()//var x: List[Option[AvroTypeProviderTestDefaultValue00]] = List(None, Some(AvroRecordTestDefaultValue00(AvroRecordTest00(8)))))
@AvroTypeProvider("tests/src/test/resources/default/AvroTypeProviderTestDefaultValue18.avsc")
@AvroRecord
case class AvroTypeProviderTestDefaultValue18()//var x: AvroRecordTest00 = AvroRecordTest00(4), var y: AvroRecordTest01 = AvroRecordTest01(3F))


class AvroTypeProviderDefaultValueTest extends Specification {


  "A case class with an Int field" should {
    "have a schema that reflects the default value" in {
      val datumSchema = AvroTypeProviderTestDefaultValue00().toString
      val recordSchema = AvroTypeProviderTestDefaultValue00.SCHEMA$.toString
      datumSchema must ===("""{"x": 0}""")
      recordSchema must ===("""{"type":"record","name":"AvroTypeProviderTestDefaultValue00","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"int","doc":"Auto-Generated Field","default":0}]}""")
    }
  }

  "A case class with a Float field" should {
    "have a schema that reflects the default value" in {
      val datumSchema = AvroTypeProviderTestDefaultValue01().toString
      val recordSchema = AvroTypeProviderTestDefaultValue01.SCHEMA$.toString
      datumSchema must ===("""{"x": 7.0}""")
      recordSchema must ===("""{"type":"record","name":"AvroTypeProviderTestDefaultValue01","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"float","doc":"Auto-Generated Field","default":7.0}]}""")
    }
  }

  "A case class with a Long field" should {
    "have a schema that reflects the default value" in {
      val datumSchema = AvroTypeProviderTestDefaultValue02().toString
      val recordSchema = AvroTypeProviderTestDefaultValue02.SCHEMA$.toString
      datumSchema must ===("""{"x": 2}""")
      recordSchema must ===("""{"type":"record","name":"AvroTypeProviderTestDefaultValue02","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"long","doc":"Auto-Generated Field","default":2}]}""")
    }
  }

  "A case class with a Double field" should {
    "have a schema that reflects the default value" in {
      val datumSchema = AvroTypeProviderTestDefaultValue03().toString
      val recordSchema = AvroTypeProviderTestDefaultValue03.SCHEMA$.toString
      datumSchema must ===("""{"x": 2.0}""")
      recordSchema must ===("""{"type":"record","name":"AvroTypeProviderTestDefaultValue03","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"double","doc":"Auto-Generated Field","default":2.0}]}""")
    }
  }

  "A case class with a Boolean field" should {
    "have a schema that reflects the default value" in {
      val datumSchema = AvroTypeProviderTestDefaultValue04().toString
      val recordSchema = AvroTypeProviderTestDefaultValue04.SCHEMA$.toString
      datumSchema must ===("""{"x": false}""")
      recordSchema must ===("""{"type":"record","name":"AvroTypeProviderTestDefaultValue04","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"boolean","doc":"Auto-Generated Field","default":false}]}""")
    }
  }

  "A case class with a String field" should {
    "have a schema that reflects the default value" in {
      val datumSchema = AvroTypeProviderTestDefaultValue05("").toString
      val recordSchema = AvroTypeProviderTestDefaultValue05.SCHEMA$.toString
      datumSchema must ===("""{"x": ""}""")
      recordSchema must ===("""{"type":"record","name":"AvroTypeProviderTestDefaultValue05","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"string","doc":"Auto-Generated Field","default":""}]}""")
    }
  }

  "A case class with a Null field" should {
    "have a schema that reflects the default value" in {
      val datumSchema = AvroTypeProviderTestDefaultValue06().toString
      val recordSchema = AvroTypeProviderTestDefaultValue06.SCHEMA$.toString
      datumSchema must ===("""{"x": null}""")
      recordSchema must ===("""{"type":"record","name":"AvroTypeProviderTestDefaultValue06","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"null","doc":"Auto-Generated Field","default":null}]}""")
    }
  }

  "A case class with an Option field" should {
    "have a schema that reflects the default value of Some[String]" in {
      val datumSchema = AvroTypeProviderTestDefaultValue07().toString
      val recordSchema = AvroTypeProviderTestDefaultValue07.SCHEMA$.toString
      datumSchema must ===("""{"x": null}""")
      recordSchema must ===("""{"type":"record","name":"AvroTypeProviderTestDefaultValue07","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":["null","string"],"doc":"Auto-Generated Field","default":null}]}""")
    }
  }

  "A case class with an Option field" should {
    "have a schema that reflects the default value Some()" in {
      val datumSchema = AvroTypeProviderTestDefaultValue08().toString
      val recordSchema = AvroTypeProviderTestDefaultValue08.SCHEMA$.toString
      datumSchema must ===("""{"x": 1}""")
      recordSchema must ===("""{"type":"record","name":"AvroTypeProviderTestDefaultValue08","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":["null","int"],"doc":"Auto-Generated Field","default":1}]}""")
    }
  }

  "A case class with an Option field" should {
    "have a schema that reflects the default value of map field types" in {
      val datumSchema = AvroTypeProviderTestDefaultValue09().toString
      val recordSchema = AvroTypeProviderTestDefaultValue09.SCHEMA$.toString
      datumSchema must ===("""{"x": {"glory": {"kitty": 3}}, "y": {"pride": {"doggy": 4}}}""")
      recordSchema must ===("""{"type":"record","name":"AvroTypeProviderTestDefaultValue09","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":{"type":"map","values":{"type":"map","values":"int"}},"doc":"Auto-Generated Field","default":{"glory":{"kitty":3}}},{"name":"y","type":{"type":"map","values":{"type":"map","values":"int"}},"doc":"Auto-Generated Field","default":{"pride":{"doggy":4}}}]}""")
    }
  }

  "A case class with an Option field" should {
    "have a schema that reflects the default value Some()" in {
      val datumSchema = AvroRecordTestDefaultValue10().toString
      val recordSchema = AvroRecordTestDefaultValue10.SCHEMA$.toString
      datumSchema must ===("""{"x": ["Greta", "Lauren"]}""")
      recordSchema must ===("""{"type":"record","name":"AvroRecordTestDefaultValue10","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":{"type":"array","items":"string"},"doc":"Auto-Generated Field","default":["Greta","Lauren"]}]}""")
    }
  }

  "A case class with a field that is a nested case class" should {
    "have a schema that reflects the nested datum as default value, and the nested datum has no default" in {
      val datumSchema = AvroTypeProviderTestDefaultValue11().toString
      val recordSchema = AvroTypeProviderTestDefaultValue11.SCHEMA$.toString
      datumSchema must ===("""{"x": [1, 2]}""")
      recordSchema must ===("""{"type":"record","name":"AvroTypeProviderTestDefaultValue11","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":{"type":"array","items":"int"},"doc":"Auto-Generated Field","default":[1,2]}]}""")
    }
  }

  "A case class with a field that is a nested case class" should {
    "have a schema that reflects the nested datum as default value, and the nested datum has no default" in {
      val datumSchema = AvroTypeProviderTestDefaultValue14().toString
      val recordSchema = AvroTypeProviderTestDefaultValue14.SCHEMA$.toString
      datumSchema must ===("""{"x": {"x": 4}}""")
      recordSchema must ===("""{"type":"record","name":"AvroTypeProviderTestDefaultValue14","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":{"type":"record","name":"AvroTypeProviderTest00","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"int","doc":"Auto-Generated Field"}]},"doc":"Auto-Generated Field","default":{"x":4}}]}""")
    }
  }

  "A case class with a field that is a nested case class" should {
    "have a schema that reflects the nested datum as default value, and the nested datum also has a default" in {
      val datumSchema = AvroTypeProviderTestDefaultValue15().toString
      val recordSchema = AvroTypeProviderTestDefaultValue15.SCHEMA$.toString
      datumSchema must ===("""{"x": {"x": 4}}""")
      recordSchema must ===("""{"type":"record","name":"AvroTypeProviderTestDefaultValue15","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":{"type":"record","name":"AvroTypeProviderTestDefaultValue00","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"int","doc":"Auto-Generated Field","default":0}]},"doc":"Auto-Generated Field","default":{"x":4}}]}""")
    }
  }

  "A case class with a field that has a doubly nested case class" should {
    "have a schema that reflects the nested records and their defaults" in {
      val datumSchema = AvroTypeProviderTestDefaultValue16().toString
      val recordSchema = AvroTypeProviderTestDefaultValue16.SCHEMA$.toString
      datumSchema must ===("""{"x": {"x": {"x": 5}}}""")
      recordSchema must ===("""{"type":"record","name":"AvroTypeProviderTestDefaultValue16","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":{"type":"record","name":"AvroTypeProviderTestDefaultValue14","doc":"Auto-Generated Schema","fields":[{"name":"x","type":{"type":"record","name":"AvroTypeProviderTest00","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"int","doc":"Auto-Generated Field"}]},"doc":"Auto-Generated Field","default":{"x":4}}]},"doc":"Auto-Generated Field","default":{"x":{"x":5}}}]}""")
    }
  }

  "A case class with a field that has a doubly nested case class" should {
    "have a schema that reflects the nested records and their defaults" in {
      val datumSchema = AvroTypeProviderTestDefaultValue17().toString
      val recordSchema = AvroTypeProviderTestDefaultValue17.SCHEMA$.toString
      datumSchema must ===("""{"x": [null, {"x": 8}]}""")
      recordSchema must ===("""{"type":"record","name":"AvroTypeProviderTestDefaultValue17","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":{"type":"array","items":["null",{"type":"record","name":"AvroTypeProviderTestDefaultValue00","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"int","doc":"Auto-Generated Field","default":0}]}]},"doc":"Auto-Generated Field","default":[null,{"x":8}]}]}""")
    }
  }

  "A case class with a field that has a doubly nested case class" should {
    "have a schema that reflects the nested records and their defaults" in {
      val datumSchema = AvroTypeProviderTestDefaultValue18().toString
      val recordSchema = AvroTypeProviderTestDefaultValue18.SCHEMA$.toString
      datumSchema must ===("""{"x": {"x": 4}, "y": {"x": 3.0}}""")
      recordSchema must ===("""{"type":"record","name":"AvroTypeProviderTestDefaultValue18","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":{"type":"record","name":"AvroTypeProviderTest00","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"int","doc":"Auto-Generated Field"}]},"doc":"Auto-Generated Field","default":{"x":4}},{"name":"y","type":{"type":"record","name":"AvroTypeProviderTest01","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"float","doc":"Auto-Generated Field"}]},"doc":"Auto-Generated Field","default":{"x":3.0}}]}""")
    }
  }

}