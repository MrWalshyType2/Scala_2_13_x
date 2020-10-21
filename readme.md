Acknowledgment: https://docs.scala-lang.org/overviews/scala-book/introduction.html
- Code examples are mainly from here

# Scala Overview
Scala is a statically typed (explicitly declared), high-level language. The syntax of Scala is known to be 'expressive', supporting the functional programming (FP) paradigm.

Scala has a built-in type inference system that reduces code bloat. The files compile to .class files that can run on the JVM, this compatibility means Java libraries are easily used in Scala.

- Scala was created by Martin Odersky (Javac father)
- Scala is a pure OOP language. Every variable is an object and every operator is a method on an object.
- As Scala is a FP language, functions are variables and can be passed into other functions.
## Compiling a Scala class
Scala classes can be compiled and run in a similar way to Java classes:
```
scalac <FileName>.scala
scala <FileName>
```
Install the Scala binaries for command-line access to scala and scalac.

## SBT
SBT is short for the Scala Build Tool. SBT defines a standard project structure:
```
build.sbt
project/
src/
-- main/
    |-- java/
    |-- resources/
    |-- scala/
-- test/
    |-- java/
    |-- resources/
    |-- scala/
target/
```

This structure can be created from the command line like so:
```
mkdir <project_name>
cd <project_name>
mkdir -p src/{main,test}/{java,resources,scala}
mkdir project target
```

The only thing necessary to start coding is the build.sbt file:
```
name := <project_name>
version := <app_version>
scalaVersion := "2.13.3"

libraryDependencies +=
    "org.scalatest" %% "scalatest" % "3.0.8" % Test
```

A SBT project can be compiled and executed with the sbt run command:
```
sbt run
```

### Test Driven Development (TDD)
TDD in Scala uses the 'FunSuite'.
- The test class should extend 'FunSuite'
- Tests are created with unique names
- Call assert at the end of test to assert a condition has been satisfied
```
object Hello extends App {
    val p = new Person("Alvin Alexander")
    println(s"Hello ${p.name}")
}

class Person(var name: String)
```

```
class HelloTests extends FunSuite {

    // test 1
    test("the name is set correctly in constructor") {
        val p = new Person("Barney Rubble")
        assert(p.name == "Barney Rubble")
    }

    // test 2
    test("a Person's name can be changed") {
        val p = new Person("Chad Johnson")
        p.name = "Ochocinco"
        assert(p.name == "Ochocinco")
    }

}
```

Run the tests with:
```
sbt test
```

### Behaviour-Driven Development (BDD)
- Tests should be relatively easy to read for a 'domain expert'
- BDD test classes extend 'FunSpec'
- Each test set begins with 'describe'
- Each test in a set begins with 'it', it is the function being tested
```
object MathUtils {

    def double(i: Int) = i * 2

}
```
```
class MathUtilsSpec extends FunSpec {
  
    describe("MathUtils::double") {

        it("should handle 0 as input") {
            val result = MathUtils.double(0)
            assert(result == 0)
        }

        it("should handle 1") {
            val result = MathUtils.double(1)
            assert(result == 2)
        }

        it("should handle really large integers") (pending)
        
    }

}
```

## Naming conventions
Follows the camel case convention as in Java:
- Class names   : Person, Currency
- Variables name: name, upperBound
- Method names  : convToInt, toLower

## Entry-point
The entry point to a Scala application is the main method.
- Extend the App trait to forego writing the main method
```
// object means Runner is a singleton, directly instantiated class
object Runner {
    def main(args: Array[String]) = {
        println("Hello")
    }
}
```

## Variables
Scala has two variable types:
- val
    - immutable variable (preferred)
- var
    - mutable variable (only use when necessary)
    
The types of variables can be inferred by the compiler from the code on the right-hand side of the assignment operator.
- The type can be explicitly stated, but the inferred form is preferred unless clarity is needed.
```
val x = 1
var y = 0

val x: Int = 1
```
Immutable fields are a cornerstone of Functional Programming, they also make the code more similar to algebra.

### Built-in Types
Scala comes with built-in types for handling different data types. All data types in Scala are objects instead of being primitive.

The basic numeric types are:
- Byte
- Int (default numeric type)
- Long
- Short
- Double (default numeric type)
- Float

Bytes, Longs, and Shorts will default to an Int unless explicitly stated due to inference.
Floats will default to Double unless explicitly stated.

#### BigInt and BigDecimal
The BigInt and BigDecimal types are available for large numbers, they also support all standard operators used with other numeric types.

