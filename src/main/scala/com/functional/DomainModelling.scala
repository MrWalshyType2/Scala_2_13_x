package com.functional

object DomainModelling extends App {
  // A domain is a sphere of activity or knowledge
  //  - A domain model refers to how objects are modelled in a given domain by software engineers.

  // The data portion of a domain model can be modelled as case classes with immutable fields
  sealed trait Topping
  case object Cheese extends Topping
  case object Onions extends Topping

  sealed trait CrustSize
  case object SmallCrust extends CrustSize
  case object LargeCrust extends CrustSize

  sealed trait CrustType
  case object RegularCrust extends CrustType
  case object ThickCrust extends CrustType

  case class Pizza (
     crustSize: CrustSize,
     crustType: CrustType,
     topping: Seq[Topping]
   )

  case class Order (
     pizza: Seq[Pizza],
     customer: Customer
   )

  case class Customer (
    name: String,
    phone: String,
    address: Address
  )

  case class Address (
     street1: String,
     street2: Option[String],
     city: String,
     postalCode: String
   )

  type Money = BigDecimal

  // The above data models do not have any behaviours (separation of concerns)
  //  - data is separated from the operations on the data.
  //    - this makes the data attributes and their relations clear

  // The behaviours could be wrote in a util class, or a companion object, but these are naive
  //  - companion objects allow access to private fields of its associated case class
  //    - can encourage writing impure functions (BAD BAD BAD)

  // The PizzaServiceInterface
  //  - This is a pure interface (a contract)
  //    - The contract states all non-abstract classes extending this trait must provide implementations of the defined services
  trait PizzaServiceInterface {
    def addTopping(p: Pizza, t: Topping): Pizza
    def removeTopping(p: Pizza, t: Topping): Pizza
    def removeAllToppings(p: Pizza): Pizza

    def updateCrustSize(p: Pizza, crustSize: CrustSize): Pizza
    def updateCrustType(p: Pizza, crustType: CrustType): Pizza

    def calculatePizzaPrice(
       pizza: Pizza,
       toppingsPrices: Map[Topping, Money],
       crustSizePrices: Map[CrustSize, Money],
       crustTypePrices: Map[CrustType, Money]
    ): Money
  }

  trait PizzaService extends PizzaServiceInterface {
    override def addTopping(p: Pizza, t: Topping): Pizza = p.copy(topping = p.topping :+ t)

    override def removeTopping(p: Pizza, t: Topping): Pizza = {
      val toppings = p.topping.filter(_ != t)
      p.copy(topping = toppings)
    }

    override def removeAllToppings(p: Pizza): Pizza = p.copy(topping = Seq[Topping]())

    override def updateCrustSize(p: Pizza, crustSize: CrustSize): Pizza = p.copy(crustSize = crustSize)

    override def updateCrustType(p: Pizza, crustType: CrustType): Pizza = p.copy(crustType = crustType)

    override def calculatePizzaPrice(pizza: Pizza, toppingsPrices: Map[Topping, Money], crustSizePrices: Map[CrustSize, Money], crustTypePrices: Map[CrustType, Money]): Money = {
      val base = BigDecimal(10)
      val numToppings = pizza.topping.length
      val price = base + 0.50 * numToppings
      price
    }
  }

  // Database impact on modular design
  trait PizzaDaoInterface {
    def getToppingPrices(): Map[Topping, Money]
    def getCrustSizePrices(): Map[CrustSize, Money]
    def getCrustTypePrices(): Map[CrustType, Money]
  }

  object MockPizzaDao extends PizzaDaoInterface {
    override def getToppingPrices(): Map[Topping, Money] = {
      Map(
        Cheese -> BigDecimal(0.30),
        Onions -> BigDecimal(0.50)
      )
    }

    override def getCrustSizePrices(): Map[CrustSize, Money] = {
      Map(
        SmallCrust -> BigDecimal(1.00),
        LargeCrust -> BigDecimal(1.50)
      )
    }

    override def getCrustTypePrices(): Map[CrustType, Money] = {
      Map(
        RegularCrust -> BigDecimal(0.00),
        ThickCrust -> BigDecimal(1.00)
      )
    }
  }

  trait OrderServiceInterface {
    // Implementing classes should provide their own db as an instance of PizzaDaoInterface
    protected def database: PizzaDaoInterface

    def calculateOrderPrice(order: Order): Money
  }

  trait AbstractOrderService extends OrderServiceInterface {
    // Concrete impl of the trait
    object PizzaService extends PizzaService // 'reifying' a trait (taking an abstract concept and making it concrete)
    import PizzaService.calculatePizzaPrice

    // trait does not have a concrete db ref, so it must be extended again
    private lazy val toppingPricesMap = database.getToppingPrices()
    private lazy val crustSizePrices = database.getCrustSizePrices()
    private lazy val crustTypePrices = database.getCrustTypePrices()

    private def calculateOrderPriceInternally(order: Order, toppingPricesMap: Map[Topping, Money], crustSizePrices: Map[CrustSize, Money], crustTypePrices: Map[CrustType, Money]): Money = {
      val pizzaPrices: Seq[Money] = for {
        pizza <- order.pizza
      } yield {
        calculatePizzaPrice(pizza, toppingPricesMap, crustSizePrices, crustTypePrices)
      }
      pizzaPrices.sum
    }

    // publicly available service
    override def calculateOrderPrice(order: Order): Money = {
      calculateOrderPriceInternally(order, toppingPricesMap, crustSizePrices, crustTypePrices)
    }
  }

  // Implementation
  object MockDbOrderService extends AbstractOrderService {
    val database = MockPizzaDao
  }

  // Running the code
  object MyPizzaService extends PizzaService
  import MyPizzaService._

  val address = Address("1 Street", None, "UK", "OL4")
  val customer = Customer("Fred", "04989403804", address)
  val order = Order(Seq[Pizza](), customer)

  val pizza = Pizza(SmallCrust, RegularCrust, Seq(Cheese))
  val newPizzas = order.pizza :+ pizza
  val updatedOrder = order.copy(pizza = newPizzas)

  val pizza2 = Pizza(LargeCrust, ThickCrust, Seq(Cheese))
  val pizza2a = addTopping(pizza2, Onions)
  val pizza2b = updateCrustSize(pizza2a, SmallCrust)

  val updatedPizzas = updatedOrder.pizza :+ pizza2b
  val updatedOrder2 = updatedOrder.copy(pizza = updatedPizzas)
  println(updatedOrder2)

  // Calculate the price
  import MockDbOrderService.calculateOrderPrice
  val orderPrice = calculateOrderPrice(updatedOrder2)
  println(s"ORDER PRICE: $orderPrice")
}
