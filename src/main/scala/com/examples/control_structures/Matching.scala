package com.examples.control_structures

object Matching extends App {

  def getClassAsString(x: Any): String = x match {
    case s: String => s + " is a String"
    case i: Int => "Int"
    case _ => "Unknown"
  }

  println(getClassAsString("hello"))
  println(getClassAsString(32))
}
