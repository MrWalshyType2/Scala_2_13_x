package com.functional

import scala.annotation.tailrec
import scala.io.StdIn.readLine
import scala.util.Random

// State should be immutable in a FP project
//  - coin flip game
case class GameState(numFlips: Int, numCorrectGuesses: Int)
case class History(states: Seq[GameState])

object ImmutableStateGame extends App {

  val state = GameState(0, 0)
  val history = History(List())
  val random = Random
  mainLoop(state, random, history)

  @tailrec
  def mainLoop(state: GameState, random: Random, history: History): Unit = {
    implicit val implicitHistory = history.copy()
    // prompt for input
    showPrompt
    // get input
    val input = getUserInput
    // compare flip to user input
    input match {
      case "H" | "T" => {
        // flip coin
        val coinFlipResult = coinToss(random)
        val newNumFlips = state.numFlips + 1

        if (input.equals(coinFlipResult)) {
          val newGameState = state.copy(numFlips = newNumFlips, numCorrectGuesses = state.numCorrectGuesses + 1)
          printGameState(printableFlipResult(coinFlipResult), newGameState)
          mainLoop(newGameState, random, history)
        } else {
          val newGameState = state.copy(newNumFlips)
          printGameState(printableFlipResult(coinFlipResult), newGameState)
          mainLoop(newGameState, random, history)
        }
      }
      case "N" => {
        printGameOver
        printGameState(state)
        val updatedHistory = updateHistory(state)
        printHistory
        printNewGame
        mainLoop(GameState(0, 0), random, updatedHistory)
      }
      case _ => {
        printGameOver
        printGameState(state)
      }
    }
  }

  // Impure functions
  def showPrompt: Unit = { print("\n(h)eads, (t)ails, (n)ew or (q)uit") }
  def getUserInput: String = readLine.trim.toUpperCase
  def printableFlipResult(flip: String): String = flip match {
    case "H" => "Heads"
    case "T" => "Tails"
  }

  def printGameState(printableResult: String, gameState: GameState): Unit = {
    print(s"Flip was $printableResult")
    printGameState(gameState)
  }

  def printGameState(gameState: GameState): Unit =
    println(s"#Flips: ${gameState.numFlips}, #Correct: ${gameState.numCorrectGuesses}")

  def printGameOver: Unit = println("\n=== GAME OVER ===")
  def printNewGame: Unit = println("\n=== NEW GAME ===")

  // "H" for heads, "T" for tails
  def coinToss(r: Random) = {
    val i = r.nextInt(2)
    i match {
      case 0 => "H"
      case 1 => "T"
    }
  }

  def updateHistory(gameState: GameState)(implicit history: History): History = history.copy(history.states ++ Seq(gameState))
  def printHistory(implicit history: History): Unit = {
    println("=== HISTORY ===")
    history.states.foreach(printGameState)
  }
}
