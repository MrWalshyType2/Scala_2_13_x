package com.functional

object StateMonad extends App {
  // State can be handled without a monad
  //  - create a construct to mode the state (typically a case class)
  //  - create a helper function that takes:
  //    - the previous state
  //    - some increment or 'delta' to that state
  //    then returns a new state based on those two passed in values
  case class GolfState(strokes: List[Int])

  def nextStroke(previousState: GolfState, distanceOfNextHit: Int): GolfState =
    GolfState(distanceOfNextHit :: previousState.strokes)

  val state1 = GolfState(Nil)
  val state2 = nextStroke(state1, 15)
  val state3 = nextStroke(state2, 20)
  println(state3)

  // State with a for expression
  case class State(value: Int) {
    def flatMap(f: Int => State): State = State(f(value).value)
    def map(f: Int => Int): State = State(f(value))
  }

  val result = for {
    a <- State(20)
    b <- State(a + 15) // manually carried a over
  } yield b
  println(s"RESULT: $result")

  // Better state where values do not have to be manually carried
  case class BetterState[S, A](run: S => (S, A)) {
    // S = State, A & B are generic labels
    // run is a code block

    def flatMap[B](g: A => BetterState[S, B]): BetterState[S, B] = BetterState { (s0: S) =>
      val (s1, a) = run(s0)
      g(a).run(s1)
    }

    def map[B](f: A => B): BetterState[S, B] = flatMap(a => BetterState.point(f(a)))
  }

  object BetterState {
    def point[S, A](v: A): BetterState[S, A] = BetterState(run = s => (s, v))
  }

  case class GolfState2(distance: Int)

  // takes distance of a swing as a param
  // takes an old state as an input parameter (took as input to the anonymous function)
  // result is a new state instance created from the anon func
  // the anon func adds the distance given as a function input and the distance from the prior state
  def swing(distance: Int): BetterState[GolfState2, Int] = BetterState { (previousState: GolfState2) =>
    val newDistance = previousState.distance + distance
    (GolfState2(newDistance), newDistance)
  }

  val stateWithNewDist: BetterState[GolfState2, Int] = for {
    _       <- swing(20)
    _       <- swing(10)
    totalDistance <- swing(0)
  } yield totalDistance

  val startingState = GolfState2(0)
  val resultingStateTuple: (GolfState2, Int) = stateWithNewDist.run(startingState)

  println(s"GOLF STATE:     ${resultingStateTuple._1}")
  println(s"TOTAL DISTANCE: ${resultingStateTuple._2}")
}
