package io.github.sclearn
package math
package loss


trait Loss {

	def value(p: Double, y: Double): Double
	def deriv(p: Double, y: Double): Double

}
