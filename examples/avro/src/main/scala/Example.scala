
package sample

import com.julianpeeters.avro.annotations._

import org.apache.avro.specific._
import org.apache.avro.generic._
import org.apache.avro.file._

import java.io.File

@AvroRecord
case class MyRecord(var i: Int, var j: Int)

object Example extends App {
  val record = MyRecord(4, 8)
    println(record.getSchema)

  val file = File.createTempFile("record", "avro")
    file.deleteOnExit()

  val userDatumWriter = new SpecificDatumWriter[MyRecord]
  val dataFileWriter = new DataFileWriter[MyRecord](userDatumWriter)
    dataFileWriter.create(record.getSchema(), file);
    dataFileWriter.append(record);
    dataFileWriter.close();

  val fileSchema = new DataFileReader(file, new GenericDatumReader[MyRecord]).getSchema
  val userDatumReader = new SpecificDatumReader[MyRecord](fileSchema)
  val dataFileReader = new DataFileReader[MyRecord](file, userDatumReader)
  val sameRecord = dataFileReader.next()
 
  println("deserialized record is the same as the pre-serialized record?: " + (sameRecord == record) )
 
}
