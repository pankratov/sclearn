package io.github.sclearn.util


object Resource {
	class ResourceWrapper[C <: AutoCloseable](protected val c: C) {
		def map[B](f: C => B): B = {
			try {
				f(c)
			} finally {
				if (c != null) c.close()
			}
		}
		def foreach(f: C => Unit): Unit = map(f)
		def flatMap[B](f: (C) => B): B = map(f)
		def withFilter(f: (C) => Boolean) = this
	}
	def apply[C <: AutoCloseable](c: C) = new ResourceWrapper(c)
}
