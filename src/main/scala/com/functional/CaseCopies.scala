package com.functional

case class Person3(forename: String, surname: String)

case class User(name: Name, age: Int)
case class Name(forename: String, surname: String)

object CaseCopies extends App {
  // Basic copy
  val dulg1 = Person3("Dulg", "Bread") // original instance is unchanged
  val dulg2 = dulg1.copy(surname = "Wine")

  // Nested copy
  val fred = User(
    Name("Fred", "Duggie"),
    37
  )
  println(fred)

  val fred2 = fred.copy(age = 38)

  val newName = fred2.name.copy(surname = "Duggle")
  val fred3 = fred2.copy(newName)
  println(fred3)

  // Shortened nested copy
  val fred4 = fred.copy(fred.name.copy(surname = "Duggle"), 38)
  println(fred4)
}
