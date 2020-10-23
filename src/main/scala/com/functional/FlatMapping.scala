package com.functional

import java.lang.Thread.sleep

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object FlatMapping extends App {

  // The standard map method transforms an iterable of type A to type B

  // flatMap does a map, then flattens the result
  println(List("Foo", "Bar").flatMap(s => s.split("")))

  val res: List[Array[String]] = List("Foo", "Bar").map(s => s.split(""))
  println(res) // List(Array(...), Array(...))
  println(res.flatten)

  // Options and flatMap pair together nicely
  //  - Option wrappers tend to coalesce (join together)
  def makeInt(s: String): Option[Int] = {
    try Some(s.trim.toInt)
    catch {
      case e: Exception => None
    }
  }

  // Cannot be directly added together as the results are wrapped in an Option
  val x = makeInt("3")
  val y = makeInt("4")

  val sum = x.getOrElse(0) + y.getOrElse(0) // Works, but there is a more powerful approach using flatMap and map

  // Using map, an Option[Option[Int]] is returned.
  //  - Using a flatMap could achieve the result wrapped in only one Option
  val result = x map { a =>
    y map { b =>
      a + b
    }
  }
  println(result) // Some(Some(7))

  // Using flatMap
  val flatResult = x flatMap { a =>
    y map { b =>
      a + b
  }}
  println(flatResult) // Some(7)

  // Option supports map & flatMap, thus it can be used in a for expression
  val newSum = for {
    x <- makeInt("2")
    y <- makeInt("8")
  } yield x + y
  println(newSum) // Some(10)

  // Using the forExpression is safer than getOrElse
  //  - If the for expression meets an erroneous value, it will return a None type for Option
  //  - Other Error handling types have a map & flatMap method

  // Scala's Future class has map & flatMap methods
  val f1 = Future { sleep(4*1000); 1}
  val f2 = Future { sleep(2*1000); 2}

  val futureResult = for {
    r1 <- f1
    r2 <- f2
  } yield (r1 + r2)

  futureResult.onComplete {
    case Success(value) => println(s"RESULT: $value")
    case Failure(exception) => println(s"ERROR: $exception")
  }
  sleep(5*1000) // allows futureResult to evaluate before exiting

  // Scala monads are classes implementing the map & flatMap methods.
  //  - there is no base Monad trait to extend unlike Haskell
  //  - monads can be used as a generator in for expressions
}
