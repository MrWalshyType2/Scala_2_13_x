package com.functional

object CurryingAndPAFs extends App {
  // PAF = Partially-Applied Function
  //  - Application: The process in which you apply a function to its arguments to produce a return a value.
  //  - Partial Application:
  //      - Process in which a function is applied only to some of its args
  //      - PAF gets returned for later use

  // plus()() has two parameter groups
  def plus(a: Int)(b: Int) = a + b

  // Instead of giving the function all of its parameters, it can be given one parameter and a placeholder (_)
  def plus2 = plus(2)(_)
  def plus2Alt = plus(2)_ // equivalent to line above
  println(plus2(20)) // 22

  // PAFs are useful for creating specialised methods from general methods
  def wrap(prefix: String)(html: String)(suffix: String) = prefix + html + suffix
  val hello = "Hello"
  val result = wrap("<div>")(hello)("</div>") // very verbose and repetitive

  // Partially applying parameters to a general function to create a more specific function
  //  - the type of the missing parameter must be specified
  def wrapWithDiv = wrap("<div>")(_: String)("</div>")
  val result2 = wrapWithDiv(hello) // Just the html arg has to be passed now

  // Currying
  //  - a function takes multiple arguments
  //  - the arguments can be translated to a series of function calls, each taking a single arg
  //    r = f(x)(y)(z) == f1 = f(x); f2 = f1(y); r = f2(z)

  // PAFs result in curried functions. A normal, one-param group function can be used to create a curried function
  def adding(x: Int, y: Int): Int = x + y
  val adder = adding _ // adder: (Int, Int) => Int
  // adder is a Function2 instance
  adder.isInstanceOf[(_, _) => _] // (_, _) => _ is short for Function2[_, _, _]
  val curriedAdder = adder.curried
  println(curriedAdder(10)(20))


  // PAFs can also be created with single-parameter groups
  def wrap2(prefix: String, html: String, suffix: String) = prefix + html + suffix
  val wrapWithDiv2 = wrap2("<div>", _: String, "</div>")
  println(wrapWithDiv2(hello))
}
