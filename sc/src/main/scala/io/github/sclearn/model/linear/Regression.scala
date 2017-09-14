package io.github.sclearn
package model
package linear


trait Regression[T] {

	def fit(x: Dataset[T], y: Dataset[T]): Unit

}
