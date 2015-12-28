package test

import org.specs2.mutable.Specification
import com.julianpeeters.avro.annotations._

import java.io.File

import org.apache.avro.generic.GenericData.Record
import org.apache.avro.generic._
import org.apache.avro.specific._
import org.apache.avro.Schema
import org.apache.avro.Schema.{Type => AvroType}
import org.apache.avro.file._


@AvroRecord
case class AvroRecordTestDefaultValue00(var x: Int =  0)
@AvroRecord
case class AvroRecordTestDefaultValue01(var x: Float = 1.0F)
@AvroRecord
case class AvroRecordTestDefaultValue02(var x: Long = 2L)
@AvroRecord
case class AvroRecordTestDefaultValue03(var x: Double = 2.0D)
@AvroRecord
case class AvroRecordTestDefaultValue04(var x: Boolean = false)
@AvroRecord
case class AvroRecordTestDefaultValue05(var x: String = "")
@AvroRecord
case class AvroRecordTestDefaultValue06(var x: Null = null)

@AvroRecord
case class AvroRecordTestDefaultValue07(var x: Option[String] = None)
@AvroRecord
case class AvroRecordTestDefaultValue08(var x: Option[Int] = Some(1))


@AvroRecord
case class AvroRecordTestDefaultValue10(var x: List[String] = List("Greta", "Lauren"))
@AvroRecord
case class AvroRecordTestDefaultValue11(var x: List[Int] = List(1,2))

@AvroRecord
case class AvroRecordTestDefaultValue12(var x: Map[String, Int] = Map("Loiusa"->2), var y: Map[String, String] = Map("criss"->"cross"))
@AvroRecord
case class AvroRecordTestDefaultValue13(var x: Map[String, Option[AvroRecordTest00]] = Map("Bull"->Some(AvroRecordTest00(6)), "Snake"->None))

@AvroRecord
case class AvroRecordTestDefaultValue14(var x: AvroRecordTest00 = AvroRecordTest00(4))
@AvroRecord
case class AvroRecordTestDefaultValue15(var x: AvroRecordTestDefaultValue00 = AvroRecordTestDefaultValue00(4))
@AvroRecord
case class AvroRecordTestDefaultValue16(var x: AvroRecordTestDefaultValue14 = AvroRecordTestDefaultValue14(AvroRecordTest00(5)))
@AvroRecord
case class AvroRecordTestDefaultValue17(var x: List[Option[AvroRecordTestDefaultValue00]] = List(None, Some(AvroRecordTestDefaultValue00(8))))
@AvroRecord
case class AvroRecordTestDefaultValue18(var x: AvroRecordTest00 = AvroRecordTest00(4), var y: AvroRecordTest01 = AvroRecordTest01(3F))


class AvroRecordDefaultValueTest extends Specification {


  "A case class with an Int field" should {
    "have a schema that reflects the default value" in {
      val datumSchema = AvroRecordTestDefaultValue00().toString
      val recordSchema = AvroRecordTestDefaultValue00.SCHEMA$.toString
      datumSchema must ===("""{"x": 0}""")
      recordSchema must ===("""{"type":"record","name":"AvroRecordTestDefaultValue00","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"int","doc":"Auto-Generated Field","default":0}]}""")
    }
  }

  "A case class with a Float field" should {
    "have a schema that reflects the default value" in {
      val datumSchema = AvroRecordTestDefaultValue01().toString
      val recordSchema = AvroRecordTestDefaultValue01.SCHEMA$.toString
      datumSchema must ===("""{"x": 1.0}""")
      recordSchema must ===("""{"type":"record","name":"AvroRecordTestDefaultValue01","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"float","doc":"Auto-Generated Field","default":1.0}]}""")
    }
  }

  "A case class with a Long field" should {
    "have a schema that reflects the default value" in {
      val datumSchema = AvroRecordTestDefaultValue02().toString
      val recordSchema = AvroRecordTestDefaultValue02.SCHEMA$.toString
      datumSchema must ===("""{"x": 2}""")
      recordSchema must ===("""{"type":"record","name":"AvroRecordTestDefaultValue02","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"long","doc":"Auto-Generated Field","default":2}]}""")
    }
  }

  "A case class with a Double field" should {
    "have a schema that reflects the default value" in {
      val datumSchema = AvroRecordTestDefaultValue03().toString
      val recordSchema = AvroRecordTestDefaultValue03.SCHEMA$.toString
      datumSchema must ===("""{"x": 2.0}""")
      recordSchema must ===("""{"type":"record","name":"AvroRecordTestDefaultValue03","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"double","doc":"Auto-Generated Field","default":2.0}]}""")
    }
  }

