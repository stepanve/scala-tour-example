package example

import org.scalatest._
import sbt.io.IO
import java.io.File
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization.{read, write}


class ScalaTourReFizzBuzzSpec extends FlatSpec {

    def createSourceJSONAndThenFizzBuzzFormJSON(n: Int): Unit = {

        implicit val formats = org.json4s.DefaultFormats
        val sourceFile = new File("sample.json")
        val destinationFile = new File("fizzBuzz.json")


        ScalaTourReFizzBuzz.createSourceJSON(n, sourceFile)
        ScalaTourReFizzBuzz.fizzBuzzFromJSON(sourceFile, destinationFile)

        val json = read[FizzBuzzHolder](IO.read(destinationFile))

        json.fizzBuzz.zipWithIndex.foreach(pair => {
            pair._2 + 1 match {
                case x if x % 15 == 0 => assert(pair._1 === "FizzBuzz")
                case x if x % 3 == 0  => assert(pair._1 === "Fizz")
                case x if x % 5 == 0  => assert(pair._1 === "Buzz")
                case x                => assert(pair._1 === x.toString) 
            }
        })
    }

    // 例外ケース
    s"'createSourceJSON' & 'fizzBuzzFromJSON' (1 to 0)" should
        "throw IllegalArgumentException" in {
     
            assertThrows[IllegalArgumentException] {
                createSourceJSONAndThenFizzBuzzFormJSON(0)
            }
    }

    // 成功ケース
    for { n <-Array(1, 15, 100) } {
        s"'createSourceJSON' & 'fizzBuzzFromJSON' (1 to $n)" should
            "apply FizzBuzz to data from JSON file" in {
                    createSourceJSONAndThenFizzBuzzFormJSON(n)
            }
    }

    // 成功しているが、実際には例外をなげてしまうケース
    for { n <-Array(1, 15, 100) } {
        s"'createSourceJSON' & 'fizzBuzzFromJSON' (1 to 0)" should
            "apply FizzBuzz to data from JSON file" in {
                    createSourceJSONAndThenFizzBuzzFormJSON(0)
            }
    }

}