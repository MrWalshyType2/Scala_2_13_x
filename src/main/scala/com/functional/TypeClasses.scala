package com.functional

object TypeClasses extends App {

  // A type class is a programming technique that allows new behaviour to be added to
  // closed data types without inheritance, and without access to the types original source code

  // A type class has three components:
  //  1- The type class (a trait that takes at least one generic param)
  //  2- Instances of the type class for types to extend
  //  3- Interface methods to expose the new API behaviour
  sealed trait Animale
  final case class Dog(name: String) extends Animale
  final case class Cat(name: String) extends Animale

  // Imagine wanting to add new behaviour to Dog, but not Cat
  //  - without modifying its source
  // Type class incoming
  trait BehavesLikeHuman[A] { // step 1
    // Using the generic type A allows this functionality to be applied to whatever type wanted
    def speak(a: A): Unit
    def eatHumanFood(): Unit
  }

  object BehavesLikeHumanInstances { // step 2
    // for Dog | implicit for implementation
    implicit val dogBehavesLikeHuman = new BehavesLikeHuman[Dog] {
      override def speak(dog: Dog): Unit = println(s"I am Dog, my name is ${dog.name}")

      override def eatHumanFood(): Unit = println(s"Haha, I got your food")
    }
  }

  // step 3 (optional here means 3a and 3b will both work, but implementation is different)
  // optional 3a - Interface objects approach
  import BehavesLikeHumanInstances.dogBehavesLikeHuman
  val joey = Dog("Joey")
  dogBehavesLikeHuman.speak(joey)

  // optional 3b
  // Interface syntax
  object BehavesLikeHumanSyntax {
    implicit class BehavesLikeHumanOps[A](value: A) {
      def speak(implicit behavesLikeHumanInstance: BehavesLikeHuman[A]): Unit = {
        behavesLikeHumanInstance.speak(value)
      }

      def eatHumanFood(implicit behavesLikeHumanInstance: BehavesLikeHuman[A]): Unit = {
        behavesLikeHumanInstance.eatHumanFood()
      }
    }
  }
  import BehavesLikeHumanSyntax.BehavesLikeHumanOps
  joey.speak
  joey.eatHumanFood
}
