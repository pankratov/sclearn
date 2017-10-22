package io.github.sclearn.dataset.impl

import breeze.linalg.DenseMatrix
import breeze.linalg.{ pinv => breeze_pinv }
import io.github.sclearn.dataset.spark.sql.types.StructType
import breeze.math.Field
import scala.reflect._
import scala.reflect.runtime.universe._

class LocalMatrixDataset[T: ClassTag](val matrix: DenseMatrix[T])(implicit N: Field[T]) extends MatrixDataset[T] {
  def *(scalar: T): LocalMatrixDataset[T] = {
    new LocalMatrixDataset(matrix.map(N.*(_, scalar)))
  }

  def v: LocalVectorDataset[T] = new LocalVectorDataset(matrix.toDenseVector)

  def *(other: MatrixDataset[T]): LocalMatrixDataset[T] = {
    other match {
      case LocalMatrixDataset(otherMatrix) => new LocalMatrixDataset(matrix * otherMatrix)
      case _ => ???
    }
  }

  def *(other: LocalVectorDataset[T]): LocalMatrixDataset[T] = {
    new LocalMatrixDataset(matrix * other.vector.toDenseMatrix)
  }

  // TODO: more types, refactor
  def pinv(): LocalMatrixDataset[T] = {
    if (classTag[T].runtimeClass.isAssignableFrom(classOf[Double])) {
      new LocalMatrixDataset(breeze_pinv(matrix.asInstanceOf[DenseMatrix[Double]]))
        .asInstanceOf[LocalMatrixDataset[T]]
    } else if (classTag[T].runtimeClass.isAssignableFrom(classOf[Float])) {
      new LocalMatrixDataset(breeze_pinv(matrix.asInstanceOf[DenseMatrix[Float]]))
        .asInstanceOf[LocalMatrixDataset[T]]
    } else {
      ???
    }
  }

  def t: LocalMatrixDataset[T] = {
    new LocalMatrixDataset(matrix.t)
  }

  override def toString = matrix.toString

}

object LocalMatrixDataset {
  def apply[T: ClassTag](values: Array[Array[T]])(implicit N: Field[T]): LocalMatrixDataset[T] = {
    val rows = values.length
    val cols = if (rows > 0) values(0).length else 0
    new LocalMatrixDataset(new DenseMatrix(rows, cols, values.flatten))
  }
  def unapply[T](localMatrix: LocalMatrixDataset[T]): Option[DenseMatrix[T]] = Option(localMatrix.matrix)
}