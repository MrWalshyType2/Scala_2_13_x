package com.functional

object BindingFunctions extends App {
  // Binding is the process of combining pure functions to make a complete application

  // If a function takes an Int as a parameter, another function that returns an Int can be bound as the parameter
  def f(a: Int): Int = a * 2
  def g(a: Int): Int = a * 2

  // Binding the functions to each other
  val x = g(f(100))
  println(x) // 400


  // Often, the outputs do not match another functions inputs
  //  - How can the output of i be plugged into the input of h?
  def h(a: Int): (Int, String) = (a * 2, a.toString)
  def i(a: Int): (Int, String) = (a * 2, a.toString)
  def j(a: Int): (Int, String) = (a * 2, a.toString)

  val hResult = h(100)
  val iResult = bind(i, hResult)
  val jResult = bind(j, iResult)
  println(s"Result of h, i, j: Int = ${jResult._1}, String = ${jResult._2}")

  // The bind function takes a function, and a tuple
  def bind(f: (Int) => (Int, String), t: (Int, String)): (Int, String) = {
    val (intResult, stringResult) = f(t._1) // f evals to (Int, String) tuple
    (intResult, stringResult) // result returned
  }
  val (iInt, iString) = bind(i, hResult)
}
