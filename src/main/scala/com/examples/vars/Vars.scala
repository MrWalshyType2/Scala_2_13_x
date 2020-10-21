package com.examples.vars

object Vars extends App {

  // type is inferred
  val myNum = 1
  val myString = "Hello"
  var mutable = 30

  // type is explicitly stated
  val x: Int = 32
  val s: String = "World"
  var b: Boolean = false

  println(x + mutable)
  println(myString + " " + s)
}
