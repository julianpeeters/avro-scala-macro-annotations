package test

import org.specs2.mutable.Specification

class AvroTypeProvider00Test extends Specification {

  "A case class with an `Int` field" should {
    "deserialize correctly" in {
      val record = AvroTypeProviderTest00(1)
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an `Float` field" should {
    "deserialize correctly" in {
      val record = AvroTypeProviderTest01(1F)
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an `Long` field" should {
    "deserialize correctly" in {
      val record = AvroTypeProviderTest02(1L)
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an `Double` field" should {
    "deserialize correctly" in {
      val record = AvroTypeProviderTest03(1D)
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an `Boolean` field" should {
    "deserialize correctly" in {
      val record = AvroTypeProviderTest04(true)
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an `String` field" should {
    "deserialize correctly" in {
      val record = AvroTypeProviderTest05("hello world")
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an `Null` field" should {
    "deserialize correctly" in {
      val record = AvroTypeProviderTest06(null)
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an empty `Option[String]` field" should {
    "deserialize correctly" in {
      val record = AvroTypeProviderTest07(None)
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an empty `Option[Int]` field" should {
    "deserialize correctly" in {
      val record = AvroTypeProviderTest08(None)
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an `List[String]` field" should {
    "deserialize correctly" in {
      val record = AvroTypeProviderTest10(List("head.tail"))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an `List[Int]` field" should {
    "deserialize correctly" in {
      val record = AvroTypeProviderTest11(List(1, 2))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an `Option[String]` field" should {
    "deserialize correctly" in {
      val record = AvroTypeProviderTest12(Some("I'm here"))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an `Option[Int]` field" should {
    "deserialize correctly" in {
      val record = AvroTypeProviderTest13(Some(1))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an `Map[String,Int]` field" should {
    "deserialize correctly" in {
      val record =  AvroTypeProviderTestMap01(Map("justice"->1))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an `Map[String,String]` field" should {
    "deserialize correctly" in {
      val record =  AvroTypeProviderTestMap02(Map("justice"->"law"))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with an `Map[String,String]` field" should {
    "deserialize correctly" in {
      val record =  AvroTypeProviderTestMap03(Map("justice"->Some(List(1,2))))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with two `Map[String,Int]` fields" should {
    "deserialize correctly" in {
      val record = AvroTypeProviderTestMap04(Map("justice"->2, "law"->4), Map("sweet"->1))
      TestUtil.verifyRead(record)
    }
  }


  "A case class with two `Map[String,String]` fields" should {
    "deserialize correctly" in {
      val record = AvroTypeProviderTestMap05(Map("justice"->"crime", "law"->"order"), Map("sweet"->"sour"))
      TestUtil.verifyRead(record)
    }
  }


  "A case class with fields `x: Map[String, Option[List[Int]]], y: Map[String, Option[List[Int]]]`" should {
    "deserialize correctly" in {
      val record = AvroTypeProviderTestMap06(Map("justice"->None, "law"->Some(List(1,2))), Map("sweet"->Some(List(3,4))))
      TestUtil.verifyRead(record)
    }
  }


  "A case class with a `Map[String, Map[String, Int]]` field" should {
    "deserialize correctly" in {
      val record = AvroTypeProviderTestMap07(Map("pepper"->Map("onion"->6)))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with a `List[Map[String, Map[String, String]]]` field" should {
    "deserialize correctly" in {
      val record = AvroTypeProviderTestMap08(List(Map("pepper"->Map("onion"->"garlic")), Map("bongo"->Map("tabla"->"conga"))))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with a `Option[Map[String, Option[List[String]]]]` field" should {
    "deserialize correctly" in {
      val record = AvroTypeProviderTestMap09(Some(Map("pepper"->Some(List("howdy", "doody")))))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with `x: Map[String, Map[String, Int]], y: Map[String, Map[String, Int]]` fields" should {
    "deserialize correctly" in {
      val record = AvroTypeProviderTestMap10(Map("pepper"->Map("onion"->6)), Map("salt"->Map("garlic"->7)))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with `x: Map[String, Map[String, Int]], y: List[Map[String, Map[String, String]]]` fields" should {
    "deserialize correctly" in {
      val record = AvroTypeProviderTestMap11(Map("pepper"->Map("onion"->6)), List(Map("salt"->Map("garlic"->"oil")), Map("sriracha"->Map("chili"->"oil"))))
      TestUtil.verifyRead(record)
    }
  }

  "A case class with `x: Map[String, Map[String, AvroTypeProviderTest00]], y: Map[String, AvroTypeProviderTest58]` fields" should {
    "deserialize correctly" in {
      val record = AvroTypeProviderTestMap12(Map("c1"->Map("00"-> AvroTypeProviderTest00(2))), Map("58"->AvroTypeProviderTest58(AvroTypeProviderTest00(4))))
      TestUtil.verifyRead(record)
    }
  }

}
