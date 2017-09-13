package io.github.sclearn.model
package linear_model

import org.scalatest.junit.AssertionsForJUnit
import scala.collection.mutable.ListBuffer
import org.junit.Assert._
import org.junit.Test
import org.junit.Before


class LinearRegressionTest extends AssertionsForJUnit {
  
  // FIXME: implement all
  @Test
  def testLinearRegression = {
    val train_X  = null
    val train_y  = null
    val test_X  = null
    val test_y  = null
    
    val regr = linear_model.LinearRegression()
    regr.fit(train_X, train_y)
    val predicted_y = regr.predict(test_X)
    println(regr.coef_)
    
  }
}