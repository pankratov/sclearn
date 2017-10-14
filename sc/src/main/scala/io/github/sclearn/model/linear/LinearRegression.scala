package io.github.sclearn
package model
package linear

trait LinearRegression {
  // TODO
  // def fit[T](x: Dataset[T], y: Dataset[T]): Unit = ???
  // def predict[T](x: Dataset[T]): Dataset[T] = ???
}

object LinearRegression {
  def apply() = new NormEqLinearRegression()
  def apply(solver: String) = solver match {
    case "nomral" => new NormEqLinearRegression()
    case "sgd" => new SGDLinearRegression()
    case "gd" => new GDLinearRegression()
    // TODO: adequate behavior
    case _ => throw new Exception(s"Solver $solver is not supported. "
      + "You can choose one of 'normal', 'gd' or 'sgd' solvers")
  }
}
