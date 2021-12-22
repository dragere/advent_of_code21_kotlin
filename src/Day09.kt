import kotlin.streams.toList

fun main() {

    fun part1(input: List<List<Int>>): Int {
        val lowPoints = mutableListOf<Int>()
        for (i in input.indices) {
            for (j in input[i].indices) {
                val v = input[i][j]
                if (j > 0 && input[i][j - 1] <= v) continue
                if (j < input[i].size - 1 && input[i][j + 1] <= v) continue
                if (i > 0 && input[i - 1][j] <= v) continue
                if (i < input.size - 1 && input[i + 1][j] <= v) continue
                lowPoints.add(v)
            }
        }

        return lowPoints.sumOf { it + 1 }

    }

    fun getAdjecent(p: Pair<Int, Int>): List<Pair<Int, Int>> {
        return listOf(
            Pair(p.first, p.second - 1),
            Pair(p.first, p.second + 1),
            Pair(p.first - 1, p.second),
            Pair(p.first + 1, p.second)
        )
    }

    fun explore(
        field: List<List<Int>>,
        low: Pair<Int, Int>,
        exploredPoints: MutableSet<Pair<Int, Int>>
    ): List<Pair<Int, Int>> {
        var tmp = getAdjecent(low)
            .filter { !exploredPoints.contains(it) }
            .filter { it.first >= 0 && it.first < field.size }
            .filter { it.second >= 0 && it.second < field[0].size }
            .filter { field[it.first][it.second] != 9 }
        exploredPoints.addAll(tmp)
        tmp = tmp.flatMap { explore(field, it, exploredPoints) }
        val out = mutableListOf(low)
        out.addAll(tmp)
        return out
    }


    fun part2(input: List<List<Int>>): Int {
        val exploredPoints = mutableSetOf<Pair<Int, Int>>()
        val out = mutableListOf<Int>()
        for (i in input.indices) {
            for (j in input[i].indices) {
                val point = Pair(i, j)
                if (exploredPoints.contains(point)) continue
                val v = input[i][j]
                if (j > 0 && input[i][j - 1] <= v) continue
                if (j < input[i].size - 1 && input[i][j + 1] <= v) continue
                if (i > 0 && input[i - 1][j] <= v) continue
                if (i < input.size - 1 && input[i + 1][j] <= v) continue
                exploredPoints.add(point)
                val tmp = explore(input, point, exploredPoints)
                out.add(tmp.size)
            }
        }
        return out.sortedDescending().subList(0, 3).foldRight(1) { i, acc -> acc * i }
    }


    fun preprocessing(input: String): List<List<Int>> {
        return input.trim().split("\n").map { it.trim().chars().map { c -> c.toChar().digitToInt() }.toList() }.toList()
    }


    val realInp = read_testInput("real9")
    val testInp = read_testInput("test9")
//    val testInp = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"

//    println("Test 1: ${part1(preprocessing(testInp))}")
//    println("Real 1: ${part1(preprocessing(realInp))}")
//    println("-----------")
    println("Test 2: ${part2(preprocessing(testInp))}")
    println("Real 2: ${part2(preprocessing(realInp))}")

}
