package com.functional

object ValFuncs extends App {

  // val is used for immutable variables (or values)

  // Function literals look like string literals
  //  - can be assigned to a val field
  //  - has a name
  //  - the function variables value is the code in the function body
  //  - has a type
  //  - can be passed around
  //  - can be stored in a collection

  // implicit return type of Boolean
  val isEven = (i: Int) => i % 2 == 0
  // explicit return type (takes an Int, returns a Boolean)
  val isEven2: Int => Boolean = (i) => i % 2 == 0

  // Functions can be passed around like variables
  val ints = List(1, 2, 3, 4)
  // Filter expects a predicate, something that evals to true or false. isEven does this and matches the type of the sequences elements
  println(ints.filter(isEven)) // isEven is passed to the filter method which is applied to the ints list

  // Functions can be stored in a Map as they are a variable
  val double = (i: Int) => i * 2
  val triple = (i: Int) => i * 3
  // Store the functions as values
  val multiplyFunctions = Map(
    "2x" -> double,
    "3x" -> triple
  )

  // get the reference to a func stored in a map
  val doubler = multiplyFunctions("2x")
  println(doubler(3))
}
