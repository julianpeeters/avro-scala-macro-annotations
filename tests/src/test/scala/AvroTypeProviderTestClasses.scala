package test

import com.julianpeeters.avro.annotations._


//Primitive Types
@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest00.avro")
@AvroRecord
case class AvroTypeProviderTest00()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest01.avro")
@AvroRecord
case class AvroTypeProviderTest01()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest02.avro")
@AvroRecord
case class AvroTypeProviderTest02()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest03.avro")
@AvroRecord
case class AvroTypeProviderTest03()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest04.avro")
@AvroRecord
case class AvroTypeProviderTest04()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest05.avro")
@AvroRecord
case class AvroTypeProviderTest05()


@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest06.avro")
@AvroRecord
case class AvroTypeProviderTest06()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest07.avro")
@AvroRecord
case class AvroTypeProviderTest07()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest08.avro")
@AvroRecord
case class AvroTypeProviderTest08()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest10.avro")
@AvroRecord
case class AvroTypeProviderTest10()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest11.avro")
@AvroRecord
case class AvroTypeProviderTest11()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest12.avro")
@AvroRecord
case class AvroTypeProviderTest12()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest13.avro")
@AvroRecord
case class AvroTypeProviderTest13()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestMap01.avro")
@AvroRecord
case class AvroTypeProviderTestMap01()//var x: Map[String, Int])
@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestMap02.avro")
@AvroRecord
case class AvroTypeProviderTestMap02()//var x: Map[String, String])
@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestMap03.avro")
@AvroRecord
case class AvroTypeProviderTestMap03()//var x: Map[String, Option[List[Int]]])


//Primitive, 2-arity records

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest14.avro")
@AvroRecord
case class AvroTypeProviderTest14()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest15.avro")
@AvroRecord
case class AvroTypeProviderTest15()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest16.avro")
@AvroRecord
case class AvroTypeProviderTest16()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest17.avro")
@AvroRecord
case class AvroTypeProviderTest17()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest18.avro")
@AvroRecord
case class AvroTypeProviderTest18()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest19.avro")
@AvroRecord
case class AvroTypeProviderTest19()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest20.avro")
@AvroRecord
case class AvroTypeProviderTest20()


@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest24.avro")
@AvroRecord
case class AvroTypeProviderTest24()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest25.avro")
@AvroRecord
case class AvroTypeProviderTest25()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest26.avro")
@AvroRecord
case class AvroTypeProviderTest26()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest27.avro")
@AvroRecord
case class AvroTypeProviderTest27()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestMap04.avro")
@AvroRecord
case class AvroTypeProviderTestMap04()//var x: Map[String, Int], var y: Map[String, Int])
@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestMap05.avro")
@AvroRecord
case class AvroTypeProviderTestMap05()//var x: Map[String, String], var y: Map[String, String])
@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestMap06.avro")
@AvroRecord
case class AvroTypeProviderTestMap06()//var x: Map[String, Option[List[Int]]], var y: Map[String, Option[List[Int]]])


//Primitive nested
@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest28.avro")
@AvroRecord
case class AvroTypeProviderTest28()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest29.avro")
@AvroRecord
case class AvroTypeProviderTest29()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest30.avro")
@AvroRecord
case class AvroTypeProviderTest30()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest31.avro")
@AvroRecord
case class AvroTypeProviderTest31()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest32.avro")
@AvroRecord
case class AvroTypeProviderTest32()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest33.avro")
@AvroRecord
case class AvroTypeProviderTest33()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest34.avro")
@AvroRecord
case class AvroTypeProviderTest34()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest35.avro")
@AvroRecord
case class AvroTypeProviderTest35()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest36.avro")
@AvroRecord
case class AvroTypeProviderTest36()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest37.avro")
@AvroRecord
case class AvroTypeProviderTest37()


@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestMap07.avro")
@AvroRecord
case class AvroTypeProviderTestMap07()//var x: Map[String, Map[String, Int]])
@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestMap08.avro")
@AvroRecord
case class AvroTypeProviderTestMap08()//var x: List[Map[String, Map[String, String]]])
@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestMap09.avro")
@AvroRecord
case class AvroTypeProviderTestMap09()//var x: Option[Map[String, Option[List[String]]]])




