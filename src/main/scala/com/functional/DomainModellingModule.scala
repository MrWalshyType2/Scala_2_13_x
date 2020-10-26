package com.functional

object DomainModellingModule extends App {

  // Behaviours for a Domain Model can be implemented using modules
  //  - A module is a small piece of a program with a well defined interface & hidden implementation
  //  - Can use Scala traits to create modules

  // Essentials of modular solutions
  //  - A module construct that provides a good separation of interface and implementation
  //  - A way to replace a module with another that shares the same interface without
  //    changing or recompiling any modules dependant on the original module.
  //  - Modules should have a way to be wired together (system configuration)

  sealed trait Colour
  case object Brown extends Colour
  case object Black extends Colour
  case object White extends Colour
  case object Mixed extends Colour

  // We can have animals
  trait Animal

  // Animals can have tails
  abstract class AnimalWithTail(tailColor: Colour) extends Animal

  // Animals with tails have certain behaviours, lets define a Dogs tail services
  //  - This trait should only be mixed into classes extending it (add a self-type)
  trait DogTailServices {

    // Implementations of this trait must be a sub-type of AnimalWithTail
    this: AnimalWithTail => // this is a self-type (This trait can only be mixed into classes that extend trait x)

    def wagTail = println("tail wags")
    def lowerTail = println("tail lowers")
    def raiseTail = println("tail raises")
    def curlTail = println("tail curls")
  }

  // Dogs also have mouths
  trait DogMouthServices {
    this: AnimalWithTail =>
    def bark = println("WOOF!")
    def lick = println("kissies!")
  }

  // The services (behaviours) that are associated with a domain object have to be thought about
  //  - the services can be implemented as functions in logically organised traits

  // This object is a module with associated services
  object FrenchyXMinPin
    extends AnimalWithTail(Black)
    with DogTailServices
    with DogMouthServices

  val myJoey = FrenchyXMinPin
  myJoey.bark
  myJoey.lick
  myJoey.wagTail
}
