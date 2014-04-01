package tutorial

import com.twitter.scalding._
import TDsl._
import com.twitter.scalding.mathematics.Matrix._
import cascading.tuple.Fields

/**
 * Finding the most representative words in every canto using TF-IDF
 * Run it using run tutorial.Tutorial3
 */
class Tutorial3(args : Args) extends Job(args) {
  val booksMatrix = TypedTsv[(String, String, Double)]("data/grimmTfIdf.tsv").toMatrix
  val normedMatrix = booksMatrix.rowL2Normalize
  val similarities = (normedMatrix * normedMatrix.transpose).pipe.toTypedPipe[(String, String, Double)](Fields.ALL)

  similarities.filter(s => s._1 < s._2).groupAll.sortBy(-_._3).values.write(TypedTsv[(String, String, Double)]("data/bookSimilarities.tsv"))
}
