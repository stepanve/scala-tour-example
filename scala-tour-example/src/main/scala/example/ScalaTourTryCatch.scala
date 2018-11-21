package example

object ScalaTourTryCatch {
    def main(args: Array[String]): Unit = {

        def divid(x: Int, y: Int): Int = x / y
        def sthNotImplemented(x: Int): Int = ???

        // 式なので値を返せるが、finallyは帰り値を無関係
        val msg1 = try {
                "Hello" + " " + "World"
            } catch {
                case e: java.lang.ArithmeticException =>
                    s"Invalid arithmeitc (${e.getMessage})"
                case e: Throwable =>
                    "Unknown error" 
            } finally { println("completed") }

        try {
            println(divid(10, 0))
        } catch {
            case e: java.lang.ArithmeticException =>
                println(s"Invalid arithmeitc (${e.getMessage})")
            case e: Throwable =>
                println("Unknown error")
        }

        try {
            println(sthNotImplemented(10))
        } catch {
            case e: java.lang.ArithmeticException =>
                println(s"Invalid arithmeitc (${e.getMessage})")
            case e: Throwable =>
                println("Unknown error")
        } finally { println("completed") }
    }
}
