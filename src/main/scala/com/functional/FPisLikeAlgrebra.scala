package com.functional

object FPisLikeAlgrebra extends App {

  def aF(): Int = 32

  def bF(a: Int): Int = 2 * a

  def cF(b: Int): Int = 2 * b

  // Functional programming is very much like algebra (evaluation & substitution)
  val a = aF() // a = aF()
  val b = bF(a) // b = bF(aF)
  val c = cF(b) // c = c(b(a))

  val b2 = bF(aF)
  val c2 = cF(bF(aF))

  println(b.equals(b2)) // true
  println(c2.equals(c)) // true

  // Functor - something that can be mapped

  // Data is not mutated in FP, simply copied and transformed during the copy process
  val p1 = new Person("Fred")
  val p2 = p1.copy(name = "Bob")
  println(s"Name: $p1, Name: $p2")
}
