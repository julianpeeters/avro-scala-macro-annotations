package test

import org.specs2.mutable.Specification
import com.julianpeeters.avro.annotations._

import java.io.File


import org.apache.avro.Schema
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
case class AvroRecordTestDefaultValue14(var x: AvroRecordTest00 = AvroRecordTest00(4))
@AvroRecord
case class AvroRecordTestDefaultValue15(var x: AvroRecordTestDefaultValue00 = AvroRecordTestDefaultValue00(4))
@AvroRecord
case class AvroRecordTestDefaultValue16(var x: AvroRecordTestDefaultValue14 = AvroRecordTestDefaultValue14(AvroRecordTest00(5)))



class AvroRecordDefaultValueTest extends Specification {


  "A case class with an Int field" should {
    "have a schema that reflects the default value" in {
      val schemaString = AvroRecordTestDefaultValue00.SCHEMA$.toString
      schemaString must ===("""{"type":"record","name":"AvroRecordTestDefaultValue00","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"int","doc":"Auto-Generated Field","default":0}]}""")
    }
  }

  "A case class with a Float field" should {
    "have a schema that reflects the default value" in {
      val schemaString = AvroRecordTestDefaultValue01.SCHEMA$.toString
      schemaString must ===("""{"type":"record","name":"AvroRecordTestDefaultValue01","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"float","doc":"Auto-Generated Field","default":1.0}]}""")
    }
  }

  "A case class with a Long field" should {
    "have a schema that reflects the default value" in {
      val schemaString = AvroRecordTestDefaultValue02.SCHEMA$.toString
      schemaString must ===("""{"type":"record","name":"AvroRecordTestDefaultValue02","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"long","doc":"Auto-Generated Field","default":2}]}""")
    }
  }

  "A case class with a Double field" should {
    "have a schema that reflects the default value" in {
      val schemaString = AvroRecordTestDefaultValue03.SCHEMA$.toString
      schemaString must ===("""{"type":"record","name":"AvroRecordTestDefaultValue03","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"double","doc":"Auto-Generated Field","default":2.0}]}""")
    }
  }

  "A case class with a Boolean field" should {
    "have a schema that reflects the default value" in {
      val schemaString = AvroRecordTestDefaultValue04.SCHEMA$.toString
      schemaString must ===("""{"type":"record","name":"AvroRecordTestDefaultValue04","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"boolean","doc":"Auto-Generated Field","default":false}]}""")
    }
  }

  "A case class with a String field" should {
    "have a schema that reflects the default value" in {
      val schemaString = AvroRecordTestDefaultValue05.SCHEMA$.toString
      schemaString must ===("""{"type":"record","name":"AvroRecordTestDefaultValue05","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"string","doc":"Auto-Generated Field","default":""}]}""")
    }
  }

  "A case class with a Null field" should {
    "have a schema that reflects the default value" in {
      val schemaString = AvroRecordTestDefaultValue06.SCHEMA$.toString
      schemaString must ===("""{"type":"record","name":"AvroRecordTestDefaultValue06","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"null","doc":"Auto-Generated Field","default":null}]}""")
    }
  }

  "A case class with an Option field" should {
    "have a schema that reflects the default value of Some[String]" in {
      val schemaString = AvroRecordTestDefaultValue07.SCHEMA$.toString
      schemaString must ===("""{"type":"record","name":"AvroRecordTestDefaultValue07","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":["null","string"],"doc":"Auto-Generated Field","default":null}]}""")
    }
  }

  "A case class with an Option field" should {
    "have a schema that reflects the default value Some()" in {
      val schemaString = AvroRecordTestDefaultValue08.SCHEMA$.toString
      schemaString must ===("""{"type":"record","name":"AvroRecordTestDefaultValue08","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":["null","int"],"doc":"Auto-Generated Field","default":1}]}""")
    }
  }

  "A case class with a field that is a nested case class" should {
    "have a schema that reflects the nested record as default value, and the nested record has no default" in {
      val schemaString = AvroRecordTestDefaultValue14.SCHEMA$.toString
      schemaString must ===("""{"type":"record","name":"AvroRecordTestDefaultValue14","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":{"type":"record","name":"AvroRecordTest00","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"int","doc":"Auto-Generated Field"}]},"doc":"Auto-Generated Field","default":{"x":4}}]}""")
    }
  }

  "A case class with a field that is a nested case class" should {
    "have a schema that reflects the nested record as default value, and the nested record also has a default" in {
      val schemaString = AvroRecordTestDefaultValue15.SCHEMA$.toString
      schemaString must ===("""{"type":"record","name":"AvroRecordTestDefaultValue15","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":{"type":"record","name":"AvroRecordTestDefaultValue00","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"int","doc":"Auto-Generated Field","default":0}]},"doc":"Auto-Generated Field","default":{"x":4}}]}""")
    }
  }

  "A case class with a field that has a doubly nested case class" should {
    "have a schema that reflects the nested records and their defaults" in {
      val schemaString = AvroRecordTestDefaultValue16.SCHEMA$.toString
      schemaString must ===("""{"type":"record","name":"AvroRecordTestDefaultValue16","namespace":"test","doc":"Auto-Generated Schema","fields":[{"name":"x","type":{"type":"record","name":"AvroRecordTestDefaultValue14","doc":"Auto-Generated Schema","fields":[{"name":"x","type":{"type":"record","name":"AvroRecordTest00","doc":"Auto-Generated Schema","fields":[{"name":"x","type":"int","doc":"Auto-Generated Field"}]},"doc":"Auto-Generated Field","default":{"x":4}}]},"doc":"Auto-Generated Field","default":{"x":{"x":5}}}]}""")
    }
  }

}