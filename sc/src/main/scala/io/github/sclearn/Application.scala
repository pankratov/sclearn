package io.github.sclearn


object Application {

	def main(args: Array[String]): Unit = {

		val iris = Dataset.IRIS.fetch()
		println(iris.schema.treeString)

	}

}
