package io.github.sclearn
package dataset

import java.nio.file.Path
import java.nio.file.Paths
import monitor.ProgressMeter


trait Fetcher[T] {

	def pack: Pack
	def part: PackPart
	def cacheDir: Path
	def progressMeter: ProgressMeter

	def fetch(force: Boolean): Dataset[T]

	def fetch(): Dataset[T] = fetch(force = false)

	protected def tmpPath(): Path = {
		Paths.get(cacheDir.toString, pack.file, part.file + part.ext)
	}

	protected def path(): Path = {
		Paths.get(cacheDir.toString, pack.file, part.file)
	}
}
