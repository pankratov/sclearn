package io.github.sclearn.ws


object Application {

	def main(args: Array[String]): Unit = {
		val webServer = new WebServer
		webServer.start("0.0.0.0", 3141)
	}

}
