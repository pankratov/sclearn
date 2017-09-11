package io.github.sclearn
package dataset
package impl
package known

import java.nio.file.Path
import java.io.DataInputStream
import java.io.FileInputStream

import util.Resource
import monitor.ProgressMeter


/*
trait PackPart {
	def name: String
	def uri: String
	def checksum: String
}

trait Pack {
	def name: String
	def version: String
	def description: String
	def parts: Seq[PackPart]
}
*/

case object MNIST_TRAIN_IMAGES extends PackPart {
	val name		= "train-images"
	val file		= "train-images-idx3-ubyte"
	val ext			= ".gz"
	val uri			= "http://yann.lecun.com/exdb/mnist/train-images-idx3-ubyte.gz"
	val checksum	= ""
	val compressed	= true
}

case object MNIST_TRAIN_LABELS extends PackPart {
	val name		= "train-labels"
	val file		= "train-labels-idx1-ubyte"
	val ext			= ".gz"
	val uri			= "http://yann.lecun.com/exdb/mnist/train-labels-idx1-ubyte.gz"
	val checksum	= ""
	val compressed	= true
}

case object MNIST_TEST_IMAGES extends PackPart {
	val name		= "test-images"
	val file		= "t10k-images-idx3-ubyte"
	val ext			= ".gz"
	val uri			= "http://yann.lecun.com/exdb/mnist/t10k-images-idx3-ubyte.gz"
	val checksum	= ""
	val compressed	= true
}

case object MNIST_TEST_LABELS extends PackPart {
	val name		= "test-labels"
	val file		= "t10k-labels-idx1-ubyte"
	val ext			= ".gz"
	val uri			= "http://yann.lecun.com/exdb/mnist/t10k-labels-idx1-ubyte.gz"
	val checksum	= ""
	val compressed	= true
}

case object MNIST_PACK extends Pack {
	val name		= "MNIST"
	val file		= "mnist"
	val version		= "initial"
	val description	= """
The MNIST database of handwritten digits, available from this page, has a training set of 60,000 examples, and a test set of 10,000 examples. It is a subset of a larger set available from NIST. The digits have been size-normalized and centered in a fixed-size image.
It is a good database for people who want to try learning techniques and pattern recognition methods on real-world data while spending minimal efforts on preprocessing and formatting.
URL: http://yann.lecun.com/exdb/mnist/
	"""
	val parts		= Seq(
		MNIST_TRAIN_IMAGES
		, MNIST_TRAIN_LABELS
		, MNIST_TEST_IMAGES
		, MNIST_TEST_LABELS
	)
}

case class MNIST_TRAIN_IMAGES_FETCHER(
	override val progressMeter: ProgressMeter
	, override val cacheDir: Path
) extends HTTPFetcher[ArrayRow](
	pack			= MNIST_PACK
	, part			= MNIST_TRAIN_IMAGES
	, progressMeter	= progressMeter
	, cacheDir		= cacheDir
) {

	def read(): Dataset[ArrayRow] = {
		progressMeter.info("reading: " + path.toString)
		for (in <- Resource(new DataInputStream(new FileInputStream(path.toString)))) {
			if (in.readInt() != 2051) {
				progressMeter.fail("Wrong MNIST code")
			}
			val count	= in.readInt()
			val width	= in.readInt()
			val height	= in.readInt()
			for (c <- 0 until count) {
				for (x <- 0 until width; y <- 0 until height) {
					in.readUnsignedByte()
				}
			}
		}
		null
	}

}

case class MNIST_FETCHER_BUILDER(
	override val progressMeter: ProgressMeter
	, override val cacheDir: Path
) extends FetcherBuilder[ArrayRow] {

	def part(name: String): Fetcher[ArrayRow] = {
		name match {
			case `MNIST_TRAIN_IMAGES`.`name` => MNIST_TRAIN_IMAGES_FETCHER(progressMeter, cacheDir)
			case _ => {
				val msg = "Dataset mnist has no part named <" + name + ">. Try one of <train-images>, <train-labels>, <test-images>, <test-labels>."
				progressMeter.fail(msg)
				throw new RuntimeException(msg)
			}
		}
	}
}
