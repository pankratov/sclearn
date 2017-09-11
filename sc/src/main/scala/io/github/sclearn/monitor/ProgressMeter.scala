package io.github.sclearn.monitor

trait ProgressMeter {
	def total: Long
	def total(value: Long)
	def remaining: Long
	def tick(value: Long): Unit

	def info(msg: String): Unit
	def warn(msg: String): Unit
	def fail(msg: String): Unit
}
