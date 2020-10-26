package com.functional

import com.functional.DomainModelling.{Cheese, CrustSize, CrustType, LargeCrust, MockPizzaDao, Onions, RegularCrust, SmallCrust, ThickCrust, Topping}

object FunctionalObjects extends App {

  // Functional Objects do not have mutable state (fp/oop hybrid approach)
  //  - Data is modeled as immutable fields in case classes
  //  - Behaviours (methods) are in the same class as the data
  //  - Behaviours are implemented as pure functions

  // FunctionalPizza methods do not take a pizza as input, they operate on the current reference

  // Although the functions may look impure, they implicitly pass the current instance as 'this' to each method
  type Money = BigDecimal

  val toppingPrices = MockPizzaDao.getToppingPrices()
  val crustSizePrices = MockPizzaDao.getCrustSizePrices()
  val crustTypePrices = MockPizzaDao.getCrustTypePrices()

  case class FunctionalPizza(crustSize: CrustSize, crustType: CrustType, val toppings: Seq[Topping]) {
    def addTopping(t: Topping): FunctionalPizza = {
      val newToppings = this.toppings :+ t
      this.copy(toppings = newToppings)
    }

    def removeTopping(t: Topping): FunctionalPizza = {
      val newToppings = this.toppings.filter(_ != t)
      this.copy(toppings = newToppings)
    }

    def removeAllToppings(): FunctionalPizza = this.copy(toppings = Seq[Topping]())

    def updateCrustSize(crustSize: CrustSize): FunctionalPizza = this.copy(crustSize = crustSize)
    def updateCrustType(crustType: CrustType): FunctionalPizza = this.copy(crustType = crustType)

    def getPrice(toppingPrices: Map[Topping, Money],
                 crustSizePrices: Map[CrustSize, Money],
                 crustTypePrices: Map[CrustType, Money]): Money = {
      val base = 10.00
      val cost = this.toppings.length * 0.50 + base
      cost
    }
  }

  val pizza = FunctionalPizza(SmallCrust, RegularCrust, Seq(Cheese))
  val pizza2 = pizza.addTopping(Onions)
  val pizza3 = pizza.updateCrustSize(LargeCrust)
                    .updateCrustType(ThickCrust)
                    .removeAllToppings()
}
