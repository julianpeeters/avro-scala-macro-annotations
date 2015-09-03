
package test

import com.julianpeeters.avro.annotations._

import org.apache.avro.specific._
import org.apache.avro.generic._
import org.apache.avro.file._

import java.io.File

@AvroRecord
case class AvroTypeProviderPreexistingCompanionTest00(var x: Int)

object AvroRecordExample extends App {
  val record = AvroTypeProviderPreexistingCompanionTest00(4)

  val file = File.createTempFile("record", "avro")
    file.deleteOnExit()

  val userDatumWriter = new SpecificDatumWriter[AvroTypeProviderPreexistingCompanionTest00]
  val dataFileWriter = new DataFileWriter[AvroTypeProviderPreexistingCompanionTest00](userDatumWriter)
    dataFileWriter.create(AvroTypeProviderPreexistingCompanionTest00.SCHEMA$, file);
    dataFileWriter.append(record);
    dataFileWriter.close();

  val schema = AvroTypeProviderPreexistingCompanionTest00.SCHEMA$
  val userDatumReader = new SpecificDatumReader[AvroTypeProviderPreexistingCompanionTest00](schema)
  val dataFileReader = new DataFileReader[AvroTypeProviderPreexistingCompanionTest00](file, userDatumReader)
  val sameRecord = dataFileReader.next()

  println("deserialized record is the same as the pre-serialized record?: " + (sameRecord == record) )

}