//Primitive nested, 2-arity
@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest38.avro")
@AvroRecord
case class AvroTypeProviderTest38()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest39.avro")
@AvroRecord
case class AvroTypeProviderTest39()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest40.avro")
@AvroRecord
case class AvroTypeProviderTest40()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest41.avro")
@AvroRecord
case class AvroTypeProviderTest41()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest42.avro")
@AvroRecord
case class AvroTypeProviderTest42()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest43.avro")
@AvroRecord
case class AvroTypeProviderTest43()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest44.avro")
@AvroRecord
case class AvroTypeProviderTest44()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest45.avro")
@AvroRecord
case class AvroTypeProviderTest45()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest46.avro")
@AvroRecord
case class AvroTypeProviderTest46()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest47.avro")
@AvroRecord
case class AvroTypeProviderTest47()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestMap10.avro")
@AvroRecord
case class AvroTypeProviderTestMap10()//var x: Map[String, Map[String, Int]], var y: Map[String, Map[String, Int]])



//Primitive, 2-arity, heterogenous members
@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest48.avro")
@AvroRecord
case class AvroTypeProviderTest48()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest49.avro")
@AvroRecord
case class AvroTypeProviderTest49()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest50.avro")
@AvroRecord
case class AvroTypeProviderTest50()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest51.avro")
@AvroRecord
case class AvroTypeProviderTest51()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest52.avro")
@AvroRecord
case class AvroTypeProviderTest52()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest53.avro")
@AvroRecord
case class AvroTypeProviderTest53()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest54.avro")
@AvroRecord
case class AvroTypeProviderTest54()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest55.avro")
@AvroRecord
case class AvroTypeProviderTest55()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest56.avro")
@AvroRecord
case class AvroTypeProviderTest56()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest57.avro")
@AvroRecord
case class AvroTypeProviderTest57()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestMap11.avro")
@AvroRecord
case class AvroTypeProviderTestMap11()//var x: Map[String, Map[String, Int]], var y: List[Map[String, Map[String, String]]])



//User-defined types

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest58.avro")
@AvroRecord
case class AvroTypeProviderTest58()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest59.avro")
@AvroRecord
case class AvroTypeProviderTest59()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest60.avro")
@AvroRecord
case class AvroTypeProviderTest60()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest61.avro")
@AvroRecord
case class AvroTypeProviderTest61()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest62.avro")
@AvroRecord
case class AvroTypeProviderTest62()

/* //TODO make readable file for this class - not very urgent since this field type is tested in other contexts also
@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest63.avro")
@AvroRecord
case class AvroTypeProviderTest63()
*/

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest64.avro")
@AvroRecord
case class AvroTypeProviderTest64()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest65.avro")
@AvroRecord
case class AvroTypeProviderTest65()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest66.avro")
@AvroRecord
case class AvroTypeProviderTest66()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest67.avro")
@AvroRecord
case class AvroTypeProviderTest67()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTest68.avro")
@AvroRecord
case class AvroTypeProviderTest68()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestMap12.avro")
@AvroRecord
case class AvroTypeProviderTestMap12()//var x: Map[String, Map[String, AvroTypeProviderTest00]], var y: Map[String, AvroTypeProviderTest58])


// record classes that already extend a trait (results in a mixin with SpecificRecordBase)
trait ProviderExtension00 { val e = 10 }
@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderExtendedTest00.avro")
@AvroRecord
case class AvroTypeProviderExtendedTest00() extends ProviderExtension00


// preexisting companion object
@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderPreexistingCompanionTest00.avro")
@AvroRecord
case class AvroTypeProviderPreexistingCompanionTest00()
object AvroTypeProviderPreexistingCompanionTest00 {
  val o = 5
}

// nested record from schema file instead of nested record from .avro file
@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestNestedSchemaFile.avsc")
@AvroRecord
case class TestMessage()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestNestedSchemaFile.avsc")
@AvroRecord
case class MetaData()
