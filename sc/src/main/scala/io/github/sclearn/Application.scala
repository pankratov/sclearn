package io.github.sclearn


object Application {

	def main(args: Array[String]): Unit = {

		val iris = Dataset.IRIS.part("all").fetch()

		// val ds = Dataset("iris").
		// val mnist = Dataset.MNIST.fetch()

		/*

		// Simple interface
		val trn = Dataset.MNIST.part("train").fetch().normalize()
		val tst = Dataset.MNIST.part("test").fetch().normalize()
		val prd = Model.Linear.Classifier(reg = Model.Reg.L2).fit(trn).predict(tst)

		// Functional interface
		
		for (
			trn <- Dataset.MNIST.part("train").fetchOpt();
			tst <- Dataset.MNIST.part("test").fetchOpt()
		) {
			Model.Linear.Classifier(reg = Model.Reg.L2).fitOpt(trn).flatMap { mdl =>
				mdl.predictOpt()
			}
		}

		// Dataset("https://gist.github.com/sherzodv/996fd1/archive/d32c20a.zip").fetch()

		*/

	}

}
