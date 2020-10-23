package com.functional

import scala.annotation.tailrec
import scala.collection.mutable.ArrayBuffer

object ForExpressions extends App {
  // For expressions are also called for comprehensions
  //  - these are not loops

  // for expressions contain the following three types of expression:
  //  - Generators (pattern <- expression)
  //  - Filters (if (expression)) drops all elements for which the expression returns false
  //  - Definitions (pattern = expression)

  // filter vs withFilter
  //  - a for expression with an if filter is translated to a withFilter method call
  //  - if withFilter does not exist, the compiler will look for a filter method
  //  - if neither exist, a compilation error will occur

  val persons = Seq(Person("Fred"), Person("Bob"))

  val names = for {
    p <- persons    // generator, p iterates over all elements inside persons
    name = p.name   // definition
    if (name startsWith "F") // filter
  } yield name
  println(names)

  // RULES
  //  - If a custom data type defines a foreach method, for loops can be used with one or more generators
  //    for (i <- ints) println(i)
  //
  //  - If a custom data type defines only map, for expressions with one generator can be used
  //
  //  - If a custom data type defines both map and flatMap, for expressions can contain multiple generators
  //
  //  - If a custom data type defines withFilter, filter expressions starting with 'if' can be used in a for expression
  abstract class CustomClass[A] {
    def map[B](f: A => B): CustomClass[B]
    def flatMap[B](f: A => CustomClass[B]): CustomClass[B]
    def withFilter(p: A => Boolean): CustomClass[A]
    def foreach(b: A => Unit): Unit
  }

  // * is the varargs parameter
  case class Sequence[A](initialElements: A*) {
    private val elems = ArrayBuffer[A]()
    elems ++= initialElements // is a for loop like: for { e <- initialElements } elems += e

    // Using the ArrayBuffers foreach
//    def foreach(codeBlock: A => Unit): Unit = elems.foreach(codeBlock)

    def foreach(codeBlock: A => Unit): Unit = {
      @tailrec
      def iterate(codeBlock: A => Unit, index: Int): Unit = {
        if (index >= elems.length) ()
        else {
          codeBlock(elems(index))
          iterate(codeBlock, index + 1)
        }
      }
      iterate(codeBlock, 0)
    }

    def map[B](f: A => B): Sequence[B] = {
      val abMap = elems.map(f)
      Sequence(abMap.toSeq :_*) // :_* = Splat operator
    }

    def withFilter(p: A => Boolean): Sequence[A] = {
      val arrayBuffer = elems.filter(p)
      Sequence(arrayBuffer.toSeq :_*)
    }

    // Similar signature to map, except the CBN function returns a Sequence of type B
    def flatMap[B](f: A => Sequence[B]): Sequence[B] = {
      // flatMap is essentially calling a map function, then flattening the result with a flatten method
      val mappedSequence: Sequence[Sequence[B]] = map(f)
      flatten(mappedSequence)
    }

    def flatten[B](nestedSequence: Sequence[Sequence[B]]): Sequence[B] = {
      var sequence = ArrayBuffer[B]()
      for (listB: Sequence[B] <- nestedSequence) {
        for (element <- listB) {
          sequence += element
        }
      }
      Sequence(sequence.toSeq :_*)
    }
  }
  val a = Sequence(1, 2, 3)
  println(a)

  for (i <- a) println(i) // requires foreach

  val doubledA = for { // requires map
    i <- a
  } yield i * 2
  doubledA.foreach(println)

  // Trying to use a filter in a for expression requires a withFilter method
  val res = for {
    i <- a
    if i > 2 // requires withFilter
  } yield i * 2
  println(res)

  val people = Sequence(
    Person("Fred"), Person("Bob"), Person("Greg"), Person("Dorothy")
  )

  val people2 = Sequence(
    Person("Dorothy"), Person("Bob"), Person("Grognak")
  )

  // Multiple generators require a flatMap method to exist
  val mutualPeople = for {
    p <- people // generator 1
    p2 <- people2 // generator 2
    if (p.name == p2.name)
  } yield p
  println(mutualPeople)
}
