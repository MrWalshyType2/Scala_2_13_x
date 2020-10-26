package com.functional

import akka.actor.{Actor, ActorSystem, Props}

object AkkaActors extends App {
  // Akka:
  //  - Actors are long-running threads capable of responding to immutable messages sent to them
  //  - Messages sent to actors are usually case class and case objects
  //  - Actors respond to messages with pattern-matching statements
  //  - Actors do not share state with other actors

  // Actors & the Actor Model
  //  - The Actor model is a mental model for thinking about systems built with actors
  // Actors:
  //  - A long-running process running in parallel to the main app thread, responds to messages
  //  - Smallest unit when building an actor-based system
  //  - Encapsulates state and behaviour
  //  - Can only message an actor for state information (no direct access)
  //  - Has a mailbox
  //  - Communicated with by sending an immutable message that is delivered to the mailbox
  //  - Received messages are took out of the mailbox, opened, processed with an algorithm
  //  - Actors have supervisors, specifically just one supervisor (an actor that created the actor)t
  //  - May have children actors
  //  - May have sibling actors
  //
  // Akka Actors should delegate work ASAP to process their mailbox as fast as possible.
  case class Hello(message: String)

  // An Actor extends akka.actor.Actor
  //  - All an actor does is respond to messages sent to it
  class HelloActor extends Actor {
    override def receive: Receive = {
      case Hello(s) => {
        println(s"You said $s")
        println(s"$s back at you")
      }
      case _ => println("I missed that")
    }
  }

  // Create a system for actors to run in
  val actorSystem = ActorSystem("HelloSystem")

  // Create and start the HelloActor
  val helloActor = actorSystem.actorOf(
    Props[HelloActor],
    name = "helloActor"
  )

  // Send the actor a known message
  helloActor ! Hello("Hey there")

  // Send an unknown message
  helloActor ! "Bluh"

  // Shut down the actor system
  actorSystem.terminate()
}
