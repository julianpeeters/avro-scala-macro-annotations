package tutorial

import com.twitter.scalding._
import com.twitter.scalding.avro._
import TDsl._
import com.twitter.algebird.mutable.PriorityQueueToListAggregator

/**
 * Finding the most common words in the text
 * Run it using run tutorial.Tutorial1 --k 20 (or any other number of common words)
 */
class Tutorial1(args : Args) extends Job(args) {
  val topWords = args("k").toInt

  val wordCounts = TypedTsv[(String, Long)]("data/grimmWordCounts.tsv")

  // The naive way. Don't do that!
  /*wordCounts
    .groupAll
    .sortBy(-_._2)
    .take(topWords)
    .values
    .write(TypedTsv[(String, Long)](s"data/mostCommon${topWords}Words.tsv"))
    */

  wordCounts
    .aggregate(new PriorityQueueToListAggregator[(String, Long)](topWords)(Ordering.by(-_._2)))
    .flatten
    .write(TypedTsv[(String, Long)](s"data/mostCommon${topWords}Words.tsv"))
}
