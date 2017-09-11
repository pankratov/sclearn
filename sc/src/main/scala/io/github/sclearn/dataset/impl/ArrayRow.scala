package io.github.sclearn
package dataset
package impl

import org.apache.spark.sql.types.StructType


private[sclearn] class ArrayRow(val schema: StructType, protected[impl] val values: Array[Any]) extends Row {
	def get(i: Int): Any = values(i)
	def length: Int = values.length
}
