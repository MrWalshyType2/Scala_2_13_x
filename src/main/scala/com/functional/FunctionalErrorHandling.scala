package com.functional

import org.scalactic.{Bad, ErrorMessage, Good, Or}

import scala.util.Try

object FunctionalErrorHandling extends App {

  // USECASES
  // Option - no need for error messages
  // Try    - good for wrapping exceptions
  // Or     - Alternative to Try for getting the 'failure reason'

  // Option, Some, None
  def makeInt(s: String): Option[Int] = {
    try Some(s.trim.toInt)
    catch {
      case e: Exception => None
    }
  }

  // Using pattern matching to handle a method returning an Optional
  var input = "3"
  makeInt(input) match {
    case Some(i) => println(s"i = $i")
    case None => println(s"Could not parse $input")
  }

  // Using getOrElse to handle a method returning an Optional
  val result = makeInt(input).getOrElse(0)

  // Using a for expression to handle a method returning an Optional
  val result2 = for {
    x <- makeInt("1")
    y <- makeInt("hi")
  } yield x + y
  println(result2.getOrElse(0))

  // Option does not tell why something has failed
  // Try & Either in the Scala API and Or in the Scalactic library can be used to state why something failed

  // Try, Success, Failure
  //  - works with match and for expressions as well
  def makeInt2(s: String): Try[Int] = Try(s.trim.toInt)
  println(makeInt2("32"))
  println(makeInt2("Hi"))

  // Either, Left, Right
  //  - Left holds an error
  //  - Right holds a success value
  def makeInt3(s: String): Either[String, Int] = {
    try Right(s.trim.toInt)
    catch {
      case e: Exception => Left(e.toString)
    }
  }
  println(makeInt3("42"))
  println(makeInt3("Whatcha"))

  makeInt3("11") match {
    case Left(s) => println(s"Error: $s")
    case Right(i) => println(s"Answer: $i")
  }

  // Or comes from the 3rd party Scalactic lib
  //  - returning an Int Or ErrorMessage
  def makeInt4(s: String): Int Or ErrorMessage = {
    try Good(s.trim.toInt)
    catch {
      case e: Exception => Bad(e.toString)
    }
  }
  println(makeInt4("32"))

  println(for {
    a <- makeInt4("hi")
    b <- makeInt4("22")
  } yield a + b)

  // Using the Null Object Pattern to handle errors
  def double(list: Seq[Int]): Seq[Int] = {
    if (list.length > 0) {
      list.map(_ * 2)
    } else {
      // Handling the else by returning an empty list rather than null
      Nil: List[Int]
    }
  }
  println(double(List(1, 3, 4)))
  println(double(List()))
}
