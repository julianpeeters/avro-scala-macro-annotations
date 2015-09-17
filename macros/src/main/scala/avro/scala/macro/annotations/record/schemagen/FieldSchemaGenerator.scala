package com.julianpeeters.avro.annotations
package record
package schemagen

import scala.reflect.macros.blackbox.Context

import collection.JavaConversions._
import java.util.{Arrays => JArrays}
import org.apache.avro.Schema
import org.apache.avro.Schema.Field
import org.apache.avro.Schema.{Type => AvroType}
import org.apache.avro.util.Utf8

abstract class FieldSchemaGenerator {

  //necessary for type refinement when trying to pass dependent types
  val context: Context

  import context.universe._
  import Flag._

  //map is from https://github.com/radlab/avro-scala-compiler-plugin/blob/master/src/main/scala/plugin/SchemaGen.scala
  val primitiveClasses: Map[Type, Schema] = Map(
    /** Primitives in the Scala and Avro sense */
    typeOf[Int]     -> Schema.create(AvroType.INT),
    typeOf[Float]   -> Schema.create(AvroType.FLOAT),
    typeOf[Long]    -> Schema.create(AvroType.LONG),
    typeOf[Double]  -> Schema.create(AvroType.DOUBLE),
    typeOf[Boolean] -> Schema.create(AvroType.BOOLEAN),
    typeOf[String]  -> Schema.create(AvroType.STRING),
    typeOf[Null]    -> Schema.create(AvroType.NULL),
    /** Primitives in the Avro sense */
    typeOf[java.nio.ByteBuffer] -> Schema.create(AvroType.BYTES),
    typeOf[Utf8]    -> Schema.create(AvroType.STRING)
  )

  def createSchema(tpe: context.universe.Type) : Schema = {
    tpe match {
      case x if (primitiveClasses.contains(x)) => primitiveClasses(x)
      case x @ TypeRef(pre, symbol, args) if (x <:< typeOf[List[Any]] && args.length == 1)  => {
        Schema.createArray(createSchema(args.head))
      }
      case x @ TypeRef(pre, symbol, args) if (x <:< typeOf[Option[Any]] && args.length == 1) => {
        if (args.head <:< typeOf[Option[Any]]) {
          throw new UnsupportedOperationException("Implementation limitation: Cannot immediately nest Option types")
        }
        else Schema.createUnion(JArrays.asList(Array(createSchema(typeOf[Null]), createSchema(args.head)):_*))
      }
      case x @ TypeRef(pre, symbol, args) if (x <:< typeOf[Map[String, Any]] && args.length == 2)  => {
        Schema.createMap(createSchema(args(1)))
      }
      case x @ TypeRef(pre, symbol, args) if (x <:< typeOf[Product with Serializable] ) => {
        // if a case class (a nested record) is found, reuse the schema that was made and stored when its macro was expanded.
        // unsuccessful alternatives: reflectively getting the schema from its companion (can't get a tree from a Symbol),
        // or regenerating the schema (no way to get default param values from outside the current at compile time).
        SchemaStore.schemas(x.toString)
      }
      case x => throw new UnsupportedOperationException("Could not generate schema. Cannot support yet: " + x )
    }
  }

  def toAvroField(namespace: String, nme: TermName, tpe: Type, dv: Tree) = {
    val toJsonMatcher = new {val c: context.type = context; val ns: String = namespace} with ToJsonMatcher

    new Field(
      nme.toString.trim,
      createSchema(tpe),
      "Auto-Generated Field",
      toJsonMatcher.toJsonNode(dv)
    )
  }
}
