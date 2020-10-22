package com.functional

object MultiParamGroups extends App {

  // Functions can have multiple input parameter groups
  //  - this allows the writing of your own control structures
  //  - allows for implicit and explicit parameters
  //  - parameters can be used from pior groups as a default value
  def foo(a: Int, b: String)(c: Double): Unit = ???

  def sum(a: Int)(b: Int) = a + b
  println(sum(1)(4)) // 5

  // A loop can be created similar to the while loop that uses two parameter groups
  //  - using by-name params
  def whilst(predicate: => Boolean)(f: => Unit): Unit = {
    while (predicate) f
  }

  var i = 0
  whilst (i < 10) {
    print(s"$i ")
    i += 1
  }

  // p & p2 are predicates, f is a block of code to be ran if both predicates are true
  def ifBothTrue(p: => Boolean)(p2: => Boolean)(f: => Unit) = if (p && p2) f

  ifBothTrue(0 == 0)(1 == 1) {
    println("hello world")
  }


  // Using implicits with multi-parameter groups
  //  - good for referring to shared resources multiple times
  //  - can only have one implicit parameter list, must be the last given
  //  - if several valid implicits exist in scope, the rules of static overloading resolution are used to make a choice
  def printIntIfTrue(a: Int)(implicit b: Boolean): Unit = if (b) println(a)
  // can be called like a normal mpg func
  printIntIfTrue(32)(false)

  // Declaring an implicit Boolean value will allow use of the function without explicitly stating the Boolean
  implicit val bool = true
  printIntIfTrue(20) // The Scala compiler knows an implicit Boolean value is in the current scope


  // Supplying default values for input parameters in mpgs
  //  - empty parenthesis have to be used to use the default values
  def myFunc(a: Int = 1)(b: Int = a): Int = { a + b }
  println(myFunc()()) // 2
  println(myFunc()(10)) // 11
}
