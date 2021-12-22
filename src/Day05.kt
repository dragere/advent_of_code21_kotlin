import kotlin.math.absoluteValue
import kotlin.reflect.KType
import kotlin.reflect.typeOf
import kotlin.streams.toList

class Line2D constructor(points: List<List<Int>>) {
    val support: Pair<Int, Int>
    val direction: Pair<Int, Int>

    init {
        support = Pair(points[0][0], points[0][1])
        val tmp = Pair(points[1][0], points[1][1])
        direction = tmp - support
    }

    fun getPoints(part: Int): List<Pair<Int, Int>> {
        if (part == 1 && direction.first != 0 && direction.second != 0) {
            return listOf()
        }
        val maxD = direction.toList().maxOf { i -> i.absoluteValue }
        val ndir = direction.map { it / maxD }
        return (0..maxD).map { support + ndir * it }

    }
}

fun main() {
    fun part1(input: List<Line2D>): Int {
        val points = HashMap<Pair<Int, Int>, Int>()
        for (l in input) {
            for (ps in l.getPoints(1)) {
                val v = if (points[ps] == null) 1 else points[ps]?.plus(1)!!
                points[ps] = v
            }
        }
        return points.count { it.value > 1 }
    }

    fun part2(input: List<Line2D>): Int {
        val points = HashMap<Pair<Int, Int>, Int>()
        for (l in input) {
            for (ps in l.getPoints(2)) {
                val v = if (points[ps] == null) 1 else points[ps]?.plus(1)!!
                points[ps] = v
            }
        }
        return points.count { it.value > 1 }
    }


    fun preprocessing(input: String): List<Line2D> {
        return input.trim().split("\n").map {
            val tmp = it.trim().split(" -> ").map { s -> s.split(",").map { s1 -> s1.toInt() } }
            Line2D(tmp)
        }.toList()
    }


    val realInp = read_testInput("real5")
    val testInp = read_testInput("test5")

//    println("Test 1: ${part1(preprocessing(testInp))}")
//    println("Real 1: ${part1(preprocessing(realInp))}")
//    println("-----------")
    println("Test 2: ${part2(preprocessing(testInp))}")
    println("Real 2: ${part2(preprocessing(realInp))}")

}
