package com.julianpeeters.avro.annotations

package conversions

import org.apache.avro.Schema
import Schema.Type
import org.apache.avro.generic.{ GenericArray, GenericData }
import org.apache.avro.util._

import java.nio.ByteBuffer

import scala.collection.Traversable
import scala.collection.mutable.ArrayBuffer

import java.util.{ Map => JMap, Iterator => JIterator, AbstractMap, AbstractSet }

/**
 * Motivated by:
 * http://stackoverflow.com/questions/2257341/java-scala-deep-collections-interoperability
 */
trait ==>>[A, B] extends ((Schema, A) => B) {
  /** True iff this transformation is the identity transform */
  def isIdentity: Boolean = false

  /** True iff this transformation is a (un)-boxing operation */
  def isBox: Boolean = false
}

object BasicTransforms {

  object IdentityTransform extends (Any ==>> Any) {
    def apply(s: Schema, a: Any) = a
    override def isIdentity = true
  }

  object toUtf8Conv extends (String ==>> Utf8) {
    def apply(s: Schema, a: String) = 
      if (a eq null) null
      else new Utf8(a)
  }

  object fromUtf8Conv extends (Utf8 ==>> String) {
    def apply(s: Schema, a: Utf8) = 
      if (a eq null) null
      else a.toString
  }

  object toByteBufferConv extends (Array[Byte] ==>> ByteBuffer) {
    def apply(s: Schema, a: Array[Byte]) = 
      if (a eq null) null
      else ByteBuffer.wrap(a)
  }

  object fromByteBufferConv extends (ByteBuffer ==>> Array[Byte]) {
    def apply(s: Schema, a: ByteBuffer) = 
      if (a eq null) null
      else a.array
  }

  import java.lang.{ Boolean => JBoolean, Short => JShort, Byte => JByte, Character => JCharacter, 
                     Integer => JInteger, Long => JLong, Float => JFloat, Double => JDouble }

  object shortToJIntConv extends (Short ==>> JInteger) {
    def apply(s: Schema, a: Short) = JInteger.valueOf(a.toInt)
  }

  object boxedShortToJIntConv extends (JShort ==>> JInteger) {
    def apply(s: Schema, a: JShort) = 
      if (a eq null) null
      else JInteger.valueOf(a.intValue)
  }

  object jintToShortConv extends (JInteger ==>> Short) {
    def apply(s: Schema, a: JInteger) = a.shortValue
  }

  object jintToBoxedShortConv extends (JInteger ==>> JShort) {
    def apply(s: Schema, a: JInteger) = 
      if (a eq null) null
      else JShort.valueOf(a.shortValue)
  }

  object byteToJIntConv extends (Byte ==>> JInteger) {
    def apply(s: Schema, a: Byte) = JInteger.valueOf(a.toInt)
  }

  object boxedByteToJIntConv extends (JByte ==>> JInteger) {
    def apply(s: Schema, a: JByte) = 
      if (a eq null) null
      else JInteger.valueOf(a.intValue)
  }

  object jintToByteConv extends (JInteger ==>> Byte) {
    def apply(s: Schema, a: JInteger) = a.byteValue
  }

  object jintToBoxedByteConv extends (JInteger ==>> JByte) {
    def apply(s: Schema, a: JInteger) = 
      if (a eq null) null
      else JByte.valueOf(a.byteValue)
  }

  object charToJIntConv extends (Char ==>> JInteger) {
    def apply(s: Schema, a: Char) = JInteger.valueOf(a.toInt)
  }

  object boxedCharToJIntConv extends (JCharacter ==>> JInteger) {
    // TODO: is there a better way to do this one?
    def apply(s: Schema, a: JCharacter) = 
      if (a eq null) null
      else JInteger.valueOf(a.charValue.toInt)
  }

  object jintToCharConv extends (JInteger ==>> Char) {
    // TODO: is there a better way to do this one?
    def apply(s: Schema, a: JInteger) = a.intValue.toChar
  }

  object jintToBoxedCharConv extends (JInteger ==>> JCharacter) {
    // TODO: is there a better way to do this one?
    def apply(s: Schema, a: JInteger) = 
      if (a eq null) null
      else JCharacter.valueOf(a.intValue.toChar)
  }

  object intToJIntConv extends (Int ==>> JInteger) {
    def apply(s: Schema, a: Int) = JInteger.valueOf(a)
    override def isBox = true
  }

