package io.github.sclearn.dataset.impl

import io.github.sclearn.Dataset

trait VectorDataset[T] extends Dataset[T] {
  def m: MatrixDataset[T]
  def addOnes: MatrixDataset[T]
  
  def + (other: Dataset[T]): Dataset[T]
  def + (scalar: T): VectorDataset[T]
  def * (scalar: T): VectorDataset[T]
  def length: Int
}