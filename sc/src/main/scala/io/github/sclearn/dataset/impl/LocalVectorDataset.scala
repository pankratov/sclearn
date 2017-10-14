package io.github.sclearn.dataset.impl

import io.github.sclearn.dataset.spark.sql.types.StructType
import io.github.sclearn.Dataset

import breeze.linalg.DenseVector
import breeze.linalg.DenseMatrix

class LocalVectorDataset(val vector: DenseVector[Double]) extends VectorDataset[Double] {
 
  def collect(): Array[Double] = {
    ???
  }

  def schema: StructType = {
    ???
  }

  def addOnes = new LocalMatrixDataset(DenseMatrix.horzcat(DenseVector.ones[Double](vector.length).toDenseMatrix.t, vector.toDenseMatrix.t)) 

  def m: MatrixDataset[Double] = new LocalMatrixDataset(vector.toDenseMatrix.t) 

  override def toString = vector.toString
  def + (other: Dataset[Double]): Dataset[Double] = other match {
    case ot:LocalVectorDataset => 
      new LocalVectorDataset(vector + ot.vector) 
    case _ => ???
  }

  def + (scalar: Double): LocalVectorDataset = {
    new LocalVectorDataset(vector.map(_ + scalar)) 
  }
  
  def * (scalar: Double): LocalVectorDataset = {
    new LocalVectorDataset(vector.map(_ * scalar)) 
  }

  def * (other: LocalMatrixDataset): LocalMatrixDataset = {
    new LocalMatrixDataset(vector.toDenseMatrix * other.matrix) 
  }

  def t(): LocalMatrixDataset = {
    new LocalMatrixDataset(vector.toDenseMatrix.t)
  }
}

object LocalVectorDataset {
  def apply[T](values: Array[Double]): LocalVectorDataset = {
    new LocalVectorDataset(new DenseVector( values ))
  }
}