#### String and Char
The String and Char data types are often declared implicitly.
```
val name = "Bill" // String
val c = 'a' // Char
```
Strings can be made multiline by using triple double-quotes.
- The pipe key (|) and the stripMargin function can be used to ensure there is no unnecessary whitespace.
```
val multilineString = """Hello,
                        |and what is your name?""".stripMargin
```

##### String Interpolation
Strings can be concatenated without using the concatenation operator using String Interpolation.
- s"" indicates an interpolated string
- $ is used to indicate a variable in an interpolated string
- The variables can be placed inside curly braces, expressions can also be put inside the braces.
```
val forename = "Bob"
val surname = "Fred"

val name = forename + " " + surname
val name = s"$forename $surname"
```

## Command-line I/O
### Output
Output is written to the console using the standard out (STDOUT) stream. Output can also be written to standard error (STDERR).
```
println("Hello world") // writes to STDOUT
System.err.println("ERROR, OH NO!") // writes to STDERR
```

### Input
Command-line input can be gathered in many ways, the easiest is the readLine method in the scala.io.StdIn package.
```
import scala.io.StdIn.readLine

print("Enter your name: ")
val name = readLine()
```

## Control Structures
### If/Else
The if/else control structure is similar to other languages, the if/else construct will return a value in Scala.
```
if (someCondition) {
    doThis()
} else if (someOtherCondition) {
    doThis2()
} else {
    false // false is returned
}

val x = if (a > b) a else b
```

#### Expression-oriented Programming
An interesting part about writing expressions is that they return values. Expression-oriented programming relies on programming expressions that return values.

The opposite of expressions are statements, these don't return a value. They are used for their side-effects.

### Match expressions
The match expression is similar to a switch statement in Java, except it allows for pattern matching.
- The underscore _ case is the catch-all, default case.
```
val answer = i match {
    case 1 => "One"
    case 2 => "Two"
    case 3 if i == 3 => "Three" // if expressions can be used in a case statement
    case _ => "Not one or two"
}

val booleanString = bool match {
    case true => "true"
    case false => "false"
}

def getClassAsString(x: Any):String = x match {
    case s: String => s + " is a String"
    case i: Int => "Int"
    // ...
}
```

### Try/Catch
Scala has a try/catch control structure for capturing exceptions. Pattern matching can also be used.
```
try {
    saveToDb(person)
} catch {
    case ioe: IOException => println(ioe)
} finally {
    // some code to run after try-catch
}
```

### For loops
For-loops in Scala operate like a forEach loop in Java. The keyword yield turns a for-loop into a for-expression that yields a result.
```
for (arg <- args) println(arg)

// from x to y syntax
for (i <- 0 to 5) println(i)

// from x to y by syntax
for (i <- 0 to 10 by 2) println(i)

val x = for (i <- 1 to 5) yield i * 2

val fruits = List("apple", "banana", "lime")

val fruitLengths = for {
    f <- fruits
    if f.length > 4
} yield f.length
```

For and foreach can be used with Maps.
```
val ratings = Map(
    "One" -> 3.0,
    "Two" -> 5.0,
    "Three" -> 4.6
)

// Print the names and rating using for:
for ((name, rating) <- ratings) println(s"$name $rating")

// With foreach
ratings.foreach {
    case(name, rating) => println(s"key: $name, value: $rating")
```

### While & Do/While
Similar to Java while and do-while loops.
```
while(condition) {
    statement(a)
}

do {
    statement(a)
} while(condition)
```

## Classes
Scala classes are simple to create, they don't require getter and setter methods.
```
class Person(var forename: String, var surname: String) {
    def printName() = println(s"$forename $surname")
}

val p = new Person("Bob", "Turok")
println(p.forename)
p.printName()
```

### Auxiliary class constructors
The primary constructor is inline with the class declaration, aux constructors can be defined using this.
- Each aux constructor must call a previously defined constructor.
```
// These vals would normally be enumerations
val defaulSize = 32
val defaultType = "DEEP"

// primary constructor
class Pizza (var crustSize: Int, var crustType: String) {
    
    // one-arg constructor
    def this(crustSize: Int) = {
        this(crustSize, defaultType)
    }

    // zero-arg
    def this() = {
        this(defaultSize, defaultType)
    }
```

### Default values for constructor parameters
Default values can be supplied for a class constructor, this allows classes to be instantiated in different ways.
```
class Socket(var timeout: Int = 2000, var linger: Int = 300) {
    ...
}

new Socket()
new Socket(100)
new Socket(100, 500)

// Using named parameters
new Socket(timeout=200, linger=300)
```
Default constructor parameters provide at least two benefits:
- Can provide preferred, default values for params
- The consumers of a class can override the values

