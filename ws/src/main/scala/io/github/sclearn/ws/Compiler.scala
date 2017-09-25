package io.github.sclearn.ws

import tools.reflect.ToolBox
import tools.reflect.ToolBoxError

package sandbox {

	class A;
	class B;
	class C;
	class D;

}

object Compiler {

	import reflect.runtime.currentMirror
	private val tbox = currentMirror.mkToolBox()

	def compile(code: String): Either[String, Any] = {
		try {
			Right(tbox.compile(tbox.parse("import io.github.sclearn.ws.sandbox._;" + code))())
		} catch {
			case e: ToolBoxError => Left(e.getMessage)
		}
	}

}
