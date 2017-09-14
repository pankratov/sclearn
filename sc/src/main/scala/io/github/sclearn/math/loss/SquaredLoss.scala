package io.github.sclearn
package math
package loss


class SquaredLoss extends Loss {

	def value(p: Double, y: Double): Double = {
		0.5 * (p - y) * (p - y)
	}

	def deriv(p: Double, y: Double): Double = {
		p - y
	}

}
