package example

object ScalaTour {
    def main(args: Array[String]): Unit = {

    // 静的メソッドとして利用できる
    object Polygon {
        // 与えられる`edges`の辺に応じて
        // 適切な多角形を生成する静的なファクトリメソッド
        // 返り血を `Option[Polygon]` 型に変更
        def fromEdges(edges: List[Int]): Option[Polygon] =
        edges.length match {
            case 3 =>
                // 三角形は実装されているので `Some[Polygon]` で返す
                Some(new Triangle(edges))
            case x =>
                None
        } 
    }

    val edges3 = List(3, 4, 5)
    val polygon3 = Polygon.fromEdges(edges3);
    polygon3 match {
        case Some(p) => println(p.area)
        case None =>
            println("不正な辺が与えられたため面積は出力できません")
    }
    polygon3.foreach(p => println(p.area))

    polygon3
        .map(p => p.area * 2)
        .foreach(area => println(area))


    val invalidEdges2 = List(3, 4)
    val invalidPolygon2 = Polygon.fromEdges(invalidEdges2)

    invalidPolygon2 match {
        case Some(p) => println(p.area)
        case None =>
            println("不正な辺が与えられたため面積は出力できません")
    }

    invalidPolygon2.foreach(p => println(p.area))

    invalidPolygon2
        .map(p => p.area * 2)
        .foreach(area => println(area))
  }
}

abstract class Polygon(edges: List[Int]) {
    val n = edges.length // n角形
    val area: Double // 面積
}

// 色と透明度の両方をミックスインしたいので、抽象クラスではなくトレイトを選択
trait Color {
    val red: Int
    val green: Int 
    val blue: Int

    // 実装を持つことができる
    def printColor(): Unit = println(s"$red-$green-$blue")
}

trait Blue extends Color {
    override val red = 0
    override val green = 0
    override val blue = 255
}

trait Yellow extends Color {
    override val red = 255
    override val green = 255
    override val blue = 0
}

trait Transparency {
    val alpha: Double
}

trait Frosted extends Transparency {
    override val alpha = 0.5
}

// クラス宣言時に青を表す `Blue`トレイトと
// 半透明を表す `Frosted` トレイトをミックスイン
class BlueFrostedTraiangle(edges: List[Int]) 
    extends Polygon(edges) with Blue with Frosted {
    val a = edges(0)
    val b = edges(1)
    val c = edges(2)

    val area = {
        // Hero's formula
        val s = (a + b + c) / 2.0
        math.sqrt(s * (s - a) * (a - b) * (s - c))
    }
}


class Triangle(edges: List[Int]) extends Polygon(edges) {
    // 与えられた辺から三角形が成立すると勝手に仮定
    val a = edges(0)
    val b = edges(1)
    val c = edges(2)

    val area = {
        // Heron's formula
        val s = (a + b + c) / 2.0
        math.sqrt(s * (s - a) * (s - b) * (s - c))
    }
}