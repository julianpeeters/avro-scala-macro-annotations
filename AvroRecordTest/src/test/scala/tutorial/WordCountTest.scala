/*
 * Copyright (c) 2012 Twitter, Inc.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package tutorial

// Specs2
import org.specs2.mutable.Specification

// Scalding
import com.twitter.scalding._
import java.io.File

import org.apache.avro.specific._
import org.apache.avro.Schema
import org.apache.avro.Schema.{Type => AvroType}
import org.apache.avro.file._

class WordCountTest extends Specification {

  "A specific record represented by a case class" should {
    "serialize and deserialize correctly" in {

val record1 = MyRecord(rec(2))
println(record1)
val file = new File("data/users.avro");
val userDatumWriter = new SpecificDatumWriter[MyRecord];
val dataFileWriter = new DataFileWriter[MyRecord](userDatumWriter);
dataFileWriter.create(record1.getSchema(), file);
//dataFileWriter.create(Schema.create(AvroType.INT), file);
dataFileWriter.append(record1);
//ataFileWriter.append(user2);
//dataFileWriter.append(user3);
dataFileWriter.close();

println(record1.getSchema)
val userDatumReader = new SpecificDatumReader[MyRecord](new DataFileReader(file, new SpecificDatumReader[MyRecord]).getSchema);
println(record1.SCHEMA$)
println(classOf[MyRecord].getDeclaredField("""SCHEMA$"""))
val dataFileReader = new DataFileReader[MyRecord](file, userDatumReader);
//var user = null;
//while (dataFileReader.hasNext()) {
// Reuse user object by passing it to next(). This saves us from
// allocating and garbage collecting many objects for files with
// many items.
// def user: tutorial.MyRecord = dataFileReader.next(user);
//System.out.println(user);
//}

      "ions".length must ===(4)
    }
  }
/*
  "A WordCount job" should {
//    JobTest("com.snowplowanalytics.hadoop.scalding.WordCountJob").
    JobTest("tutorial.WordCountJob").
      arg("input", "inputFile").
      arg("output", "outputFile").
      source(TextLine("inputFile"), List("0" -> "hack hack hack and hack")).
      sink[(String,Int)](Tsv("outputFile")){ outputBuffer =>
        val outMap = outputBuffer.toMap
        "count words correctly" in {
          outMap("hack") must be_==(4)
          outMap("and") must be_==(1)
        }
      }.
      run.
      finish
  }


*/



}
