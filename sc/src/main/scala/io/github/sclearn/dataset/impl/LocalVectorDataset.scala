package io.github.sclearn.dataset.impl

import io.github.sclearn.dataset.spark.sql.types.StructType
import io.github.sclearn.Dataset

import breeze.linalg.DenseVector
import breeze.linalg.DenseMatrix
import scala.reflect.ClassTag

class LocalVectorDataset[T: ClassTag](val vector: DenseVector[T])(implicit N: Numeric[T]) extends VectorDataset[T] {

  def length = vector.length

  def addOnes = ???
  //  {
  //
  //
  //    new LocalMatrixDataset(DenseMatrix.horzcat(DenseVector.ones[Double](vector.length).toDenseMatrix.t, vector.toDenseMatrix.t))
  //  }

  def m: MatrixDataset[T] = ???
  // new LocalMatrixDataset(vector.toDenseMatrix.t)

  override def toString = vector.toString
  def +(other: Dataset[T]): Dataset[T] = {
    // new LocalVectorDataset(vector + ot.vector)
    ???
  }

  def +(scalar: T): LocalVectorDataset[T] = {
    new LocalVectorDataset(vector.map(N.plus(_, scalar)))
  }

  def *(scalar: T): LocalVectorDataset[T] = {
    /// new LocalVectorDataset(vector.map(_ * scalar))
    ???
  }

  def *(other: LocalMatrixDataset[T]): LocalMatrixDataset[T] = {
    // new LocalMatrixDataset(vector.toDenseMatrix * other.matrix)
    ???
  }

  def t: LocalMatrixDataset[T] = {
    /// new LocalMatrixDataset(vector.toDenseMatrix.t)
    ???
  }

  override def hashCode = vector.hashCode()

  override def equals(other: Any) = {
    other match {
      case LocalVectorDataset(otherVector) => vector.equals(otherVector)
      case _ => false
    }
  }
}

object LocalVectorDataset {
  def apply[T: ClassTag](values: Array[T])(implicit N: Numeric[T]): LocalVectorDataset[T] = {
    new LocalVectorDataset(new DenseVector(values))
  }
  def unapply[T](localVector: LocalVectorDataset[T]): Option[DenseVector[T]] = Option(localVector.vector)
}