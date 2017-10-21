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
    val longVector = LocalVectorDataset(Array(1L, 2L, 3L))
    assertEquals(longVector.length, 3)
    val doubleVector = LocalVectorDataset(Array(1.0, 2.0, 3.0, 4.0))
    assertEquals(doubleVector.length, 4.0)
  }

  @Test
  def testPlusScalar {
    val vector = LocalVectorDataset(Array(1L, 2L, 3L))
    val vectorPlusOne = LocalVectorDataset(Array(2L, 3L, 4L))

    assertEquals(vector + 1, vectorPlusOne)
  }
}