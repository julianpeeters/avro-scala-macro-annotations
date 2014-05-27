##Herein lie assorted macro annotations for working with Avro in Scala:

1) `@AvroTypeProvider("path/to/schema")` - Automatically convert Avro Schemas to Scala case class definitions at compile time


2) `@AvroRecord` - Use Scala classes to represent your Avro records, serializable by the Apache Avro runtime without bulky IDL/code-gen steps (a port of [Avro-Scala-Compiler-Plugin](https://code.google.com/p/avro-scala-compiler-plugin/))

Get the dependency with:

        //planning to publish to Sonatype, for now please use `publish-local`
        "org.julianpeeters" % "avro-scala-macro-annotations" % "0.1-SNAPSHOT"

Use the annotations separately, or together like this:

        package sample
        
        import com.julianpeeters.avro.annotations._
         
        @AvroTypeProvider("data/input.avro")
        @AvroRecord
        case class MyRecord()


First the fields are added automatically, then the methods necessary for de/serialization are generated for you, at compile time.

This is a work in progress. Criticism is appreciated.


##1) Avro-Type-Provider
If your use-case is "data-first" and you're using an Avro runtime library that allows you to use Scala case classes to represent your Avro records, then you are probably a little weary of transcribing Avro Schemas into their Scala case class equivalents. 

Now you can annotate an "empty" case class and it's members will be generated automatically at compile time, using the data found in the Schema of a given file:
 
  annotated empty case classes:


        import com.julianpeeters.avro.annotations._

        @AvroTypeProvider("data/input.avro")
        case class rec()
         
        @AvroTypeProvider("data/input.avro")
        case class MyRecord()


  given the schema automatically found in `input.avro`:
        

        {"type":"record","name":"MyRecord","namespace":"tutorial","doc":"Auto-generated schema","fields":[{"name":"x","type":{"type":"record","name":"Rec","doc":"Auto-generated schema","fields":[{"name":"i","type":"int","doc":"Auto-Generated Field"}]},"doc":"Auto-Generated Field"}]}}


  expands to:

        import com.julianpeeters.avro.annotations._

        @AvroTypeProvider("data/input.avro")
        case class Rec(i: Int)
         
        @AvroTypeProvider("data/input.avro")
        case class MyRecord(x: Rec)


####Please note:
1) The datafile *must* be available at compile time.

2) The filepath *must* be a String literal

3) The name of the empty case class *must* match the record name exactly 

4) The order of class definition *must* be such that the classes that represent the most-nested records are expanded first

5) A class that is doubly annotated with `@AvroTypeProvider` and `@AvroRecord` will be updated with vars instead of vals

##2) Avro-Record: 
Implements `SpecificRecord` at compile time so you can use Scala case classes to represent Avro records (like [Scalavro](https://github.com/GenslerAppsPod/scalavro) or [Salat-Avro](https://github.com/julianpeeters/salat-avro/tree/master), but for the Apache Avro runtime so that it runs on your cluster). Since Avro-Scala-Compiler-Plugin doesn't work with Scala 2.10+ but the compiler still stumps me, I ported the serialization essentials over to use [Scala Macro Annotations](http://docs.scala-lang.org/overviews/macros/annotations.html) instead of a compiler plugin. 

Now you can annotate a case class that you'd like to have serve as your Avro record:

        package sample

        @AvroRecord
        case class A(var i: Int)

        @AvroRecord
        case class B(var a: Option[A])


  expands to implement `SpecificRecord` with `put`, `get`, and `getSchema` methods, and the schema:

        {"type":"record","name":"B","namespace":"sample","doc":"Auto-generated schema","fields":[{"name":"a","type":["null",{"type":"record","name":"A","doc":"Auto-generated schema","fields":[{"name":"i","type":"int","doc":"Auto-Generated Field"}]}],"doc":"Auto-Generated Field"}]}}


Use the expanded class as you would a code-gen'd class with any SpecificRecord API. E.g.:


        //Writing avros - no reflection
        val datumWriter = new SpecificDatumWriter[B]
        val dataFileWriter = new DataFileWriter[B](datumWriter)


        //Reading avros - no reflection
        val schema = new DataFileReader(file, new GenericDatumReader[GenericRecord]).getSchema 
        val userDatumReader = new SpecificDatumReader[B](schema)
        val dataFileReader = new DataFileReader[B](file, userDatumReader)


####Please note:
1) Seamlessly compatible with Scalding(reading/writing) and Spark(writing), but since Scala fields are always private, vanilla Avro's API is reduced to only those constructors that do not rely on reflection to get the schema. Therefore one must use the no-argument constructor for `SpecificDatumWriter`, and provide an Avro `Schema` when constructing a `SpecificDatumReader` as in the example above.

2) Fields must be `var`s in order to be compatible with the SpecificRecord API

3) Works with Avro Primitives (`Int`, `Float`, `Long`, `Double`, `Boolean`, `String`, `Null`), Nullable fields (represented by Options), and Lists for Arrays. Map, Fixed, other collections besides List, and unions (beyond nullable fields) are not yet supported.

4) Provide a `null` argument (e.g. `@AvroRecord(null)` ) to force the omission of a namespace in the generated schema. This must be done in order to read files with no namespace in the schema into records with a namespace in Scalding, Spark, or any other tool that avoids reflection on the record (i.e. not vanilla Avro).

5) A class that is doubly annotated with `@AvroTypeProvider` and `@AvroRecord` will automatically be updated with vars instead of vals
