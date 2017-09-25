package io.github.sclearn.lang


import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach

import tools.reflect.ToolBox

package sandbox {

	class A;
	class B;
	class C;
	class D;

}

@Tag("exp")
class LangTest {

	@Test
	def test1() {
		import reflect.runtime.currentMirror
		val tbox = currentMirror.mkToolBox()
		val d = tbox.compile(tbox.parse("import io.github.sclearn.lang.sandbox.D; new D"))().asInstanceOf[sandbox.D]
		println(d)
	}

}
