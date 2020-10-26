package com.functional

// Wrapper is wrote using the 'functional object' approach. Implicitly accessing fields from a function in a
// class is allowed.
object WrapperForForExpressions extends App {

  // To use a bind() func in a for expression, it requires a map and flatMap method
  //  - requires a Wrapper class for bind
  class Wrapper[A] private (value: A) {
    def map[B](f: A => B): Wrapper[B] = {
      // apply f to an Int
      val result = f(value) // value comes from the Wrapper's constructor
      new Wrapper(result)
    }

    // Because f evals to a Wrapper[Int], it can be directly used for the eval type of flatMap
    def flatMap[B](f: A => Wrapper[B]): Wrapper[B] = f(value)

    override def toString: String = s"Wrapper[${value.getClass.getSimpleName}](${value.toString})"
  }

  object Wrapper {
    def apply[A](value: A): Wrapper[A] = {
      new Wrapper[A](value)
    }
  }

  ////////////////////////////////////////////////////////
  val x = Wrapper(22)
  println(x.map(_ * 2))

  // map method allows use of one generator
  val result = for {
    i <- x
  } yield i * 2
  println(result)

  val result2 = for {
    i <- x
    y <- x
  } yield i * y // 44 * 44
  println(result2)

  val strResult: Wrapper[String] = for {
    // using the wrappers
    a <- Wrapper("Hello")
    b <- Wrapper("World")
  } yield s"$a $b"
  println(strResult)
}
