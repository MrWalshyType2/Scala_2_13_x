package com.examples

//*Fizz Buzz Exercise*
//
//Write a function that takes an *Integer* as input and returns a *String* as the output, following these rules:
//- If the input is divisible by 3, return “fizz”
//- If the input is divisible by 5, return “buzz”
//- If the input is divisible by both 3 and 5, return “fizzbuzz”
//- Otherwise, return the input as a string
object FizzBuzz extends App {

  val ans = fizzBuzz(3)
  val ans2 = fizzBuzz(5)
  val ans3 = fizzBuzz(15)
  val ans4 = fizzBuzz(27)
  val ans5 = fizzBuzz(120)
  val ans6 = fizzBuzz(37)

  val ans7 = fizzBuzz(-3)
  val ans8 = fizzBuzz(0)

  println(ans)
  println(ans2)
  println(ans3)
  println(ans4)
  println(ans5)
  println(ans6)
  println(ans7)
  println(ans8)
  println()

  val ans9 = fizzBuzzAlt(-3)
  val ans10 = fizzBuzzAlt(120)
  val ans11 = fizzBuzzAlt(5)
  val ans12 = fizzBuzzAlt(11)
  val ans13 = fizzBuzzAlt(0)

  println(ans9)
  println(ans10)
  println(ans11)
  println(ans12)
  println(ans13)
  println()

  val ans14 = fizzBuzzAlt2(Option(-3))
  val ans15 = fizzBuzzAlt2(Option(120))
  val ans16 = fizzBuzzAlt2(Option(5))
  val ans17 = fizzBuzzAlt2(Option(11))
  val ans18 = fizzBuzzAlt2(Option(0))
  val ans19 = fizzBuzzAlt2(None)

  println(ans14)
  println(ans15)
  println(ans16)
  println(ans17)
  println(ans18)
  println(ans19)

  def fizzBuzz(input: Integer): String = {
    if (input == 0) input.toString
    else if ((input % 3) == 0 && (input % 5) == 0) "fizzbuzz"
    else if ((input % 5) == 0) "buzz"
    else if ((input % 3) == 0) "fizz"
    else input.toString
  }

  def fizzBuzzAlt(input: Integer): String = {
    if (input == 0) input.toString
    // Creating a tuple with the results of the ops, then using tuples with expected results to decide the return val
    else (input % 3, input % 5) match {
      case (0, 0) => "fizzbuzz"
      case (_, 0) => "buzz"
      case (0, _) => "fizz"
      case _ => input.toString
    }
  }

  def fizzBuzzAlt2(option: Option[Int]): String = {
    option match {
      case Some(i) => {
        fizzBuzzAlt(i)
      }
      case None => ""
    }
  }
}
