package com.functional

object Concurrency {
  // Concurrency and Mutability do not mix well
  //  - Immutable objects are always thread-safe

  // Scala provides tools for concurrent and parallel programming
  //  - Akka actors
  //  - Scala futures (came from Akka)
  //
  // Third-party:
  //  - Parallel collections classes

  // Akka Actors are live objects that can interact with any number of other objects, including itself.
  //  - Functions as a long-lived message processor with a potentially changing state

  // Futures are intended as single-purpose entity's for short use.
  //  - Only addressable by the code waiting for a future's promise, and the code fulfilling the promise
}