## Scala methods
Scala classes have methods just like other OOP languages.
- A methods return type does not need to be declared
```
def sum(a: Int, b: Int): Int = a + b
```

## Traits
Traits allow code to be broken into small, modular units. They are similar to abstract classes and interfaces in Java.
- Methods on a trait can be inherited
- Multiple traits can be inherited by using the 'with' keyword after first extending a trait
    - extends will extend the first trait
    - with will extend any further specified traits
```
trait Runner {
    def start(): Unit = println("running")
    def stop(): Unit = println("stopped")
}

class Dog(name: String) extends Runner {
    def speak(): String = "Woof woof!"
}
```

Usecases:
- define an interface for some functionality without implemented behaviour (similar to Java interfaces)
- add working methods to the interface and use like an abstract class (known as a 'mixin')
- runtime binding of traits
```
trait TailWagger {
    def start(): Unit = println("Tail wags")
}

// Creating a Dog instance that uses a trait
//  - this works as the methods in TailWagger are defined and not abstract
val d = new Dog("Bobby") with TailWagger
```

## Abstract classes
Abstract classes are only needed in two cases:
- A base class requires constructor arguments
- The Scala code is called from Java code

Scala traits don't take constructor parameters, so an abstract class is used whenever a base class needs constructor parameters.
```
abstract class Animal(name: String) {
    def speak(): Unit = println("Hiya")
}

// The parameter of a super class has to be passed in
class Dog(name: String) extends Pet(name)
```
Classes, like Java, can only extend a single abstract class.

## Collection classes
The Java collection classes can be used in Scala, but it is preferable to use Scala's collection classes. The basic Scala collection classes are:
- List: linear (linked list), immutable sequence
- ListBuffer
- Vector: indexed, immutable sequence
- ArrayBuffer: indexed, mutable sequence
- Map: base Map class (key:value pairs)
- Set: base Set class

Map & Set have immutable and mutable versions.

### Populating lists
```
val nums = List.range(0, 10)
val nums = (1 to 10 by 2).toList
val letters = ('a' to 'f').toList
val letters = ('a' to 'f' by 2).toList
```

### Sequence methods
```
val nums = (1 to 10).toList
val names = List("fred", "igor", "julia")

// foreach
names.foreach(println)

// filter followed by foreach (filters all nums less than 4)
nums.filter(_ < 4).foreach(println)

// map maps values into a list
//  - map applies an algorithm to every element in a collection. This returns new transformed values for each element
val doubles = nums.map(_ * 2)

// foldLeft takes a seed value, the value to start with
nums.foldLeft(0)(_ + _) // sums the list
nums.foldLeft(1)(_ * _) // product of the list
```

## Tuples
Tuples allow heterogenous (different) collections of elements in a container.
- Contains between 2 and 22 values
- Can contain values of different types

Tuples are not a collection class, this means they don't have methods like map or filter.
```
class Person(var name: String)

val myTuple = (22, "Hey", new Person("Gru"))

// tuple values are accessed by number
println(t._1) // 22

// tuple fields can be assigned to variables
val (num, string, person) = (22, "Hey", new Person("Gru"))
```

## Enumerations
Enumerations are useful for groups of constants. They can be created by having case objects extend a sealed trait.
```
sealed trait DayOfWeek
case object Monday extends DayOfWeek
case object Tuesday extends DayOfWeek
// ...

val day: DayOfWeek = Monday
```

## Functional Programming
Functional programming emphasizes writing applications that only use pure functions and immutable values.
- The code is similar to math

### Pure Functions
A pure function, according to Alvin Alexander, should:
- Have an output that depends only on its input variables
- No hidden state is mutated
- No 'back doors', data is not read from an external source or wrote to
```
// pure function
def double(i: Int): Int = i * 2

// recursive pure function
def sum(list: List[Int]): Int = list match {
    case Nil => 0
    case head :: tail => head + sum(tail)
}
```

### Impure Functions
In Scala, any function that returns 'Unit' is impure.

Impure functions do one or more of:
- Read hidden inputs
- Write hidden outputs
- Mutate passed-in parameters
- Perform some external I/O

### Passing Functions around
Functions can be created as variables in Scala, allowing functions to be passed as parameters to other functions. These functions are known as 'Higher-Order Functions' (HOFs).

The map filter methods for collections take an anonymous function as a parameter for example.
```
val nums = (1 to 10).toList
val doubles = nums.map(_ * 2)
val lessThanSix = nums.filter(_ < 6)

// equivalent of above
def double(i: Int): Int = i * 2
val doubles = nums.map(double)
```

