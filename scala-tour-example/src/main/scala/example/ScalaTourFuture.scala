package example

object ScalaTourFuture {
    def main(args: Array[String]): Unit = {

        import scala.concurrent.Future
        import scala.concurrent.ExecutionContext.Implicits.global
        import scala.util.{Success, Failure}

        val f1 = Future {
            Thread.sleep(1000)
            println("タスク１終了")
            4 / 2
        }

        val f2 = Future {
            Thread.sleep(3000)
            println("タスク2終了")
            // 2
            0 / 2
        }

        val f = f1.zip(f2)

        f.onComplete {
            case Success(res) => println(res._1 + res._2)
            case Failure(ex) => println(ex.getMessage)
        }

        // for {
        //     res1 <- f1
        //     res2 <- f2
        // } {
        //     println(res1 + res2)
        // }

        // println("コード的には、一番下だよ")
    }
}
