package io.github.sclearn.dataset.impl

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._
import org.junit.runner.RunWith
import org.junit.platform.runner.JUnitPlatform
import io.github.sclearn.model.linear.LinearRegression

@RunWith(classOf[JUnitPlatform])
class LocalVectorDatasetTests {

  @Test
  def testInit {
    val longVector = LocalVectorDataset(1L, 2L, 3L)
    assertEquals(longVector.length, 3)
    val doubleVector = LocalVectorDataset(1.0, 2.0, 3.0, 4.0)
    assertEquals(4, doubleVector.length)
  }

  @Test
  def testPlusScalar {
    val vector = LocalVectorDataset(1L, 2L, 3L)
    val vectorPlusOne = LocalVectorDataset(2L, 3L, 4L)

    assertEquals(vectorPlusOne, vector + 1)
  }

  @Test
  def testMultiplyScalar {
    val vector = LocalVectorDataset(1L, 2L, 3L)
    val vectorPlusOne = LocalVectorDataset(2L, 4L, 6L)

    assertEquals(vectorPlusOne, vector * 2)
  }

  @Test
  def testVectorSum {
    val vector1 = LocalVectorDataset(1, 2, 3)
    val vector2 = LocalVectorDataset(5, 4, 3)
    val sumVector = LocalVectorDataset(6, 6, 6)
    
    assertEquals(sumVector, vector1 + vector2)
  }
}