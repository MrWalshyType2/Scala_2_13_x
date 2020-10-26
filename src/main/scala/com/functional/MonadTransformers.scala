package com.functional

object MonadTransformers extends App {

  // A monad transformer is a special monad that can stack its own effects on the effects of another monad.
  //  - The result of stacking the effects is another monad, combining the effects of both
  // Monad transformers have a T after the name by convention: StateT, OptionT
  // The standard Scala library does not define a monad trait

  // To use a monad transformer:
  //  - A Monad interface is needed
  //  - The types to use as monads should be instances extending the Monad interface
  trait Monad[A] {
    def flatMap[A, B](f: A => Monad[B]): Monad[B]
    def map[A, B](f: A => B): Monad[B]
    def point[A](a: A): Monad[A] // lifts a value into a monad
  }
}
