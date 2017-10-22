package io.github.sclearn.model.linear

import io.github.sclearn.Dataset
import io.github.sclearn.dataset.impl.LocalVectorDataset
import io.github.sclearn.dataset.impl.LocalMatrixDataset
import io.github.sclearn.dataset.impl.VectorDataset
import breeze.math.Field
import scala.reflect.ClassTag

class NormEqLinearRegression extends LinearRegression {

  var w: LocalVectorDataset[_] = null

  def coef = w
  /**
   * Calculate
   *   $$ w =  (X' \cdot X)^{-1} \cdot X' \cdot y $$
   */
  def fit[T](X: Dataset[T], y: Dataset[T]): Unit = {
    y match {
      case yvec: LocalVectorDataset[T] => X match {
        case xvec: LocalVectorDataset[T] =>
          w = ((xvec * xvec.t).pinv * xvec * yvec.t).v
        case xmat: LocalMatrixDataset[T] =>
          w = ((xmat.t * xmat).pinv * xmat.t * yvec.t).v
        case _ => throw new Exception("not emplemented")
      }
      case _ => throw new Exception("not emplemented")
    }
  }

  // TODO: check types
  def predict[T](X: Dataset[T]): VectorDataset[T] = {
     X match {
      case xvec: LocalVectorDataset[T] =>
        (xvec.t * w.t.asInstanceOf[LocalMatrixDataset[T]]).v
      case xmat: LocalMatrixDataset[T] =>
        (xmat * w.t.asInstanceOf[LocalMatrixDataset[T]]).v
      case _ => throw new Exception("not emplemented")
    }
  }
}