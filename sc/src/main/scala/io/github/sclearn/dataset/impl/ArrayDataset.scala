package io.github.sclearn
package dataset
package impl

import io.github.sclearn.dataset.spark.sql.types.StructType


class ArrayDataset[T](override val schema: StructType, protected val values: Array[T]) extends Dataset[T] {

	override def collect(): Array[T] = values

}
