package test

import org.specs2.mutable.Specification

import java.io.File

import org.apache.avro.generic._
import org.apache.avro.specific._
import org.apache.avro.Schema
import org.apache.avro.file._

import collection.JavaConversions._

import com.julianpeeters.avro.annotations._

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestNoNamespaceOption.avro")
@AvroRecord
case class AvroTypeProviderTestNoNamespaceOption()


@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestNoNamespaceDeeplyNested.avro")
@AvroRecord
case class AvroTypeProviderTestNoNamespaceDeeplyNested()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestNoNamespaceDoubleNested.avro")
@AvroRecord
case class AvroTypeProviderTestNoNamespaceDoubleNested()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestNoNamespace.avro")
@AvroRecord
case class AvroTypeProviderTestNoNamespace()

@AvroTypeProvider("tests/src/test/resources/AvroTypeProviderTestNoNamespaceNested.avro")
@AvroRecord
case class AvroTypeProviderTestNoNamespaceNested()


class AvroTypeProviderProvideNamespaceTest extends Specification {

  "An avro file without a namespace" should {
    "deserialize into a non-default package" in {

      val record = AvroTypeProviderTestNoNamespace(1)

      val file = new File("tests/src/test/resources/AvroTypeProviderTestNoNamespace.avro")

      val schema = AvroTypeProviderTestNoNamespace.SCHEMA$

      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTestNoNamespace](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTestNoNamespace](file, userDatumReader)
      val sameRecord = dataFileReader.next()


      sameRecord must ===(record)
    }
  }


"A record without a namespace as a field's type" should {
    "deserialize into a non-default package" in {


      val record = AvroTypeProviderTestNoNamespaceNested(AvroTypeProviderTestNoNamespace(2))

      val file = new File("tests/src/test/resources/AvroTypeProviderTestNoNamespaceNested.avro")
      val schema = AvroTypeProviderTestNoNamespaceNested.SCHEMA$

      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTestNoNamespaceNested](schema)

      val dataFileReader = new DataFileReader[AvroTypeProviderTestNoNamespaceNested](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }



/*

#####################################################################################################
  3 FAILING TESTS:
  Reading and writing succeeds for simple records whose fields are primitive types,
  and also succeeds for "nested" records whose fields are `record` types,
  however reading and writing fails for records whose fields are unions of `record` types.


  The issues seem to be due to the mismatch between a) the expected and actual schemas, and b) the full names of records vs specific classes.
  Thus, I believe this is an Avro issue, but so far no response on the users mailing list:
  http://apache-avro.679487.n3.nabble.com/Issues-reading-and-writing-namespace-less-schemas-from-namespaced-Specific-Records-tc4032092.html

######################################################################################################


"A case class with in the default package (i.e. without a namespace) as a field's 2-level nested type" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTestNoNamespaceDoubleNested(List(Some(AvroTypeProviderTestNoNamespace(5))))

      val file = new File("tests/src/test/resources/AvroTypeProviderTestNoNamespaceDoubleNested.avro")

      val schema = AvroTypeProviderTestNoNamespaceDoubleNested.SCHEMA$
      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTestNoNamespaceDoubleNested](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTestNoNamespaceDoubleNested](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }


"A case class with in the default package (i.e. without a namespace) as a field's deeply nested type" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTestNoNamespaceDeeplyNested(Some(List(Some(AvroTypeProviderTestNoNamespace(4)))))

      val file = new File("tests/src/test/resources/AvroTypeProviderTestNoNamespaceDeeplyNested.avro")

      val schema = AvroTypeProviderTestNoNamespaceDeeplyNested.SCHEMA$

      val userDatumReader = new SpecificDatumReader[AvroTypeProviderTestNoNamespaceDeeplyNested](schema)
      val dataFileReader = new DataFileReader[AvroTypeProviderTestNoNamespaceDeeplyNested](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)
    }
  }




  "A case class with in the default package (i.e. without a namespace) as an optional field's type" should {
    "serialize and deserialize correctly" in {

      val record = AvroTypeProviderTestNoNamespaceOption(Some(AvroTypeProviderTestNoNamespace(6)))

      val file = new File("tests/src/test/resources/AvroTypeProviderTestNoNamespaceOption.avro")
      val schema = AvroTypeProviderTestNoNamespaceOption.SCHEMA$
      val userDatumReader = new GenericDatumReader[AvroTypeProviderTestNoNamespaceOption](schema, schema, SpecificData.get)
      val dataFileReader = new DataFileReader[AvroTypeProviderTestNoNamespaceOption](file, userDatumReader)
      val sameRecord = dataFileReader.next()

      sameRecord must ===(record)

    }
  }


*/







}
