package io.github.sclearn.dataset

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._
import io.github.sclearn.Dataset
import org.junit.runner.RunWith
import org.junit.platform.runner.JUnitPlatform
import breeze.linalg.DenseVector
import breeze.linalg.DenseMatrix

@RunWith(classOf[JUnitPlatform])
class DatasetTests {

  implicit class MatrixHelper(val sc: StringContext) {
    private def parsePart(str: String): List[Double] = {
      str.replaceAll("[\\[\\];]", " ")
        .trim().split("\\s+")
        .map(_.toDouble).toList
    }

    private def parseValues(args: Double*): List[Double] = {
      parsePart(sc.parts.head) ++ args.zip(sc.parts.tail).flatMap {
        case (arg, part) => arg :: parsePart(part)
      }
    }

    def m(args: Double*): DenseMatrix[Double] = {
      val values = parseValues(args: _*)

      val concatenated = sc.parts.mkString("0")
      val rowStr = concatenated.takeWhile(_ != ';')
      val colNum = rowStr.tail.trim().split("\\s+").length
      val rowNum = values.length / colNum

      new DenseMatrix(colNum, rowNum, values.toArray).t
    }

    def v(args: Double*): DenseVector[Double] = {
      val values = parseValues(args: _*)
      new DenseVector(values.toArray)
    }
  }

  @Test
  def testSomething {
    val y = 2
    val matrix = m"[1 $y; 3 4; 5 6]"
    val vector = v"[1 $y 3 4 5 6]"
    println(matrix)
    println(vector)
    
    assertEquals(1, 1)
  }

}
