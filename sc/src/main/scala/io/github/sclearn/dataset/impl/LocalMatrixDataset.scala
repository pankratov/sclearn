package io.github.sclearn.dataset.impl

import breeze.linalg.DenseMatrix
import breeze.linalg.{ pinv => breeze_pinv }
import io.github.sclearn.dataset.spark.sql.types.StructType

class LocalMatrixDataset[T : Numeric](val matrix: DenseMatrix[T]) extends MatrixDataset[T] {
  def *(scalar: T): LocalMatrixDataset[T] = {
    /// new LocalMatrixDataset(matrix.map(_ * scalar))
    ???
  }

  def v: LocalVectorDataset[T] =  ??? 
  // new LocalVectorDataset(matrix.toDenseVector)

  def *(other: MatrixDataset[T]): LocalMatrixDataset[T] = {
    //  new LocalMatrixDataset(matrix * local.matrix)
    ???
  }

  def *(other: LocalVectorDataset[T]): LocalMatrixDataset[T] = {
    // new LocalMatrixDataset(matrix * other.vector.toDenseMatrix)
    ???
  }

  def pinv(): LocalMatrixDataset[T] = {
    // new LocalMatrixDataset(breeze_pinv(matrix))
    ???
  }

  def t: LocalMatrixDataset[T] = {
    // new LocalMatrixDataset(matrix.t)
    ???
  }
  
  override def toString = matrix.toString

}