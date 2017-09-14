package io.github.sclearn
package sklearn.linear_model

import pandas.DataFrame


class LinearRegression(
			fit_intercept: Boolean,
			normalize: Boolean,
			copy_X: Boolean,
			n_jobs: Int
		) {

	def coef_ : Array[Double] = ???

	def fit(X: DataFrame, y: DataFrame): LinearRegression = ???
	def score(X: DataFrame, y: DataFrame) = ???
	def predict(X: DataFrame): DataFrame = ???

	def get_params(deep: Boolean = false): Map[String, Any] = ???
	def set_params(params: (String, Any)*): Unit = ???
	def set_params(params: Map[String, Any]): Unit = ???

	// TODO: sample_weights parameter
}

object LinearRegression {

	def apply(
		fit_intercept: Boolean = true,
		normalize: Boolean = false,
		copy_X: Boolean = true,
		n_jobs: Int = 1
	) = new LinearRegression(fit_intercept, normalize, copy_X, n_jobs)

}