## Null?
Null values are not a thing in functional programming as null values are not used in algebra.

Scala makes use of constructs like the Option/Some/None classes to handle what would normally be a null value.
- Some & None are subclasses of Option.
```
def toInt(s: String): Option[Int] = {
    try {
        Some(Integer.parseInt(s.trim))
    } catch {
        case e: Exception => None
    }
}
```

### Working with the return of an Option
The consumer of a method returning an Option needs to handle the return types. The two main approaches in FP are:
- A match expression
- A for expression
```
// Pattern matching
toInt(x) match {
    case Some(i) => println(i)
    case None => println("Didn't work.")
}

// For/Yield
//  - y will either be Some[Int] or None if a toInt() fails
val y = for {
    a <- toInt(stringA)
    b <- toInt(stringB)
} yield a + b
```

Some & None can be though of as containers for data. As they are similar to the collection classes, the map, filter, and foreach methods are implemented.
```
// toInt evals first
// then the result of toInt is evaluated to Some(<num>).foreach(println)
// foreach knows how to get the contained value and pass it to the method specified
toInt("1").foreach(println) // 1
toInt("X").foreach(println) // Does not print anything
```

### Anything other than Option?
A trio of classes known as Try/Success/Failure operate in a similar manner. These classes have a different usecase:
- primarily used with code that can throw an exception
- the Failure class gives access to the exception message

Try/Success/Failure is commonly implemented when writing methods that interact with files, databases and internet services.

## Companion Objects
Companion objects are declared in the same file as a class. A companion object is said to accompany a class.
```
class Pizza {}
object Pizza{}
```
Companion objects, and their respective classes, are able to access each other's private members (fields and methods).
```
class SomeClass {
    def printFilename() = {
        // Accessing the HiddenFilename stored in the companion object
        println(SomeClass.HiddenFilename)
    }
}

object SomeClass {
    private val HiddenFilename = "/tmp/foo.bar"
}
```

### Forget about the new keyword
Companion objects allow classes to be instantiated without specifying the new keyword, you just have to apply an 'apply' method in a classes companion object. The Scala compiler is built to handle this.
- The apply() method acts like a Factory Method
```
class Person {
    var name = ""
}

object Person {
    def apply(name: String): Person = {
        var p = new Person
        p.name = name
        p
    }
}

// Equivalent
val p = Person.apply("Fred")
val p2 = Person("Fred")
```

#### Multiple constructors
Multiple apply() methods can be specified in a Companion object.
```
class Person {
    var name: Option[String] = None
    var age: Option[Int] = None
    override def toString = s"$name, $age"
}

object Person {

    // a one-arg constructor
    def apply(name: Option[String]): Person = {
        var p = new Person
        p.name = name
        p
    }

    // a two-arg constructor
    def apply(name: Option[String], age: Option[Int]): Person = {
        var p = new Person
        p.name = name
        p.age = age
        p
    }

}

val p1 = Person(Some("Fred"))
val p2 = Person(Some("Wilma"), None)
```

### Unapply
Companion objects have an apply method to construct a new instance, unapply allows the deconstruction of an instance.
```
class Person(var name: String, var age: Int)

object Person {
    def unapply(p: Person): String = s"${p.name}, ${p.age}"
}

val p = new Person("Lori", 22)
val result = Person.unapply(p) // Lori, 22
```

Other return types for the unapply method can be used:
```
class Person(var name: String, var age: Int)

object Person {
    def unapply(p: Person): Tuple2[String, Int] = (p.name, p.age)
}

val p = new Person("Lori", 22)
val (name, age) = Person.unapply(p) // name = Lori, age = 22
```

The unapply method is an extractor that enables a convenient form of pattern-matching match expressions. Case classes implement apply and unapply methods automatically.

## Case Classes
A case class is special class in Scala, it contains all of the regular class functionality, plus more.

When the compiler sees the 'case' keyword with a class, code is generated with the following benefits:
- constructor params are public val fields by default
- apply methods are created in the companion object of the class
- unapply methods are generated, allowing case classes to be used in match expressions more extensively
- a copy method is generated
- equals and hashCode methods are generated
- toString method is generated
- new instances don't require the 'new' keyword
```
case class Person(name: String, relation: String)

val bob = Person("Bob", "Friend")
```

### No mutator methods
As the fields in a case class are val by default, we can get but not set the values after instantiation.
```
bob.name // legal
bob.name = "fred" // illegal
```

