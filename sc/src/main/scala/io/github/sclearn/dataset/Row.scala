package io.github.sclearn.dataset

import io.github.sclearn.dataset.spark.sql.types.StructType


trait Row {

	def get(i: Int): Any
	def length: Int
	def schema: StructType

	def size: Int = length
	def apply(i: Int): Any = get(i)
	def isNullAt(i: Int): Boolean = get(i) == null
	def getBoolean(i: Int): Boolean = getAnyValAs[Boolean](i)
	def getByte(i: Int): Byte = getAnyValAs[Byte](i)
	def getShort(i: Int): Short = getAnyValAs[Short](i)
	def getInt(i: Int): Int = getAnyValAs[Int](i)
	def getLong(i: Int): Long = getAnyValAs[Long](i)
	def getFloat(i: Int): Float = getAnyValAs[Float](i)
	def getDouble(i: Int): Double = getAnyValAs[Double](i)
	def getString(i: Int): String = getAs[String](i)
	def getDecimal(i: Int): java.math.BigDecimal = getAs[java.math.BigDecimal](i)
	def getDate(i: Int): java.time.LocalDate = getAs[java.time.LocalDate](i)
	def getTimestamp(i: Int): java.time.LocalDateTime = getAs[java.time.LocalDateTime](i)
	def getSeq[T](i: Int): Seq[T] = getAs[Seq[T]](i)
	def getMap[K, V](i: Int): scala.collection.Map[K, V] = getAs[Map[K, V]](i)
	def getStruct(i: Int): Row = getAs[Row](i)
	def getAs[T](i: Int): T = get(i).asInstanceOf[T]
	def getAs[T](fieldName: String): T = getAs[T](fieldIndex(fieldName))

	def fieldIndex(name: String): Int = {
		throw new UnsupportedOperationException("fieldIndex on a Row without schema is undefined.")
	}

	def getValuesMap[T](fieldNames: Seq[String]): Map[String, T] = {
		fieldNames.map { name =>
			name -> getAs[T](name)
		}.toMap
	}

	def anyNull: Boolean = {
		val len = length
		var i = 0
		while (i < len) {
			if (isNullAt(i)) { return true }
			i += 1
		}
		false
	}

	def toSeq: Seq[Any] = {
		val n = length
		val values = new Array[Any](n)
		var i = 0
		while (i < n) {
			values.update(i, get(i))
			i += 1
		}
		values.toSeq
	}

	def mkString: String = toSeq.mkString
	def mkString(sep: String): String = toSeq.mkString(sep)
	def mkString(start: String, sep: String, end: String): String = toSeq.mkString(start, sep, end)

	override def toString: String = s"[${this.mkString(",")}]"

	private def getAnyValAs[T <: AnyVal](i: Int): T =
		if (isNullAt(i)) throw new NullPointerException(s"Value at index $i is null")
		else getAs[T](i)

}
