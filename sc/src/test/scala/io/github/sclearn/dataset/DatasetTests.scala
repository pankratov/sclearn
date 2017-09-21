package io.github.sclearn.dataset

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._
import io.github.sclearn.Dataset

class DatasetTests {

  class Matrix[T <: AnyVal](rows: List[MatrixRow[T]]) {
    def ~ (nextRow: MatrixRow[T]) = new Matrix(rows :+ nextRow)
    def toDs = ???
    def ~ () = this
    override def toString = rows.mkString("\n")
  }
  object Matrix {
    def apply[T <: AnyVal](row: MatrixRow[T]) = new Matrix(List(row))
    def apply[T <: AnyVal](rows: List[MatrixRow[T]]) = new Matrix(rows)
  }
 
  class MatrixRow[T <: AnyVal](elements: List[T]) {
    def   x (nextElement: T) = new MatrixRow(elements :+ nextElement)
    override def toString = elements.mkString(" ")
  }
  object MatrixRow {
    def apply[T <: AnyVal](el: T) = new MatrixRow(List(el))
    def apply[T <: AnyVal](elements: List[T]) = new MatrixRow(elements)
  }
  implicit def val2Row[T <: AnyVal](value: T) = MatrixRow(value)
  implicit def row2Matrix[T <: AnyVal](value: MatrixRow[T]) = Matrix(value)
  
  @Test
  def testSomething {
   val matrix =  { 1 x 2 x 3 } ~
                 { 4 x 5 x 6 } ~
                 { 7 x 8 x 9 } 
             
   println(matrix)
//    val matrix = {{1 : 2 : 3} 
//                  {4 : 5 : 6}
//                  {7 : 8 : 9}}
//    println(matrix)
    assertEquals(1, 1)  
  }

  
}