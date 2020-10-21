package com.examples.control_structures

object TryCatch extends App {

  val s = "hello"

  try {
    var x = 22
    var y = 22/0
  } catch {
      // Pattern matching
      case ae: ArithmeticException => println(ae)
      case e: Exception => println(e)
  }
}
