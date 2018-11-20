package example

object ScalaTourFizzBuzz {
    def main(args: Array[String]): Unit = {

    def toFizzBuzz(numbers: List[Int]): List[String] = {
        // numbers.map((i: Int) => i match {
        //    case x if x % 15 == 0 => "FizzBuzz"
        //    case x if x % 5 == 0 => "Fizz"
        //    case x if x % 3 == 0 => "Buzz"
        //    case x => x.toString
        val f: Int => String = (i: Int) => i match {
            case x if x % 15 == 0 => "FizzBuzz"
            case x if x % 5 == 0 => "Fizz"
            case x if x % 3 == 0 => "Buzz"
            case x => x.toString
        }
        numbers.map(f)
    }


    val n = 15;
    val numbers = (1 to n).toList
    val fizzBuzzList = toFizzBuzz(numbers)
    fizzBuzzList.foreach((s: String) => println(s))
  }
}