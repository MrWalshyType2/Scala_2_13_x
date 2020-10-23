package com.functional

object Functors extends App {

  // Functors are things that can be mapped over
  //  - a functor can be thought of as a trait
  trait Functor[A] {
    def map[B](f: A => B): Functor[B]
  }
}
