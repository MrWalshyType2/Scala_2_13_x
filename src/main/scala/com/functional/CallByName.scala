package com.functional

case class Person2(var name: String)

object CallByName extends App {

  // A 'call by-value' function argument is when an object is passed to a function,
  // the function receives a pointer to the passed-in objects memory location.
  //  - there is no local copy of the object
  //  - most function parameters are call by-value unless explicitly state

  // The person 'p' is call by-value
  def changeName(p: Person2) = p.name = "Bob"

  val p1 = Person2("Fred")
  changeName(p1)
  println(p1) // The value of name for obj p1 has been mutated

  // A function that takes a 'block' of code as a parameter is referred to as a 'call by-name' parameter
  //  - the code is only evaluated when it is referenced inside the function
  //  - use the by-name syntax which leaves off the () after the input parameter

  // Accepts code that returns a type of A
  def timer[A](blockOfCode: => A) = {
    val startTime = System.nanoTime()
    val result = blockOfCode // blockOfCode can return Unit or any other type once executed
    val stopTime = System.nanoTime()
    val delta = stopTime - startTime
    (result, delta/1000000d)
  }

  val (result, time) = timer(println("Hello"))
  println(s"println('Hello') resulted in $result and took $time seconds to run")

  val (result2, time2) = timer {
    println("Hello")
    32
  }
  println(s"println('Hello') & 32 resulted in $result2 and took $time2 seconds to run")
}
