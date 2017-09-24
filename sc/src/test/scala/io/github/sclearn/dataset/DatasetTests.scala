package io.github.sclearn.dataset

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._
import io.github.sclearn.Dataset

class DatasetTests {
  implicit class MatrixHelper(val sc: StringContext) {
    def m(args: Any*): Array[Array[Double]] = {
      sc.parts.mkString
        .replaceAll("[\\[\\]]", "")
        .split(";")
        .map(_.split("\\s+")
          .filter(_.matches("\\d+(\\.\\d+)?"))
          .map(_.toDouble))
    }
  }

  @Test
  def testSomething {
    val x = 3
    val matrix = m"[1 2 3; 4 5 6]"

    assertEquals(1, 1)
  }

}