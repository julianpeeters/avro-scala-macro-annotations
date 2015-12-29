Introduction
------------

This project is an example of computing the classic WordCount from a
corpus of [Apache Avro](http://avro.apache.org/)-encoded records using Spark. There are examples using the Avro Specific records represented by Scala case classes made serializable by avro-scala-macro-annotations.

Adapted from: Joe Crobak's [AvroSparkScala](https://github.com/jcrobak/avro-examples/blob/master/avro-spark/src/main/scala/AvroSparkScala.scala) and Sandy Ryza's [simplesparkavroapp](https://github.com/sryza/simplesparkavroapp).


Spark
--------

Requirements
============

* Java 1.7+
* sbt
//* Hadoop (tested with Hadoop 1.2.1) installed and `hadoop` on the `PATH`.

Reading Avro Files
==================
Run in local mode with `$ sbt run`, then choose number `1`


Writing Avro Files
==================
Run in local mode with `$ sbt run`, then choose number `2`
