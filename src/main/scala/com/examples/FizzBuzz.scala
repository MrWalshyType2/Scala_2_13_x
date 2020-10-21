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

  def fizzBuzz(input: Integer): String = {
    if (input == 0) input.toString
    else if ((input % 3) == 0 && (input % 5) == 0) "fizzbuzz"
    else if ((input % 5) == 0) "buzz"
    else if ((input % 3) == 0) "fizz"
    else input.toString
  }
}
