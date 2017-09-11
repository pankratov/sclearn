package io.github.sclearn
package dataset
package impl


class ArrayDataset[T](protected val values: Array[T]) extends Dataset[T] {

	def collect(): Array[T] = values

}
