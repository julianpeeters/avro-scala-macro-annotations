package com.miguno.avro

import com.julianpeeters.avro.annotations._

// generates code for case class twitter_schema(var username: String, var tweet: String, var timestamp: Long)
@AvroTypeProvider("twitter.avro")
@AvroRecord
case class twitter_schema()