##Herein lie assorted macro annotations for working with Avro in Scala:

1) `@AvroTypeProvider("path/to/schema")` - Automatically convert Avro Schemas to Scala case class definitions for use in your favorite Scala Avro runtime.


2) `@AvroRecord` - Use Scala classes to represent your Avro records, serializable by the Apache Avro runtime (a port of [Avro-Scala-Compiler-Plugin](https://code.google.com/p/avro-scala-compiler-plugin/)).



####Get the dependency:
For Scala 2.11.x ([for Scala 2.10.x](https://github.com/julianpeeters/avro-scala-macro-annotations/issues/6#issuecomment-77973333) please use version 0.4.2 with sbt 0.13.8+):



        libraryDependencies += "com.julianpeeters" % "avro-scala-macro-annotations_2.11" % "0.9.1"


Macro annotations are only available in Scala 2.10.x, 2.11.x, and 2.12.x with the macro paradise plugin. Their inclusion in official Scala might happen in Scala 2.13 - [official docs](http://docs.scala-lang.org/overviews/macros/annotations.html). To use the plugin, add the following `build.sbt`:

        addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0-M5" cross CrossVersion.full)



####Usage:
Use the annotations separately, or together like this:

        package sample
        
        import com.julianpeeters.avro.annotations._
         
        @AvroTypeProvider("data/input.avro")
        @AvroRecord
        case class MyRecord()


First the fields are added automatically from an Avro Schema in a file, then the methods necessary for de/serialization are generated for you, all at compile time.


##1) Avro-Type-Provider
If your use-case is "data-first" and you're using an Avro runtime library that allows you to use Scala case classes to represent your Avro records, then you are probably a little weary of transcribing Avro Schemas into their Scala case class equivalents. 

Annotate an "empty" case class, and its members will be generated automatically at compile time using the data found in the Schema of a given file:

  given the schema automatically found in `input.avro` or `input.avsc`:
        
```
        {"type":"record","name":"MyRecord","namespace":"tutorial","doc":"Auto-generated schema","fields":[{"name":"x","type":{"type":"record","name":"Rec","doc":"Auto-generated schema","fields":[{"name":"i","type":"int","doc":"Auto-Generated Field"}]},"doc":"Auto-Generated Field","default":{"i":4}}]}}
```

  annotated empty case classes:

```scala
        import com.julianpeeters.avro.annotations._

        @AvroTypeProvider("data/input.avro")
        case class Rec()
         
        @AvroTypeProvider("data/input.avro")
        case class MyRecord()
```

  expand to:

 ```scala       
        package tutorial

        import com.julianpeeters.avro.annotations._

        @AvroTypeProvider("data/input.avro")
        case class Rec(i: Int)
         
        @AvroTypeProvider("data/input.avro")
        case class MyRecord(x: Rec = Rec(4))
```

####Please note:
1) The datafile must be available at compile time.

2) The filepath must be a String literal.

3) The name of the empty case class must match the record name exactly (peek at the schema in the file, if needed).

4) The order of class definition must be such that the classes that represent the most-nested records are expanded first.

5) A class that is doubly annotated with `@AvroTypeProvider` and `@AvroRecord` will be updated with vars instead of vals.

##2) Avro-Record: 
Implements `SpecificRecord` at compile time so you can use Scala case classes to represent Avro records (like [Scalavro](https://github.com/GenslerAppsPod/scalavro) or [Salat-Avro](https://github.com/julianpeeters/salat-avro/tree/master), but for the Apache Avro runtime so that it runs on your cluster). Since Avro-Scala-Compiler-Plugin doesn't work with Scala 2.10+ and the compiler still stumps me, I ported the serialization essentials over to use [Scala Macro Annotations](http://docs.scala-lang.org/overviews/macros/annotations.html) instead. 

Now you can annotate a case class that you'd like to have serve as your Avro record:

```scala
        package sample

        @AvroRecord
        case class A(var i: Int)

        @AvroRecord
        case class B(var a: Option[A] = None)
```

  expands to implement `SpecificRecord` with `put`, `get`, and `getSchema` methods, with the schema:

```
        {"type":"record","name":"B","namespace":"sample","doc":"Auto-generated schema","fields":[{"name":"a","type":["null",{"type":"record","name":"A","doc":"Auto-generated schema","fields":[{"name":"i","type":"int","doc":"Auto-Generated Field"}]}],"doc":"Auto-Generated Field",default: null}]}
```

Use the expanded class as you would a code-gen'd class with any `SpecificRecord` API. e.g.:

```scala
        //Writing avros 
        val datumWriter = new SpecificDatumWriter[B](B.SCHEMA$)
        val dataFileWriter = new DataFileWriter[B](datumWriter)


        //Reading avros
        val userDatumReader = new SpecificDatumReader[B](B.SCHEMA$)
        val dataFileReader = new DataFileReader[B](file, userDatumReader)
```

####Please note:
1) If your framework is one that relies on reflection to get the Schema, it will fail since Scala fields are private. Therefore preempt it by passing in a Schema to DatumReaders and DatumWriters (as in the Avro example above).

2) Fields must be `var`s in order to be compatible with the SpecificRecord API

3) Works with the following Avro datatypes:  
`int`
`float`
`long`
`double`
`boolean`
`string`
`null`
`array`
`map`
`record`
`union`*

*Optional fields of type `[null, t]` are represented by `Option[T]` 

The remaining avro types, `fixed`, `enum`, and `union` (beyond nullable fields), are not yet supported.


4) A class that is doubly annotated with `@AvroTypeProvider` and `@AvroRecord` will automatically be updated with vars instead of vals


