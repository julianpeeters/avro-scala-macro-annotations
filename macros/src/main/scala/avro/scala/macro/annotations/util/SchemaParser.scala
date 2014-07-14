package com.julianpeeters.avro.annotations
package util

import org.apache.avro.file.DataFileReader
import org.apache.avro.generic.{GenericDatumReader, GenericRecord}
import org.apache.avro.Schema
import org.apache.avro.Schema.Parser
import org.apache.avro.Schema.Type._

import scala.collection.JavaConverters._

object SchemaParser {
  def getSchema(infile: java.io.File) = {
    val schema = infile.getName.split("\\.").last match {
      case "avro" =>
        val gdr = new GenericDatumReader[GenericRecord]
        val dfr = new DataFileReader(infile, gdr)
        dfr.getSchema
      case "avsc" =>
        new Parser().parse(infile)
      case _ => throw new Exception("Invalid file ending. Must be .avsc for plain text json files and .avro for binary files.")
    }
    schema.getType match {
      case UNION  => schema.getTypes.asScala.foreach(s => ClassFieldStore.storeClassFields(s))
      case RECORD => ClassFieldStore.storeClassFields(schema)
      case _      => error("The Schema in the datafile is neither a record nor a union of records, nothing to map to case class.")
    }
  }
}
