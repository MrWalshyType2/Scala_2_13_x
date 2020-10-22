package com.functional

object MethodsAsFuncs extends App {

  // Methods can also be passed around like Functions
  def doubleMethod(i: Int) = i * 2
  List(1, 2, 3).map(doubleMethod).foreach(i => print(s"$i "))

  // val functions inherit from FunctionX traits
  val isEven = (i: Int) => i % 2 == 0 // Function1[Int, Int]

  // Methods must belong to a class, object, or trait
  def isEven2(i: Int) = i % 2 == 0 // isEven: (i: Int)Boolean

  // Methods can be passed into a function, they are converted into functions during compilation (Eta Expansion)
  // Partially applied functions can be used to create a function from a method
  val isEven3 = isEven2 _ // isEven3: Int => Boolean
  println(List(2, 3, 4).map(isEven3))

  // Methods cannot be directly stored in a Map, instead use the partially applied function syntax (f _)
  val funcs = Map(
    "2x" -> isEven2 _
  )
}
