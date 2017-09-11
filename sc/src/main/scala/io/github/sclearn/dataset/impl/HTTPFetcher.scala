package io.github.sclearn
package dataset
package impl

import java.net.Proxy
import java.net.InetSocketAddress
import java.net.URL
import java.net.HttpURLConnection
import java.io.IOException
import java.io.FileOutputStream
import java.nio.file.Path
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.channels.Channels
import java.nio.channels.ReadableByteChannel

import util.Resource
import util.ArchiveUtils
import monitor.ProgressMeter


abstract class HTTPFetcher[T](
	override val pack: Pack
	, override val part: PackPart
	, override val progressMeter: ProgressMeter
	, override val cacheDir: Path
) extends Fetcher[T] {

	protected def read(): Dataset[T]

	def fetch(force: Boolean = false): Dataset[T] = {
		if (force || !Files.exists(path)) {
			download()
			if (part.compressed) {
				progressMeter.info("Unpack: " + part.file + part.ext)
				ArchiveUtils.unzipFileTo(tmpPath.toString, path.toString)
				progressMeter.info("Done")
			}
		} else {
			progressMeter.info("File already exists, skip downloading")
		}
		read()
	}

	private def getProxy(): (String, Int) = {
		val m = System.getenv()
		val http_proxy = m.get("http_proxy")
		val https_proxy = m.get("https_proxy")
		if (https_proxy != null) {
			val url = new URL(https_proxy)
			return (url.getHost, url.getPort)
		}
		if (http_proxy != null) {
			val url = new URL(http_proxy)
			return (url.getHost, url.getPort)
		}
		return ("", 0)
	}

	private def connect(): Option[HttpURLConnection] = {
		try {
			val url = new URL(part.uri)
			val px = getProxy()
			if (px._1.length > 0) {
				val proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(px._1, px._2))
				Some(url.openConnection(proxy).asInstanceOf[HttpURLConnection])
			} else {
				Some(url.openConnection().asInstanceOf[HttpURLConnection])
			}
		} catch {
			case _: Exception => None
		}
	}

	private def getFileSize(): Long = {
		progressMeter.info("Try to get file size")
		connect().flatMap{ cn =>
			try {
				Some(cn.getContentLengthLong())
			} catch {
				case e: IOException => {
					progressMeter.warn("Can't determine file size")
					None
				}
			} finally {
				cn.disconnect()
			}
		}.getOrElse(-1)
	}

	private def download(): Unit = {
		progressMeter.info("Download: " + part.uri)
		val batchSize = 1000000L
		val size = getFileSize()
		progressMeter.total(size)
		tmpPath.toFile.getParentFile.mkdirs()
		connect().foreach{ cn =>
			var in: ReadableByteChannel = null
			var out: FileOutputStream = null
			try {
				in	= Channels.newChannel(cn.getInputStream()).asInstanceOf[ReadableByteChannel]
				out	= new FileOutputStream(tmpPath.toString)
				val chan = out.getChannel()
				var pos = 0L
				var cnt = chan.transferFrom(in, pos, batchSize)
				progressMeter.info("File size: " + size + " bytes. Start downloading")
				while (cnt > 0L) {
					progressMeter.tick(cnt)
					pos += cnt
					cnt = chan.transferFrom(in, pos, batchSize)
				}
				progressMeter.info("Done")
			} catch {
				case e: Exception => 
					progressMeter.fail(e.getMessage())
			} finally {
				if (in != null) in.close()
				if (out != null) out.close()
				cn.disconnect()
			}
		}
	}

}
