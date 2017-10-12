package io.github.sclearn.dataset.impl

import io.github.sclearn.dataset.spark.sql.types.StructType
import io.github.sclearn.Dataset

import breeze.linalg.DenseVector

class LocalVectorDataset(val vector: DenseVector[Double]) extends VectorDataset[Double] {
 
  def collect(): Array[Double] = {
    ???
  }

  def schema: StructType = {
    ???
  }
 
  override def toString = vector.toString
  override def + (other: Dataset[Double]): Dataset[Double] = other match {
    case ot:LocalVectorDataset => 
      new LocalVectorDataset(vector + ot.vector) 
    case _ => ???
  }

  override def * (scalar: Double): Dataset[Double] = {
    new LocalVectorDataset(vector.map(_ * scalar)) 
  }
}

object LocalVectorDataset {
  def apply[T](values: Array[Double]): LocalVectorDataset = {
    new LocalVectorDataset(new DenseVector( values ))
  }
}