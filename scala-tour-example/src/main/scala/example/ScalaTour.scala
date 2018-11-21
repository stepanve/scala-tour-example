package example

object ScalaTour {
    def main(args: Array[String]): Unit = {

        // 静的メソッドとして利用できる
        object Polygon {
            // 与えられる`edges`の辺に応じて
            // 適切な多角形を生成する静的なファクトリメソッド
            // 返り血を `Option[Polygon]` 型に変更
            def fromEdges(edges: List[Int]): Either[String, Polygon] =
            edges.length match {
                case 3 => Triangle.fromEdges(edges)
                case x => Left(s"${x}個の辺からなる多角形は実装されていません")
            } 
        }

        object Triangle {
            // 辺の数だけではなく、図形が成立するかどうかもチェックするファクトリメソッド
            def fromEdges(edges: List[Int]): Either[String, Triangle] =
                if (edges.length !=3)
                    Left(s"${edges.length}個の辺からなる三角形は作成できません")
                else if (!(edges(0) + edges(1) > edges(2)
                    && edges(1) + edges(2) > edges(0)
                    && edges(2) + edges(0) > edges(1)))
                    Left(s"三角形は成立しない辺の組み合わせです")
                else
                    Right(new Triangle(edges)) 
        }

        // プライベートコンストラクタに変更することで、
        // インスタンス作成を `Triangle.fromEdges` 経由に制限
        class Triangle private (edges: List[Int]) extends Polygon(edges) {
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

        val edges3 = List(3, 4, 5)
        val polygon3 = Polygon.fromEdges(edges3);
        polygon3 match {
            case Right(p) => println(p.area)
            case Left(err) => println(err)
        }
        polygon3.foreach(p => println(p.area))

        polygon3
            .map(p => p.area * 2)
            .foreach(area => println(area))

        val invalidEdges3 = List(3, 4, 100)
        val invalidPolygon3 = Polygon.fromEdges(invalidEdges3);
        invalidPolygon3 match {
            case Right(p) => println(p.area)
            case Left(err) => println(err)
        }
        invalidPolygon3.foreach(p => println(p.area))
        invalidPolygon3
            .map(p => p.area * 2)
            .foreach(area => println(area))


        val invalidEdges2 = List(3, 4)
        val invalidPolygon2 = Polygon.fromEdges(invalidEdges2)

        invalidPolygon2 match {
            case Right(p) => println(p.area)
            case Left(err) => println(err)
        }

        // not display
        invalidPolygon2.foreach(p => println(p.area))

        // not display
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
