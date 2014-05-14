###This is a work in progress. Criticism appreciated.

####Herein lie two macro annotations for making your favorite Avro runtime easier to use:

1) `@AvroTypeProvider` - "Data-first", Avro Schemas to Scala case class definitions


2) `@AvroRecord` - Use Scala case classes as Avro `SpecificRecord`s


Use them separately, or together like this:

        package sample
        
        import com.julianpeeters.avro.annotations._
         
        @AvroTypeProvider("data/input.avro")
        @AvroRecord
        case class MyRecord()



Planning to publish to Sonatype sometime soon, for now please  `publish-local`.

##1) Avro-Type-Provider: Automatically define case classes based on Avro Schemas at compile time
If your use-case is "data-first" and you're using an Avro runtime library that allows you to use Scala case classes to represent your Avro records, then you are probably a little weary of transcribing Avro Schemas into their Scala case class equivalents. 

Now you can annotate an "empty" case class and it's members will be generated automatically at compile time, using the data found in the Schema of a given file:
 
  annotated empty case classes:


        import com.julianpeeters.avro.annotations._

        @AvroTypeProvider("data/input.avro")
        case class rec()
         
        @AvroTypeProvider("data/input.avro")
        case class MyRecord()


  and the schema automatically read from `input.avro`:
        

        {"type":"record","name":"MyRecord","namespace":"tutorial","doc":"Auto-generated schema","fields":[{"name":"x","type":{"type":"record","name":"Rec","doc":"Auto-generated schema","fields":[{"name":"i","type":"int","doc":"Auto-Generated Field"}]},"doc":"Auto-Generated Field"}]}}


  expands to:

        import com.julianpeeters.avro.annotations._

        @AvroTypeProvider("data/input.avro")
        case class Rec(i: Int)
         
        @AvroTypeProvider("data/input.avro")
        case class MyRecord(x: Rec)


####Please note:
1) The datafile *must be available at compile time.
2) The filepath *must be a String literal
3) The name of the empty case class *must match the record name exactly 
4) The order of class definition *must be such that the classes that represent the most-nested records are expanded first

##2) Avro-Record: Implement `SpecificRecord` at compile time 
If you are using Avro `SpecificRecord`, then you are probably a little sad that the Avro-Scala-Compiler-Plugin doesn't work with Scala 2.10+. I was, but the compiler still stumps me, so I ported the serialization essentials over to use Scala Macro Annotations instead.

Now you can annotate a case class that youd like to serve as your Avro record:

        package sample

        @AvroRecord
        case class A(var i: Int)

        @AvroRecord
        case class B(var a: Option[A])


  expands to implement `SpecificRecord`, with the schema:

        {"type":"record","name":"B","namespace":"sample","doc":"Auto-generated schema","fields":[{"name":"a","type":["null",{"type":"record","name":"A","doc":"Auto-generated schema","fields":[{"name":"i","type":"int","doc":"Auto-Generated Field"}]}],"doc":"Auto-Generated Field"}]}}


####Please note:
1) Works with Avro Primitives, Arrays, Nullable fields represented by Option (Map, Fixed, and true unions not yet supported)
2) Provide a `null` argument (`@AvroRecord(null)`) to force the omission of a namespace in the generated schema, and thus read and write Avros regardless of the package of the case class.
