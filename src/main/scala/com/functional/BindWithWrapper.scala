package com.functional

// The Debuggable class is an implementation of a Writer monad in Haskell
//  - Writer Monad is for values attached to other values, such as the message List for Debuggable
//    - Computations happen, but the results are combined into one message value
case class Debuggable[A](value: A, message: List[String]) {

  def map[B](f: A => B): Debuggable[B] = Debuggable(f(this.value), this.message)

  def flatMap[B](f: A => Debuggable[B]): Debuggable[B] = {
    val nextVal = f(value)
    Debuggable(nextVal.value, this.message ::: nextVal.message) // ::: is the prefix operator
  }

  override def toString: String = s"VALUE: $value\n\nMESSAGE: \n${message.mkString("\n")}"
}

object BindWithWrapper extends App {
  // To use something in a for expression, the class or function used does not have to implement map and flatMap.
  //  - But its return type does need to
  def f(a: Int): Debuggable[Int] = {
    val result = a * 2
    val message = s"f: a ($a) * 2 = $result"
    Debuggable(result, List(message))
  }

  def g(a: Int): Debuggable[Int] = {
    val result = a * 3
    val message = s"g: a ($a) * 3 = $result"
    Debuggable(result, List(message))
  }

  def h(a: Int): Debuggable[Int] = {
    val result = a * 4
    val message = s"h: a ($a) * 4 = $result"
    Debuggable(result, List(message))
  }

  val finalResult = for {
    fResult <- f(1)
    gResult <- g(fResult)
    hResult <- h(gResult)
  } yield hResult
  println(finalResult)
}
