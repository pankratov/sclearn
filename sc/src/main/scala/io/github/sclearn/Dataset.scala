package io.github.sclearn

import io.github.sclearn.dataset.spark.sql.types.StructType
import io.github.sclearn.dataset.impl.known._


trait Dataset[T] {

	def schema: StructType
	def collect(): Array[T]
	def map[U](func : T => U): Dataset[U] = ???
	def + (other: Dataset[T]): Dataset[T] = ???
	def * (scalar: T): Dataset[T] = ???
	def *: (scalar: T): Dataset[T] = *(scalar)
}

object Dataset {

	import dataset.Row
	import java.nio.file.Paths
	import monitor.ProgressMeter
	import dataset.FetcherBuilder
	import dataset.impl.known._

	private[this] val progressMeter = new ProgressMeter {
		private var _total = 0L
		private var _value = 0L
		def total = _total
		def remaining = _total - _value
		def total(value: Long): Unit = {
			_total = value
			println("File size: " + _total)
		}
		def tick(value: Long): Unit = {
			_value += value
			println("got: " + _value + ", remaining: " + remaining)
		}
		def info(msg: String): Unit = println(msg)
		def warn(msg: String): Unit = println(msg)
		def fail(msg: String): Unit = println(msg)
	}

	val IRIS	= IRIS_FETCHER_BUILDER(progressMeter, Paths.get("/tmp/sclearn"))
	val MNIST	= MNIST_FETCHER_BUILDER(progressMeter, Paths.get("/tmp/sclearn"))

}
