package io.github.sclearn.model.linear

import io.github.sclearn.Dataset

class NormEqLinearRegression[T] extends LinearRegression[T] {
 
  

  /**
   * Calculate
   *   $$ w =  (X' \cdot X)^{-1} \cdot X' \cdot y $$
   */
  def fit(x: Dataset[T], y: Dataset[T]): Unit = {
    // w = (x.tras * x).inv * x.trans * y
    ???
  }
 
}