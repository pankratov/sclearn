package io.github.sclearn.dataset.impl

import io.github.sclearn.Dataset

trait MatrixDataset[T] extends Dataset[T] {
  def v: VectorDataset[T]
  def pinv(): MatrixDataset[T]
  def *(other: MatrixDataset[T]): MatrixDataset[T]
  def *(scalar: T): MatrixDataset[T]
  def t : MatrixDataset[T]
}