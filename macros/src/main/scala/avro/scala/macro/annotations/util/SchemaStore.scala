package com.julianpeeters.avro.annotations
package util

import java.util.concurrent.ConcurrentHashMap
import collection.JavaConversions._
 
import org.apache.avro.Schema

object SchemaStore {

  val schemas: scala.collection.concurrent.Map[String, Schema] = scala.collection.convert.Wrappers.JConcurrentMapWrapper(new ConcurrentHashMap[String, Schema]())

}