  "A case class with a Boolean field" should {
    "have a schema that reflects the default value" in {
      val datumSchema = AvroRecordTestDefaultValue04().toString
      val recordSchema = AvroRecordTestDefaultValue04.SCHEMA$.toString
      datumSchema must ===("""{"x": false}""")
      recordSchema must ===("""{"type":"record","name":"AvroRecordTestDefaultValue04","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"boolean","doc":"Auto-Generated Field","default":false}]}""")
    }
  }

  "A case class with a String field" should {
    "have a schema that reflects the default value" in {
      val datumSchema = AvroRecordTestDefaultValue05().toString
      val recordSchema = AvroRecordTestDefaultValue05.SCHEMA$.toString
      datumSchema must ===("""{"x": ""}""")
      recordSchema must ===("""{"type":"record","name":"AvroRecordTestDefaultValue05","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"string","doc":"Auto-Generated Field","default":""}]}""")
    }
  }

  "A case class with a Null field" should {
    "have a schema that reflects the default value" in {
      val datumSchema = AvroRecordTestDefaultValue06().toString
      val recordSchema = AvroRecordTestDefaultValue06.SCHEMA$.toString
      datumSchema must ===("""{"x": null}""")
      recordSchema must ===("""{"type":"record","name":"AvroRecordTestDefaultValue06","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"null","doc":"Auto-Generated Field","default":null}]}""")
    }
  }

  "A case class with an Option field" should {
    "have a schema that reflects the default value of Some[String]" in {
      val datumSchema = AvroRecordTestDefaultValue07().toString
      val recordSchema = AvroRecordTestDefaultValue07.SCHEMA$.toString
      datumSchema must ===("""{"x": null}""")
      recordSchema must ===("""{"type":"record","name":"AvroRecordTestDefaultValue07","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":["null","string"],"doc":"Auto-Generated Field","default":null}]}""")
    }
  }

  "A case class with an Option field" should {
    "have a schema that reflects the default value Some()" in {
      val datumSchema = AvroRecordTestDefaultValue08().toString
      val recordSchema = AvroRecordTestDefaultValue08.SCHEMA$.toString
      datumSchema must ===("""{"x": 1}""")
      recordSchema must ===("""{"type":"record","name":"AvroRecordTestDefaultValue08","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":["null","int"],"doc":"Auto-Generated Field","default":1}]}""")
    }
  }

  "A case class with an Option field" should {
    "have a schema that reflects the default value of List[String]" in {
      val datumSchema = AvroRecordTestDefaultValue10().toString
      val recordSchema = AvroRecordTestDefaultValue10.SCHEMA$.toString
      datumSchema must ===("""{"x": ["Greta", "Lauren"]}""")
      recordSchema must ===("""{"type":"record","name":"AvroRecordTestDefaultValue10","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":{"type":"array","items":"string"},"doc":"Auto-Generated Field","default":["Greta","Lauren"]}]}""")
    }
  }

  "A case class with an Option field" should {
    "have a schema that reflects the default value List[Int]" in {
      val datumSchema = AvroRecordTestDefaultValue11().toString
      val recordSchema = AvroRecordTestDefaultValue11.SCHEMA$.toString
      datumSchema must ===("""{"x": [1, 2]}""")
      recordSchema must ===("""{"type":"record","name":"AvroRecordTestDefaultValue11","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":{"type":"array","items":"int"},"doc":"Auto-Generated Field","default":[1,2]}]}""")
    }
  }

  "A case class with an Option field" should {
    "have a schema that reflects the default value List[Int]" in {
      val datumSchema = AvroRecordTestDefaultValue12().toString
      val recordSchema = AvroRecordTestDefaultValue12.SCHEMA$.toString
      println(recordSchema)
      datumSchema must ===("""{"x": {"Loiusa": 2}, "y": {"criss": "cross"}}""")
      recordSchema must ===("""{"type":"record","name":"AvroRecordTestDefaultValue12","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":{"type":"map","values":"int"},"doc":"Auto-Generated Field","default":{"Loiusa":2}},{"name":"y","type":{"type":"map","values":"string"},"doc":"Auto-Generated Field","default":{"criss":"cross"}}]}""")
    }
  }

