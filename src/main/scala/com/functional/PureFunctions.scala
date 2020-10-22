package com.functional

case class Person(name: String)

// Pure Functions + Immutable Values = Algebra
//  - A Pure Function is said to evaluate to a result rather than return a result
object PureFunctions extends App {

  // Pure functions:
  //  - always evaluate to the same result given the same inputs
  //  - cannot depend on hidden state or value.
  //  - cannot depend on I/O.
  //  - Eval of the result does not cause observable side effects like mutation or I/O output.
  // OUTPUT DEPENDS ONLY ON INPUT (generally)

  // Two general telltales that a function is impure (does not always apply):
  //  - No input parameters
  //  - No return type (Returns Unit in Scala)

  // Free Variable
  //  - A variable used in a function that was not passed as a parameter or defined in the function body

  // This Pure Function takes an input as a String and returns an output as an Int
  def foo(s: String): Int = s.length

  // Pure functions can also make use of classes
  def foo(people: Seq[Person], n: Int): Person = people(n)
  val p1 = new Person("Bob")
  val p2 = new Person("Eleven")
  val ans = foo(Seq(p1, p2), 1)
  println(ans) // Person(Eleven)

  // This function returns Unit, it most likely has a side effect
  def foo(person: Person): Unit = ???
}
