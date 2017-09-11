package io.github.sclearn
package dataset
package impl


private[sclearn] class ArrayRow(protected[impl] val values: Array[Any]) extends Row {
	def this(size: Int) = this(new Array[Any](size))
	def get(i: Int): Any = values(i)
	def length: Int = values.length
}
