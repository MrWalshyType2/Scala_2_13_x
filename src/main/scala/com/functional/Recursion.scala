package com.functional

import scala.annotation.tailrec

// UML Sequence Diagrams are good for visualising recursion
object Recursion extends App {

  // Recursion is used with functions to make recursive functions
  //  - a function that calls itself
  // Recursion though process?
  //  - Function signature
  //  - Algorithm end condition
  //  - Actual algorithm

  // Recursion is used instead of a loop as loops require var fields

  // Recursion is commonly used with collections
  //  - the List type in Scala is a linked list (head, tail)
  val list1 = List(1, 2, 3)
  val list2 = 1 :: 2 :: 3 :: Nil // equivalent of above, but using 'con cells', Nil has to be the last element

  // Recursive function to get the sum of a list of Integers
  //  - the sum of an Int List is the sum of the head element and the sum of the tail elements
  def sum(list: List[Int]): Int = list match {
    // using pattern matching
    case Nil => 0 // if the list is empty, eval to 0. Can also be wrote as: case List() => 0
    case head :: tail => head + sum(tail) // Splits the list into head and tail components.
  }

  println(sum(list1))


  // The recursive sum function shown is good, but a StackOverflowError could occur as it is not tail-recursive
  //  - Each JVM thread has a Stack (Call Stack) that is created at the same time as the thread.
  //    - A stack stores frames (Stack Frames)
  //      - The stack holds local variables and partial results. It also is involved with method invocation and returning.
  //        The params, local vars, and other data is stored in a frame.
  //  - Only two operations can be directly applied on a Java stack; pushing and popping frames
  //  - When a method is invoked, a frame is added to the stack.
  //    - Once the recursive calls end, the stack then unwinds popping off all the added frames.

  // Tail-recursive sum function
  def tailSum(list: List[Int]): Int = {
    // An accumulator is used to track the current value without filling up the stack
    @tailrec
    def sumWithAccumulator(list: List[Int], accumulator: Int): Int = list match {
      case Nil => accumulator
      case head :: tail => sumWithAccumulator(tail, accumulator + head)
    }
    sumWithAccumulator(list, 0)
  }
}