  object jintToIntConv extends (JInteger ==>> Int) {
    def apply(s: Schema, a: JInteger) = a.intValue
    override def isBox = true
  }

  object longToJLongConv extends (Long ==>> JLong) {
    def apply(s: Schema, a: Long) = JLong.valueOf(a)
    override def isBox = true
  }

  object jlongToLongConv extends (JLong ==>> Long) {
    def apply(s: Schema, a: JLong) = a.longValue
    override def isBox = true
  }

  object floatToJFloatConv extends (Float ==>> JFloat) {
    def apply(s: Schema, a: Float) = JFloat.valueOf(a)
    override def isBox = true
  }

  object jfloatToFloatConv extends (JFloat ==>> Float) {
    def apply(s: Schema, a: JFloat) = a.floatValue
    override def isBox = true
  }

  object doubleToJDoubleConv extends (Double ==>> JDouble) {
    def apply(s: Schema, a: Double) = JDouble.valueOf(a)
    override def isBox = true
  }

  object jdoubleToDoubleConv extends (JDouble ==>> Double) {
    def apply(s: Schema, a: JDouble) = a.doubleValue
    override def isBox = true
  }

  object booleanToJBooleanConv extends (Boolean ==>> JBoolean) {
    def apply(s: Schema, a: Boolean) = JBoolean.valueOf(a)
    override def isBox = true
  }

  object jbooleanToBooleanConv extends (JBoolean ==>> Boolean) {
    def apply(s: Schema, a: JBoolean) = a.booleanValue
    override def isBox = true
  }

  private[conversions] final val StringSchema = Schema.create(Type.STRING)

}
/*
trait BuilderFactory[Elem, Coll] {
  def newBuilder: scala.collection.mutable.Builder[Elem, Coll]
}

trait HasBuilderFactories extends HasCollectionBuilders
                          with    HasImmutableBuilders
                          with    HasMutableBuilders
*/
trait HasAvroConversions extends HasAvroPrimitiveConversions 
                     //    with    HasBuilderFactories 
                         //with    HasTraversableConversions
                         with    HasOptionConversions {

  import BasicTransforms._

  @inline def convert[A, B](s: Schema, a: A)(implicit trfm: A ==>> B): B = trfm(s, a)

  @inline implicit def identity[A] =
    IdentityTransform.asInstanceOf[==>>[A, A]]

}

trait HasAvroPrimitiveConversions {

  import BasicTransforms._

  implicit def stringToUtf8 = toUtf8Conv 
  implicit def utf8ToString = fromUtf8Conv

  implicit def byteArrayToByteBuffer = toByteBufferConv
  implicit def byteBufferToByteArray = fromByteBufferConv

  implicit def shortToJInt  = shortToJIntConv
  implicit def jshortToJInt = boxedShortToJIntConv
  implicit def jintToShort  = jintToShortConv
  implicit def jintToJShort = jintToBoxedShortConv

  implicit def byteToJInt   = byteToJIntConv
  implicit def jbyteToJInt  = boxedByteToJIntConv
  implicit def jintToByte   = jintToByteConv
  implicit def jintToJByte  = jintToBoxedByteConv

  implicit def charToJInt   = charToJIntConv
  implicit def jcharToJInt  = boxedCharToJIntConv
  implicit def jintToChar   = jintToCharConv
  implicit def jintToJChar  = jintToBoxedCharConv

  implicit def intToJInt = intToJIntConv
  implicit def jintToInt = jintToIntConv

  implicit def longToJLong = longToJLongConv
  implicit def jlongToLong = jlongToLongConv

  implicit def floatToJFloat = floatToJFloatConv
  implicit def jfloatToFloat = jfloatToFloatConv

  implicit def doubleToJDouble = doubleToJDoubleConv
  implicit def jdoubleToDouble = jdoubleToDoubleConv

  implicit def booleanToJBoolean = booleanToJBooleanConv
  implicit def jbooleanToBoolean = jbooleanToBooleanConv

}

trait HasOptionConversions {

  implicit def toOption[FromElem, ToElem](implicit trfm: FromElem ==>> ToElem) = new (FromElem ==>> Option[ToElem]) {
    def apply(s: Schema, a: FromElem) = {
      val optA = Option(a)
      if (trfm.isIdentity || trfm.isBox)
        optA.asInstanceOf[Option[ToElem]]
      else
        optA.map(trfm.curried(s))
    }
  }

