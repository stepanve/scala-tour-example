package example

import sbt.io.IO
import java.io.File
import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization.{read, write}

object ScalaTourReFizzBuzz {
    implicit val formats = org.json4s.DefaultFormats

    def main(args: Array[String]): Unit = {
        val sourceFile = new File("sample.json")
        val destinationFile = new File("fizzBuzz.json")

        // 元となるファイルを作成
        createSourceJSON(15, sourceFile)
        
        // ファイルを読み込んでFizzBuzzを実行
        fizzBuzzFromJSON(sourceFile, destinationFile)

        // Scastieではファイルを直接みることができないので、下で確認
        // println(IO.read(destinationFile)) 
    }

    def createSourceJSON(n: Int, srcFile: File): Unit = {
        require(n >= 1) // "n"は1以上とする

        val intArrayHolder = IntArrayHolder((1 to n).toArray)
        IO.write(srcFile, write(intArrayHolder))
    }

    def fizzBuzzFromJSON(srcFile: File, dstFile: File): Unit = {
        // 'sample.json'を読み込み
        val rawJson = IO.read(srcFile)
        val intArrayHolder = read[IntArrayHolder](rawJson)

        // JSON内の配列を元にFizzBuzzに変換
        val fizzBuzz = intArrayHolder.intArray.map(i => 
            i match {
                case x if x % 15 == 0 => "FizzBuzz"
                case x if x % 3 == 0  => "Fizz"
                case x if x % 5 == 0  => "Buzz"
                case x                => x.toString
            }
        )
        val fizzBuzzHolder = FizzBuzzHolder(fizzBuzz)

        // FizzBuzzの結果を `fizzBuzz.json` に書き出す
        IO.write(dstFile, write(fizzBuzzHolder))
    }
}
case class IntArrayHolder(intArray: Array[Int])
case class FizzBuzzHolder(fizzBuzz: Array[String])