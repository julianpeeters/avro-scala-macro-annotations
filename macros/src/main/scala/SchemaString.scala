package avro.provider

import org.apache.avro.Schema

object SchemaString {
    def getSchemaStringFromFile(infile: java.io.File): String = {
      val bufferedInfile = scala.io.Source.fromFile(infile, "iso-8859-1")
      val parsable = new String(bufferedInfile.getLines.mkString.dropWhile(_ != '{').toCharArray)
      val avroSchema = new Schema.Parser().parse(parsable)
      avroSchema//.toString
    }
}
