package com.examples.classes

class Person(val forename: String, val surname: String) {
  def printName(): Unit = println(s"$forename $surname")
}

object Run extends App {
  val p: Person = new Person("Fred", "Grok")
  p.printName()
  println(p.forename)
}
