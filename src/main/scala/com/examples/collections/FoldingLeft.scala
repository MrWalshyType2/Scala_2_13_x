package com.examples.collections

object FoldingLeft extends App {

  val nums = List(10, 20, 30, 40, 50)
  println(nums.foldLeft(0)(_ + _))
}