  "A case class with an Option field" should {
    "have a schema that reflects the default value List[Option[AvroRecordTest00]]" in {
      val datumSchema = AvroRecordTestDefaultValue13().toString
      val recordSchema = AvroRecordTestDefaultValue13.SCHEMA$.toString
      datumSchema must ===("""{"x": {"Snake": null, "Bull": {"x": 6}}}""")
      recordSchema must ===("""{"type":"record","name":"AvroRecordTestDefaultValue13","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":{"type":"map","values":["null",{"type":"record","name":"AvroRecordTest00","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"int","doc":"Auto-Generated Field"}]}]},"doc":"Auto-Generated Field","default":{"Bull":{"x":6},"Snake":null}}]}""")
    }
  }

  "A case class with a field that is a nested case class" should {
    "have a schema that reflects the nested datum as default value, and the nested datum has no default" in {
      val datumSchema = AvroRecordTestDefaultValue14().toString
      val recordSchema = AvroRecordTestDefaultValue14.SCHEMA$.toString
      datumSchema must ===("""{"x": {"x": 4}}""")
      recordSchema must ===("""{"type":"record","name":"AvroRecordTestDefaultValue14","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":{"type":"record","name":"AvroRecordTest00","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"int","doc":"Auto-Generated Field"}]},"doc":"Auto-Generated Field","default":{"x":4}}]}""")
    }
  }

  "A case class with a field that is a nested case class" should {
    "have a schema that reflects the nested datum as default value, and the nested datum also has a default" in {
      val datumSchema = AvroRecordTestDefaultValue15().toString
      val recordSchema = AvroRecordTestDefaultValue15.SCHEMA$.toString
      datumSchema must ===("""{"x": {"x": 4}}""")
      recordSchema must ===("""{"type":"record","name":"AvroRecordTestDefaultValue15","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":{"type":"record","name":"AvroRecordTestDefaultValue00","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"int","doc":"Auto-Generated Field","default":0}]},"doc":"Auto-Generated Field","default":{"x":4}}]}""")
    }
  }

  "A case class with a field that has a doubly nested case class" should {
    "have a schema that reflects the nested records and their defaults" in {
      val datumSchema = AvroRecordTestDefaultValue16().toString
      val recordSchema = AvroRecordTestDefaultValue16.SCHEMA$.toString
      datumSchema must ===("""{"x": {"x": {"x": 5}}}""")
      recordSchema must ===("""{"type":"record","name":"AvroRecordTestDefaultValue16","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":{"type":"record","name":"AvroRecordTestDefaultValue14","doc":"Auto-Generated Schema","fields":[{"name":"x","type":{"type":"record","name":"AvroRecordTest00","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"int","doc":"Auto-Generated Field"}]},"doc":"Auto-Generated Field","default":{"x":4}}]},"doc":"Auto-Generated Field","default":{"x":{"x":5}}}]}""")
    }
  }

  "A case class with an Option field" should {
    "have a schema that reflects the default value List[Option[MyRecord]]]" in {
      val datumSchema = AvroRecordTestDefaultValue17().toString
      val recordSchema = AvroRecordTestDefaultValue17.SCHEMA$.toString
      datumSchema must ===("""{"x": [null, {"x": 8}]}""")
      recordSchema must ===("""{"type":"record","name":"AvroRecordTestDefaultValue17","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":{"type":"array","items":["null",{"type":"record","name":"AvroRecordTestDefaultValue00","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"int","doc":"Auto-Generated Field","default":0}]}]},"doc":"Auto-Generated Field","default":[null,{"x":8}]}]}""")
    }
  }

  "A case class with a field that is a different nested case class in each of two fields" should {
    "have a schema that reflects the nested datum as default value, and the nested datum has no default" in {
      val datumSchema = AvroRecordTestDefaultValue18().toString
      val recordSchema = AvroRecordTestDefaultValue18.SCHEMA$.toString
      datumSchema must ===("""{"x": {"x": 4}, "y": {"x": 3.0}}""")
      recordSchema must ===("""{"type":"record","name":"AvroRecordTestDefaultValue18","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":{"type":"record","name":"AvroRecordTest00","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"int","doc":"Auto-Generated Field"}]},"doc":"Auto-Generated Field","default":{"x":4}},{"name":"y","type":{"type":"record","name":"AvroRecordTest01","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"float","doc":"Auto-Generated Field"}]},"doc":"Auto-Generated Field","default":{"x":3.0}}]}""")
    }
  }

}
