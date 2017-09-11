package io.github.sclearn
package dataset
package impl

import org.apache.spark.sql.types.StructType


class ArrayDataset[T](override val schema: StructType, protected val values: Array[T]) extends Dataset[T] {

	def collect(): Array[T] = values

}
