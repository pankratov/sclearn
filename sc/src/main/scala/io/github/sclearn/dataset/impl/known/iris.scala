package io.github.sclearn
package dataset
package impl
package known

import java.nio.file.Path
import java.io.DataInputStream
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.FileReader
import java.io.BufferedReader
import java.util.StringTokenizer

import io.github.sclearn.dataset.spark.sql.types._

import util.Resource
import monitor.ProgressMeter


case object IRIS_ALL extends PackPart {
	val name		= "all"
	val file		= "iris"
	val ext			= ""
	val uri			= "https://archive.ics.uci.edu/ml/machine-learning-databases/iris/iris.data"
	val checksum	= ""
	val compressed	= false
}

case object IRIS_PACK extends Pack {
	val name		= "IRIS"
	val file		= "iris"
	val version		= "08-Mar-1993"
	val description	= """
	"""
	val parts		= Seq(IRIS_ALL)
}

case class IRIS_FETCHER(
	override val progressMeter: ProgressMeter
	, override val cacheDir: Path
) extends HTTPFetcher[ArrayRow](
	pack			= IRIS_PACK
	, part			= IRIS_ALL
	, progressMeter	= progressMeter
	, cacheDir		= cacheDir
) {

	private[this] val schema = StructType(
		StructField("sepal_width", DoubleType, true) ::
		StructField("sepal_length", DoubleType, false) ::
		StructField("petal_width", DoubleType, true) ::
		StructField("petal_length", DoubleType, false) ::
		StructField("class", StringType, false) ::
		Nil
	)

	def read(): Dataset[ArrayRow] = {
		progressMeter.info("reading: " + path.toString)
		Resource(new BufferedReader(new FileReader(path.toString))).map{ in =>
			var i = 0
			var line = in.readLine()
			val ds = new Array[ArrayRow](150)
			while (line != null && i < 150) {
				if (line.length > 0) {
					val arr = new Array[Any](5)
					val tkn = new StringTokenizer(line, ",")
					arr(0) = java.lang.Double.valueOf(tkn.nextToken())
					arr(1) = java.lang.Double.valueOf(tkn.nextToken())
					arr(2) = java.lang.Double.valueOf(tkn.nextToken())
					arr(3) = java.lang.Double.valueOf(tkn.nextToken())
					arr(4) = tkn.nextToken()
					ds(i) = new ArrayRow(schema, arr)
				}
				line = in.readLine()
				i += 1
			}
			new ArrayDataset[ArrayRow](schema, ds)
		}
	}

}

case class IRIS_FETCHER_BUILDER(
	override val progressMeter: ProgressMeter
	, override val cacheDir: Path
) extends FetcherBuilder[ArrayRow] {

	def part(name: String): Fetcher[ArrayRow] = {
		name match {
			case `IRIS_ALL`.`name` => IRIS_FETCHER(progressMeter, cacheDir)
			case _ => {
				val msg = "Dataset iris no part named " + name + ". Try <all> instead."
				progressMeter.fail(msg)
				throw new RuntimeException(msg)
			}
		}
	}
}
