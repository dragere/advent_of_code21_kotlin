import kotlin.streams.toList

fun main() {

    fun fold(vec: Pair<Int, Int>, folder: Pair<Int, Int>): Pair<Int, Int> {
        var tmp = vec * folder.normalize()
        if (tmp.total() < folder.total()) return vec
        tmp = folder - (vec - folder)
        return if (folder.first == 0) {
            Pair(vec.first, tmp.second)
        } else {
            Pair(tmp.first, vec.second)
        }
    }

    fun part1(input: Pair<List<Pair<Int, Int>>, List<Pair<Int, Int>>>): Int {
        var points = HashSet(input.first)

        val f = input.second[0]
        val nextPoints = HashSet<Pair<Int, Int>>()
        points.forEach { nextPoints.add(fold(it, f)) }
        points = nextPoints

        return points.size

    }

    fun part2(input: Pair<List<Pair<Int, Int>>, List<Pair<Int, Int>>>): Int {
        var points = HashSet(input.first)

        for (f in input.second) {
            val nextPoints = HashSet<Pair<Int, Int>>()
            points.forEach { nextPoints.add(fold(it, f)) }
            points = nextPoints
        }

        val maxX = points.maxOf { it.first }
        val maxY = points.maxOf { it.second }

        for (x in 0..maxY) {


            println((0..maxX).map { if (points.contains(Pair(it, x))) "##" else "  " }.joinToString("") { it })
        }

        return 0

    }


    fun preprocessing(input: String): Pair<List<Pair<Int, Int>>, List<Pair<Int, Int>>> {


        val tmp = input.trim().split("\r\n\r\n")
        val l = tmp[0].split("\r\n").map { parsePair(it.trim(), ",") }
        val f = tmp[1].split("\r\n").map {
            val i = it.split("=")
            when (i[0].last()) {
                'x' -> Pair(i[1].toInt(), 0)
                'y' -> Pair(0, i[1].toInt())
                else -> Pair(0, 0)
            }
        }
        return Pair(l, f)
    }


    val realInp = read_testInput("real13")
    val testInp = read_testInput("test13")
//    val testInp = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"

//    println(fold(Pair(2, 2), Pair(2, 0)))
//    println("Test 1: ${part1(preprocessing(testInp))}")
//    println("Real 1: ${part1(preprocessing(realInp))}")
//    println("-----------")
//    println("Test 2: ${part2(preprocessing(testInp))}")
    println("Real 2: ${part2(preprocessing(realInp))}")
}
