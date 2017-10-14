package io.github.sclearn.stat

import io.github.sclearn.Dataset
import io.github.sclearn.dataset.impl.VectorDataset

trait Generator {
  type Continous1DDistribution = VectorDataset[Double]

  def uniform(min: Double = 0.0, max: Double = 1.0)(size: Int): Continous1DDistribution
  def normal(mu: Double = 0.0, sigma: Double = 1.0)(size: Int): Continous1DDistribution
  def gaussian(mu: Double = 0.0, sigma: Double = 1.0)(size: Int): Continous1DDistribution = normal(mu, sigma)(size)

  def cauchy(x0: Double = 0.0, gamma: Double = 1.0)(size: Int): Continous1DDistribution
  def exponential(min: Double = 0.0, lambda: Double = 1.0)(size: Int): Continous1DDistribution

  def uniform(size: Int): Continous1DDistribution = uniform()(size)
  def normal(size: Int): Continous1DDistribution = normal()(size)
  def gaussian(size: Int): Continous1DDistribution = gaussian()(size)
}
