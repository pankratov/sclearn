package io.github.sclearn
package dataset
package impl

import io.github.sclearn.spark.sql.types.StructType


class ArrayDataset[T](override val schema: StructType, protected val values: Array[T]) extends Dataset[T] {

	def collect(): Array[T] = values

}
