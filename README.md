This is a work in progress. Criticism is appreciated.

##Herein lie two macro annotations that will generate boilerplate for you when using Avro from Scala:

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


Now the fields and methods necessary for de/serialization are generated for you at compile time.

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

##2) Avro-Record: 
Implements `SpecificRecord` at compile time so you can use Scala case classes to represent Avro records (like [Scalavro](https://github.com/GenslerAppsPod/scalavro) or [Salat-Avro](https://github.com/julianpeeters/salat-avro/tree/master), but for the Apache Avro runtime so that it runs on your cluster). Since Avro-Scala-Compiler-Plugin doesn't work with Scala 2.10+ but the compiler still stumps me, I ported the serialization essentials over to use Scala Macro Annotations instead of a compiler plugin. 

Now you can annotate a case class that you'd like to have serve as your Avro record:

        package sample

        @AvroRecord
        case class A(var i: Int)

        @AvroRecord
        case class B(var a: Option[A])


  expands to implement `SpecificRecord` with the schema:

        {"type":"record","name":"B","namespace":"sample","doc":"Auto-generated schema","fields":[{"name":"a","type":["null",{"type":"record","name":"A","doc":"Auto-generated schema","fields":[{"name":"i","type":"int","doc":"Auto-Generated Field"}]}],"doc":"Auto-Generated Field"}]}}

Use the expanded class as you would any other code-gen'd class with the SpecificRecord API, e.g.:
        val sdw = SpecificDatumWriter[B]


####Please note:
1) Works with Avro Primitives, Nullable fields (represented by Option), and Lists for Arrays. Map, Fixed, other collections besides List, and true unions not yet supported.

2) Provide a `null` argument (e.g. `@AvroRecord(null)` ) to force the omission of a namespace in the generated schema. This must be done in order to read files with no namespace in the schema, and also allows the reading and writing of Avros irrespecitive of the package of the case class.
