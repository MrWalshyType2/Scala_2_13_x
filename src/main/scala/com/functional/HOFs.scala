package com.functional

object HOFs extends App {
  // A HOF (Higher Order Function) can take a function as a parameter

  // callback is a function that takes no parameters and returns nothing (Unit)
  def sayHello(callback: () => Unit): Unit = callback()
  def sayHello2(callback: (String) => Unit): Unit = callback("Fred")

  // A function that matches callbacks signature in sayHello
  def hi(): Unit = println("Hello")
  def hi(s: String): Unit = println(s"Hello $s")

  sayHello(hi)
  sayHello2(hi)

  // Function param with other params
  def executeNTimes(f: () => Unit, n: Int): Unit = {
    for (i <- 1 to n) f()
  }
  executeNTimes(hi, 10)

  // Takes a func which takes a param of type A, returns B
  // Takes a sequence of type A
  // Returns a sequence of type B
  def map[A, B](f: (A) => B, list: Seq[A]): Seq[B] = {
    for {
      x <- list
    } yield f(x)
  }

  // Takes a func which takes a param of type A, returns a Boolean
  // Takes a sequence of type A
  // Returns a sequence of A where the predicate function evaluates to true
  def filter[A](f: (A) => Boolean, list: Seq[A]): Seq[A] = {
    for {
      x <- list if f(x) // passes x to yield if f(x) is true
    } yield x
  }

  def toDouble(num: Int): Double = num.toDouble
  def isEven(num: Int): Boolean = if (num % 2 == 0) true else false

  println(map(toDouble, List(1, 2, 3, 4, 5)))
  println(filter(isEven, List(1, 2, 3, 4, 5)))
}
