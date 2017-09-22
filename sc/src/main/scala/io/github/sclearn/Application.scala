package io.github.sclearn


object Application {

	def gradientDescent() {

		import org.nd4j.linalg.factory.Nd4j
		import org.nd4j.linalg.api.ndarray.INDArray
		import org.nd4j.linalg.ops.transforms.{ Transforms => math }

		(1, 2, 3)

		val x = Nd4j.linspace(1, 10, 10).reshape(1, 10)
		val y = Nd4j.ones(10, 1)
		val s = math.sin(x)

		println(x.mmul(y))
	}


	def datasetExamples(): Unit = {

		val iris = Dataset.IRIS.fetch()
		println(iris.schema.treeString)

	}

	def main(args: Array[String]): Unit = {
		gradientDescent()
	}

}
