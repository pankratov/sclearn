package io.github.sclearn.model.linear

import io.github.sclearn.Dataset
import io.github.sclearn.dataset.impl.LocalVectorDataset
import io.github.sclearn.dataset.impl.LocalMatrixDataset
import io.github.sclearn.dataset.impl.VectorDataset

class NormEqLinearRegression extends LinearRegression {
 
  var w: LocalVectorDataset = null

  def coef = w
  /**
   * Calculate
   *   $$ w =  (X' \cdot X)^{-1} \cdot X' \cdot y $$
   */
  def fit(X: Dataset[_], y: Dataset[_]): Unit = {
    y match {
      case yvec: LocalVectorDataset => X match {
        case xvec: LocalVectorDataset => 
          w = ((xvec * xvec.t).pinv * xvec * yvec.t).v
        case xmat: LocalMatrixDataset => 
          w = ((xmat.t * xmat).pinv * xmat.t * yvec.t).v
        case _ => throw new Exception("not emplemented")
      }
      case _ => throw new Exception("not emplemented")
    }
  }

  def predict(X: Dataset[_]): VectorDataset[Double] = {
     X match {
      case xvec: LocalVectorDataset => 
        (xvec.t * w.t).v
      case xmat: LocalMatrixDataset => 
        (xmat * w.t).v
      case _ => throw new Exception("not emplemented")
    }
  }
}