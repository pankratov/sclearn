package io.github.sclearn
package dataset

import java.nio.file.Path
import java.nio.file.Paths
import monitor.ProgressMeter


trait FetcherBuilder[T] {

	def cacheDir: Path
	def progressMeter: ProgressMeter

	def part(name: String): Fetcher[T]


	def fetch(): Dataset[T]					= part("all").fetch()
	def fetch(force: Boolean): Dataset[T]	= part("all").fetch(force)

}
