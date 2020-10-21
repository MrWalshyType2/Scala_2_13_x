package com.examples.concurrency

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object Futures extends App {

  val startTime = currentTime

  // Creating some futures
  val aaFuture = getStockPrice("AA")
  val amzFuture = getStockPrice("AMZ")

  // Get a combined result of the Futures in a for-expression
  val result: Future[(Double, Double)] = for {
    aa <- aaFuture
    amz <- amzFuture
  } yield (aa, amz)

  // When the results are complete, do this
  result.onComplete {
    case Success(value) => {
      val totalTime = deltaTime(startTime)
      println(s"Success, time delta: $totalTime")
      println(s"Stock prices: $value")
    }
    case Failure(exception) => exception.printStackTrace
  }

  sleep(5000) // keeps the main thread alive while the others return, demo purposes

  def sleep(time: Long): Unit = Thread.sleep(time)

  // a simulated web service
  def getStockPrice(stockSymbol: String): Future[Double] = Future {
    val r = scala.util.Random
    val randomSleepTime = r.nextInt(3000)
    println(s"For $stockSymbol, sleep time is $randomSleepTime")
    val randomPrice = r.nextDouble * 1000
    sleep(randomSleepTime)
    randomPrice
  }

  def currentTime = System.currentTimeMillis()
  def deltaTime(t0: Long) = currentTime - t0
}
