package com.julianpeeters.avro.annotations
package provider

import org.apache.avro.file.DataFileReader
import org.apache.avro.generic.{GenericDatumReader, GenericRecord}
import org.apache.avro.Schema
import org.apache.avro.Schema.Parser
import org.apache.avro.Schema.Type._

import scala.collection.JavaConverters._

object FileParser {

  def getSchemas(infile: java.io.File, parser : Parser): List[Schema] = {
    val schema = infile.getName.split("\\.").last match {
      case "avro" =>
        val gdr = new GenericDatumReader[GenericRecord]
        val dfr = new DataFileReader(infile, gdr)
        dfr.getSchema
      case "avsc" =>
        parser.parse(infile)
      case _ => throw new Exception("Invalid file ending. Must be .avsc for plain text json files and .avro for binary files.")
    }
    schema.getType match {
      case UNION  => {
        val recordSchemas = schema.getTypes.asScala.toList.filter(_.getType == RECORD)
        if (recordSchemas.nonEmpty) recordSchemas
        else sys.error("no record type found in the union from " + infile)
      }
      case RECORD => List(schema)
      case _      => sys.error("The Schema in the datafile is neither a record nor a union of a record type, nothing to map to case class.")
    }
  }

}
