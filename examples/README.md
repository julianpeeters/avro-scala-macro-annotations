avro-scala-macro-annotation-examples
====================================

Example of how to use the @AvroRecord annotation to make a Scala case class Avro serializable. 

Annotated case classes that implement `SpecificRecord` are compatible with any Avro tool that takes care to not use the reflective constructors of the SpecificRecord API:

Examples of compatibility with the following:

- Avro*
- Scalding
- Spark*
- Scalavro

*requires a schema be passed in order to avoid reflection
