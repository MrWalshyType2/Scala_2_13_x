package com.functional

import com.functional.LoanPattern.using

import scala.io.Source.fromFile
import scala.io.StdIn.readLine
import scala.util.{Failure, Success, Try}

class IO[A] private (constructorCodeBlock: => A) {

  def run: A = constructorCodeBlock

  def flatMapOrig[B](f: A => IO[B]): IO[B] = IO(f(run).run)

  def flatMap[B](customAlgorithm: A => IO[B]): IO[B] = {
    val result1: IO[B] = customAlgorithm(run)
    val result2: B = result1.run
    IO(result2)
  }

  def map[B](f: A => B): IO[B] = flatMap(a => IO(f(a)))

}

object IO {
  def apply[A](a: => A): IO[A] = new IO(a)
}

object IOMonad extends App {

  // IO Monads do not make I/O pure
  // BENEFITS:
  //  - I/O function signatures declare they return an IO type, this warns of side-effects
  //  - Allows I/O functions to be used in Scala for expressions
  //  - IO monads serve as a marker data type
  // WHAT IS IT?
  //  - A wrapper to wrap around I/O functions

  def getLine: IO[String] = IO(readLine())
  def putStrLine(s: String): IO[Unit] = IO(println(s))

  // A for expression can be assigned to a function for use with the IO monad
  //  - callable via the IO's run method
  def getName: IO[Unit] = for {
    _         <- putStrLine("What is your forename?") // _ ignores the return of a statement
    forename  <- getLine
    _         <- putStrLine("What is your surname?")
    surname   <- getLine
    _         <- putStrLine(s"Forename: $forename, Surname: $surname")
  } yield ()
//  getName.run

  // IO monads can be used with for expressions that implement recursion
  def loop: IO[Unit] = for {
    _         <- putStrLine("COMMAND: ")
    input     <- getLine
    _         <- putStrLine(s"INPUT: $input")
    _         <- if (input == "quit") IO() else loop // recursion
  } yield ()
//  loop.run

  // Using IO monads with I/O other than cli
  def readTextFileAsTry(filename: String): Try[List[String]] = {
    Try {
      val lines = using(fromFile(filename)) { source =>
        (for (line <- source.getLines) yield line).toList
      }
      lines
    }
  }
  val fileContents = readTextFileAsTry("test.txt")
  fileContents match {
    case Success(value) => value.foreach(println)
    case Failure(exception) => println(s"ERROR: $exception")
  }
}
