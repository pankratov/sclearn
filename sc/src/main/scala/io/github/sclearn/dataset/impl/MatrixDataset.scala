package io.github.sclearn.dataset.impl

import io.github.sclearn.Dataset

trait MatrixDataset[T] extends Dataset[T] {
  def v: VectorDataset[T]  
}