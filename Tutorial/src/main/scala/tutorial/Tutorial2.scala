package tutorial

import com.twitter.scalding._
import TDsl._
import com.twitter.scalding.typed.Syntax._
import com.twitter.algebird.mutable.PriorityQueueToListAggregator

/**
 * Finding the most representative words in every canto using TF-IDF
 * Run it using run tutorial.Tutorial2 --k 20 (or any other number of common words)
 */
class Tutorial2(args : Args) extends Job(args) {
  def tokenize(text : String) : Array[String] = {
    // Lowercase each word and remove punctuation.
    text.toLowerCase.replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+").filter(_.length > 0)
  }

  val topWords = args("k").toInt

  val cantos = TypedTsv[(String, String)]("data/grimmBooks.txt")

  val words = cantos.flatMap{ case (c, ws) => tokenize(ws).map(w => (c, w)) }

  val tf = words.groupBy(identity).size
  val D = 62.0 // not necessary to compute for ranking
  val df = tf.groupBy(_._1._2).size

  val tfidf = tf
    .joinBy(df)(_._1._2, _._1)
    .mapValues{ case (((c, w), t), (_, d)) => (c, w, t * math.log(D / d)) }
    .values
    .filter(_._3 > 0)
    .write(TypedTsv[(String, String, Double)]("data/grimmTfIdf.tsv"))

  tfidf
    .groupBy(_._1)
    .aggregate(new PriorityQueueToListAggregator[(String, String, Double)](topWords)(Ordering.by(-_._3)))
    .values
    .flatten
    .write(TypedTsv[(String, String, Double)]("data/topWordsInEveryBook.tsv"))
}
