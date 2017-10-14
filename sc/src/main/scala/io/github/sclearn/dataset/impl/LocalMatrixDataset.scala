package io.github.sclearn.dataset.impl

import breeze.linalg.DenseMatrix
import breeze.linalg.{ pinv => breeze_pinv }
import io.github.sclearn.dataset.spark.sql.types.StructType

class LocalMatrixDataset(val matrix: DenseMatrix[Double]) extends MatrixDataset[Double] {
  def *(scalar: Double): LocalMatrixDataset = {
    new LocalMatrixDataset(matrix.map(_ * scalar))
  }

  def v: LocalVectorDataset = new LocalVectorDataset(matrix.toDenseVector)

  def *(other: LocalMatrixDataset): LocalMatrixDataset = {
    new LocalMatrixDataset(matrix * other.matrix)
  }

  def *(other: LocalVectorDataset): LocalMatrixDataset = {
    new LocalMatrixDataset(matrix * other.vector.toDenseMatrix)
  }

  def pinv(): LocalMatrixDataset = {
    new LocalMatrixDataset(breeze_pinv(matrix))
  }

  def t(): LocalMatrixDataset = {
    new LocalMatrixDataset(matrix.t)
  }
  
  override def toString = matrix.toString

  def collect(): Array[Double] = {
    ???
  }

  def schema: StructType = {
    ???
  }
}