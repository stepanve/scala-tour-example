package example

object ScalaTourType {
    def main(args: Array[String]): Unit = {

    val intBox = new Box[Int](10) // `box[Int]`
    println(intBox.get())

    intBox.set(0)
    println(intBox.get())


    val animalBox = new Box[Animal](Cat) // `Box[Animal]`
    println(animalBox.get())

    animalBox.set(Dog) // `Dogはanimalを継承しているので問題なし`
    println(animalBox.get())

    val intBox2 = new Box(10)
    val catBox = new Box(Cat)
    // catBox.set(Dog) // ERROR
  }
}

class Box[T](var element: T) {
    def get(): T = element
    def set(newElement: T): Unit = {
        element = newElement
    }
}

sealed abstract class Animal(val cry: String)
case object Cat extends Animal("にゃー")
case object Dog extends Animal("わんわん")
