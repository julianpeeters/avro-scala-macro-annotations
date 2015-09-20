// Adapted from: https://github.com/jcrobak/avro-examples/blob/master/avro-spark/src/main/scala/AvroSparkScala.scala, 
// and https://github.com/sryza/simplesparkavroapp

package com.miguno.avro

import registrator._
import com.miguno.avro._

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.avro.mapred.AvroKey
import org.apache.avro.mapreduce.AvroJob
import org.apache.avro.mapreduce.AvroKeyInputFormat

import org.apache.avro.mapreduce.AvroKeyOutputFormat
import org.apache.hadoop.fs.Path

import org.apache.hadoop.io.NullWritable
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat

import org.apache.hadoop.conf.Configuration
import org.apache.commons.lang.StringEscapeUtils.escapeCsv


object AvroSpecificWriteJob {
  def main(args: Array[String]) {
    val sparkConf = new SparkConf().setAppName("Spark Avro").setMaster("local")
    MyKryoRegistrator.register(sparkConf)
    val sc = new SparkContext(sparkConf)
    val c = new Configuration()
    val job = new Job(c)
    val conf = job.getConfiguration
    val outPath = "output/twitter.avro"

    val user1 = new twitter_schema("Alyssa", "I'm the boss", 4L)
    val user2 = new twitter_schema("Ben", "win my money", 5L)

    val records = sc.parallelize(Array(user1, user2))
    val withValues = records.map((x) => (new AvroKey(x), NullWritable.get))

    FileOutputFormat.setOutputPath(job, new Path(outPath))
    val schema = twitter_schema.SCHEMA$
    AvroJob.setOutputKeySchema(job, schema)
    job.setOutputFormatClass(classOf[AvroKeyOutputFormat[twitter_schema]])

    withValues.saveAsNewAPIHadoopDataset(conf)
  }
}


object AvroSpecificReadJob {
  def main(args: Array[String]) {
    val sparkConf = new SparkConf().setAppName("Spark Avro").setMaster("local")
    MyKryoRegistrator.register(sparkConf)
    val sc = new SparkContext(sparkConf)
    val c = new Configuration()
    val job = new Job(c)
    val conf = job.getConfiguration
    val outPath = "output/twitter-wordcount-scala-spark-specific.tsv"
    
    // A Schema must be specified (avro.mapreduce tries to make one reflectively but fails since Scala fields are private)
    val schema = twitter_schema.SCHEMA$
    AvroJob.setInputKeySchema(job, schema)

    val avroRdd = sc.newAPIHadoopFile("twitter.avro",
                      classOf[AvroKeyInputFormat[twitter_schema]],
                      classOf[AvroKey[twitter_schema]],
                      classOf[NullWritable],
                      conf)

    val specificRecords = avroRdd.map{case (ak, _) => ak.datum()}

    val wordCounts = specificRecords.map((sr: twitter_schema) => sr.get(1).asInstanceOf[String])
      .flatMap{tweet: String => tweet.split(" ")}
      .map(word => (word, 1))
      .reduceByKey((a, b) => a + b)

    val wordCountsFormatted = wordCounts.map{case (word, count) => (escapeCsv(word), count)}
      .map{case (word, count) => s"$word,$count"}

    wordCountsFormatted.saveAsTextFile(outPath)
    
  }
}

