package io.github.sclearn
package dataset


trait PackPart {
	def name: String
	def file: String
	def ext: String
	def uri: String
	def checksum: String
	def compressed: Boolean
}

trait Pack {
	def name: String
	def file: String
	def version: String
	def description: String
	def parts: Seq[PackPart]
}
