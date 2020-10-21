package com.examples.control_structures

object Fors extends App {

  for (i <- 0 to 10 by 2) println(i)

  val x = for (i <- 1 to 5) yield i * 2;
  println(x) // Vector(2, 4, 6, 8, 10)

  val fruits = List("apple", "banana", "lime")

  val fruitLengths = for {
    f <- fruits
    if f.length > 4
  } yield f.length
  println(fruitLengths) // List(5, 6)   "lime" is not more than 4 chars long, thus is not yielded
}
