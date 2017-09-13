package io.github.sclearn.model.linear_model

import io.github.sclearn.Dataset
import io.github.sclearn.dataset.Row

class LinearRegression(
        fit_intercept: Boolean,
        normalize: Boolean,
        copy_X: Boolean,
        n_jobs: Int
      ) {
  def coef_ : Dataset[Row] = ???
  // TODO: sample_weights parameter
  def fit(X: Dataset[Row], y: Dataset[Row]): LinearRegression = ???
  def get_params(deep: Boolean = false): Map[String, Any] = ???
  def predict(X: Dataset[Row]): Dataset[Row] = ???
  // TODO: sample_weights parameter
  def score(X: Dataset[Row], y: Dataset[Row]) = ???
  def set_params(params: (String, Any)*): Unit = ???
  def set_params(params: Map[String, Any]): Unit = ???

}

object LinearRegression {
  def apply(
        fit_intercept: Boolean = true,
        normalize: Boolean = false,
        copy_X: Boolean = true,
        n_jobs: Int = 1
      ) = new LinearRegression(fit_intercept, normalize, copy_X, n_jobs)
}
