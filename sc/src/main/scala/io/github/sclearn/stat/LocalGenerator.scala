package io.github.sclearn.stat

import breeze.stats.distributions._
import io.github.sclearn.dataset.impl.LocalVectorDataset


object LocalGenerator extends Generator {
  def cauchy(x0: Double, gamma: Double)(size: Int): Continous1DDistribution = {
    val cauchy = new CauchyDistribution(x0, gamma)
    LocalVectorDataset(cauchy.sample(size).toArray)
  }

  def exponential(min: Double, lambda: Double)(size: Int): Continous1DDistribution = {
    val exponential = Exponential(lambda)
    LocalVectorDataset(exponential.sample(size).map(_ + min).toArray)
  }

  def normal(mu: Double, sigma: Double)(size: Int): Continous1DDistribution = {
    val gaussian = Gaussian(mu, sigma)
    LocalVectorDataset(gaussian.sample(size).toArray)
  }

  def uniform(min: Double, max: Double)(size: Int): Continous1DDistribution = {
    val uniform = Uniform(min, max)
    LocalVectorDataset(uniform.sample(size).toArray)
  }

}