package com.functional

object LoanPattern extends App {

  // Multiple parameter groups, param resource: A has to be closeable, f is a by-name function
  def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B = {
    try f(resource)
    finally {
      resource.close()
    }
  }

  using(io.Source.fromFile("test.txt")) { source =>
    for (line <- source.getLines()) {
      println(line)
    }
  }
}