  implicit def fromOption[FromElem, ToElem]
    (implicit trfm: FromElem ==>> ToElem) = new (Option[FromElem] ==>> ToElem) {
    def apply(s: Schema, a: Option[FromElem]) =
      if (a eq null) null.asInstanceOf[ToElem]
      else {
        if (trfm.isIdentity || trfm.isBox)
          a.asInstanceOf[Option[ToElem]].getOrElse(null).asInstanceOf[ToElem]
        else
          a.map(trfm.curried(s)).getOrElse(null).asInstanceOf[ToElem]
      }
  }

}
/*
trait HasTraversableConversions {

  implicit def toScalaTraversable[FromElem, ToColl, ToElem]
    (implicit ev: ToColl <:< Traversable[ToElem], trfm: FromElem ==>> ToElem, bf: BuilderFactory[ToElem, ToColl]) = new (GenericArray[FromElem] ==>> ToColl) {
    def apply(s: Schema, a: GenericArray[FromElem]) = {
      import scala.collection.JavaConversions._
      val res = 
        if (trfm.isIdentity || trfm.isBox) asIterator(a.iterator.asInstanceOf[JIterator[ToElem]])
        else a.iterator.map(trfm.curried(s.getElementType))
      val builder = bf.newBuilder
      builder ++= res
      builder.result
    }
  }

  implicit def fromScalaTraversable[FromColl, FromElem, ToElem]
    (implicit ev: FromColl <:< Traversable[FromElem], trfm: FromElem ==>> ToElem): FromColl ==>> GenericArray[ToElem] = new (FromColl ==>> GenericArray[ToElem]) {
    def apply(s: Schema, a: FromColl) = {
      assert(s.getType == Type.ARRAY)
      if (a eq null) null
      else {
        val res =
          if (trfm.isIdentity || trfm.isBox)
            a.asInstanceOf[Traversable[ToElem]] 
          else
            a.map(trfm.curried(s.getElementType))
        /* View Generic Array */
        new GenericArray[ToElem] {
          import scala.collection.JavaConversions._
          def add(elem: ToElem) = error("ADD NOT SUPPORTED - ARRAY IS IMMUTABLE")
          def clear = error("CLEAR NOT SUPPORTED - ARRAY IS IMMUTABLE")
          def peek = error("PEEK NOT SUPPORTED")
          def size = res.size
          def iterator = 
            if (res.isInstanceOf[Iterable[_]]) // Iterable provides more efficient implementations of iterators
              res.asInstanceOf[Iterable[ToElem]].iterator 
            else
              res.toIterator
          def getSchema = s
        }
      }
    }
  }

  @inline private def transform[A, B](trfm: A ==>> B, schema: Schema, a: A): B = 
    if (trfm.isIdentity || trfm.isBox) a.asInstanceOf[B]
    else trfm(schema, a)

  import BasicTransforms.StringSchema

  implicit def toMap[M0, K0, V0, K1, V1]
    (implicit ev: M0 <:< Traversable[(K0, V0)], keyTrfm: K0 ==>> K1, valTrfm: V0 ==>> V1): M0 ==>> JMap[K1, V1] = new (M0 ==>> JMap[K1, V1]) {
    def apply(s: Schema, a: M0) = {
      assert(s.getType == Type.MAP)
      if (a eq null) null
      else {
        import scala.collection.JavaConversions._
        new AbstractMap[K1, V1] {
          def entrySet = new AbstractSet[JMap.Entry[K1, V1]] {
            def iterator = {
              val res = 
                a.map(t => new AbstractMap.SimpleImmutableEntry[K1, V1](transform(keyTrfm, StringSchema, t._1), transform(valTrfm, s.getValueType, t._2)))
              if (res.isInstanceOf[Iterable[_]])
                res.asInstanceOf[Iterable[JMap.Entry[K1, V1]]].iterator
              else 
                res.toIterator
            }
            def size = a.size
          }
        }
      }
    }
  }

  implicit def fromMap[K0, V0, M1, K1, V1]
    (implicit ev: M1 <:< Traversable[(K1, V1)], keyTrfm: K0 ==>> K1, valTrfm: V0 ==>> V1, bf: BuilderFactory[(K1, V1), M1]) = new (JMap[K0, V0] ==>> M1) {
    def apply(s: Schema, a: JMap[K0, V0]) = {
      import scala.collection.JavaConversions._
      val builder = bf.newBuilder
      builder ++= a.map(t => (transform(keyTrfm, StringSchema, t._1), transform(valTrfm, s.getValueType, t._2)))
      builder.result
    }
  }

}
*/
