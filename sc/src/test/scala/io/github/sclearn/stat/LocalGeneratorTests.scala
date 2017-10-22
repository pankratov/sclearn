package io.github.sclearn.stat

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._
import org.junit.runner.RunWith
import org.junit.platform.runner.JUnitPlatform
import io.github.sclearn.model.linear.LinearRegression

@RunWith(classOf[JUnitPlatform])
class LocalGeneratorTests {

  import LocalGenerator._

  @Test
  def testSomething() {
    val k = 13
    val b = 56

    val X = uniform(0, 5)(1000)
    val noise = normal(1000)
    val y = X * k + b + noise
    val regr = LinearRegression()
    val trainX = X.addOnes
    regr.fit(trainX, y)
    println(regr.coef)

    val predicted_y = regr.predict(trainX)
    println(y)
    println(predicted_y)

    assertEquals(true, true)
  } 
  
}