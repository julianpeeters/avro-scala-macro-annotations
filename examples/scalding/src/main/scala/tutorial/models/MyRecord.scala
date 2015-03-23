package tutorial

import com.julianpeeters.avro.annotations.AvroRecord

@AvroRecord
case class Person(var name: String, var age: Int)
