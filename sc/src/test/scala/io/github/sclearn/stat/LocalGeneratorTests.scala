package io.github.sclearn.stat

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._
import org.junit.runner.RunWith
import org.junit.platform.runner.JUnitPlatform



@RunWith(classOf[JUnitPlatform])
class LocalGeneratorTests {

  import LocalGenerator._

   @Test
   def testSomething() {
     val points = uniform(0, 5)(10)
     val noise = normal(10)
     println(points)
     val values = (points * 4) + noise
     println(values)
     assertEquals(true, true)
   }
} 