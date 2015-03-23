Introduction
------------

This project is an example of computing the classic WordCount from a
corpus of [Apache Avro](http://avro.apache.org/)-encoded records using Spark. There are examples using the Avro Specific records represented by Scala case classes made serializable by avro-scala-macro-annotations.




Spark
--------

Requirements
============

* Java 1.7+ 
* sbt
//* Hadoop (tested with Hadoop 1.2.1) installed and `hadoop` on the `PATH`.

Reading Avro Files
==================
Run in local mode with `$ sbt run` 


Writing Avro Files
==================

//TODO: Currently failing to write. Why? Maybe improved support in Spark 1.0? (see https://github.com/sryza/simplesparkavroapp)


