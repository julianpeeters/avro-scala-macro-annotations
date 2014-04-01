package com.julianpeeters.avro.annotations
package provider

import org.apache.avro.Schema

object SchemaParser { 
    def getSchemaFromFile(infile: java.io.File): Schema = {
      val bufferedInfile = scala.io.Source.fromFile(infile, "iso-8859-1")
      val parsable = new String(bufferedInfile.getLines.mkString.dropWhile(_ != '{').toCharArray)
      val avroSchema = new Schema.Parser().parse(parsable) //parse(infile) finds an unexpected character...
      avroSchema
    }
}