### Unapply
Case classes automatically generate unapply methods.
```
trait Person {
    def name: String
}

case class Student(name: String, year: Int) extends Person
case class Teacher(name: String, specialty: String) extends Person

// Match expression that makes use of the generated unapply methods
def getPrintableString(p: Person): String = p match {
    case Student(name, year) =>
        s"$name is a student in Year $year."
    case Teacher(name, whatTheyTeach) =>
        s"$name teaches $whatTheyTeach."
}
```

Scala specifies a standard that unapply method should return the case classes constructor fields in a tuple. The tuple is wrapped in an Option.
```
val s = Student("Al", 1)
val t = Teacher("Fred", "Maths")

getPrintableString(s) // Al is a student in year 1
getPrintableString(t) // Fred teaches Maths
```

## Case Objects
A regular object in Scala is used when creating a singleton. These objects are useful for methods and values that are not associated with individual class instances.

The case object is similar, but has more features:
- Serializable
- Default hashCode implementation
- Improved toString implementation

Case objects are primarily used in two places (rather than regular objects):
- Enumerations
- Containers for 'messages' to be passed between objects

### Enumeration
```
sealed trait Topping
case object Cheese extends Topping
case object Pepperoni extends Topping
case object Sausage extends Topping
case object Mushrooms extends Topping
case object Onions extends Topping
```

### Messages
Case objects can be used to pass messages around:
```
case class StartSpeakingMessage(textToSpeak: String) // This message needs a param, so it is a case class
case object StopSpeakingMessage
case object PauseSpeakingMessage
case object ResumeSpeakingMessage
```

Using a framework like Akka, the receiver would look like:
```
class Speak extends Actor {
  def receive = {
    case StartSpeakingMessage(textToSpeak) =>
        // code to speak the text
    case StopSpeakingMessage =>
        // code to stop speaking
    case PauseSpeakingMessage =>
        // code to pause speaking
    case ResumeSpeakingMessage =>
        // code to resume speaking
  }
}
```

## Functional Error Handling
FP is like algebra, there are no null values or exceptions. Except there can be exceptions, so these need handling. We can use the Option/Some/None class trio, or Try/Success/Failure.

### Try/Success/Failure
Operates similarly to Option/Some/None, but:
- Try makes it easy to catch exceptions
- Failure contains the exception message

```
def toInt(s: String): Try[Int] = Try {
    Integer.parseInt(s.trim)
}

// can shorten further
def toInt(s: String): Try[Int] = Try(Integer.parseInt(s.trim))

val a = toInt("1") // Success(1)
val b = toInt("huh") // Failure(java.lang.NumberFormatException: For input string: "huh")
```

There are many ways to work with a Try's results, most common are match and for expressions.
```
toInt(x) match {
    case Success(i) => println(i)
    case Failure(s) => println(s"Failed. Reason: $s")
}

val y = for {
    a <- toInt(stringA)
    b <- toInt(stringB)
    c <- toInt(stringC)
} yield a + b + c
```

## Futures
Parallel and concurrent Scala applications could be made with the native Java 'Thread', but Scala's 'Future' makes parallel/concurrent programming simpler.

The official description from Scaladoc is:
- “A Future represents a value which may or may not currently be available, but will be available at some point, or an exception if that value could not be made available.”

### Thinking in Futures
Single-threaded programming requires binding the result of a function call to a variable:
```
def aShortTask(): Int = 42
val x = aShortTask() // 42 is immediately bound to x
```
Working with Futures means a task may take an undetermined amount of time to return a value.
```
def aLongTask(): Future[Int] = ???
val x = aLongTask()
```

A Future essentially is 'one-shot'; handle this computation on another thread, call me with the result when it's done.

### Future Example
Future's are used for temporary pockets of concurrency. They are usually used when an algorithm has an undetermined runtime, such as when calling a web service. This means it needs to run off of the main thread.
```
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

// Sleep for 10 seconds, then return 42
val a = Future { Thread.sleep(10*1000); 42 }

// map the response into another var
val b = a.map(_ * 2) // Future(<not completed>) or Future(Success(84))
```

The result of a future is a Success or Failure wrapped in a Future, with the result wrapped inside Success or the exception message inside Failure.

A common callback method is onComplete, which takes partial functions for handling the Success and Failure cases.
```
a.onComplete {
    case Success(value) => println(s"Callback got, value = $value")
    case Failure(e) => e.printStackTrace
```

### Future Methods
Futures have multiple common callback methods:
- onComplete
- onSuccess
- onFailure
- filter
- foreach
- map
- andThen
- fallbackTo
- recoverWith
