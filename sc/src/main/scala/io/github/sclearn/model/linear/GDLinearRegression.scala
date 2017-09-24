package io.github.sclearn.model.linear

import io.github.sclearn.Dataset

class GDLinearRegression[T] extends LinearRegression[T] {
  def fit(x: Dataset[T], y: Dataset[T]): Unit = ???
}