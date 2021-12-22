import kotlin.streams.toList

fun main() {

    fun flash(field: MutableList<MutableList<Int>>, flashed: Array<Array<Boolean>>, pos: Pair<Int, Int>) {
        val i = pos.first
        val j = pos.second
        if (field[i][j] <= 9 || flashed[i][j]) return
        flashed[i][j] = true
        for (m in -1..1) {
            for (n in -1..1) {
                if (m == 0 && n == 0) continue
                val ni = i + m
                val nj = j + n
                if (ni < 0 || ni >= field.size) continue
                if (nj < 0 || nj >= field[ni].size) continue

                field[ni][nj] += 1
                if (field[ni][nj] > 9) flash(field, flashed, Pair(ni, nj))
            }
        }
    }

    fun part1(input: List<List<Int>>): Int {
        var l = input//.map { it.toMutableList() }.toMutableList()
        var out = 0
        for (step in 1..100) {

            val flashed = Array(10) { Array(10) { false } }
            l = l.map { it.map { p -> p + 1 }.toMutableList() }.toMutableList()
            for (i in input.indices) {
                for (j in input.indices) {
                    flash(l, flashed, Pair(i, j))

                }
            }
            out += flashed.sumOf { it.count { b -> b } }
            l = l.map { it.map { p -> if (p > 9) 0 else p }.toMutableList() }.toMutableList()
//            println("Step $step:")
//            println(flashed.joinToString { booleans -> booleans.joinToString { b -> if (b) "#" else "." } + "\n" })
        }

        return out

    }

    fun part2(input: List<List<Int>>): Int {
        var l = input.map { it.toMutableList() }.toMutableList()
        for (step in 1..Int.MAX_VALUE) {

            val flashed = Array(10) { Array(10) { false } }
            l = l.map { it.map { p -> p + 1 }.toMutableList() }.toMutableList()
            for (i in input.indices) {
                for (j in input.indices) {
                    flash(l, flashed, Pair(i, j))

                }
            }
            l = l.map { it.map { p -> if (p > 9) 0 else p }.toMutableList() }.toMutableList()
            if (flashed.sumOf { it.count { b -> b } } == 100) return step
//            println("Step $step:")
//            println(flashed.joinToString { booleans -> booleans.joinToString { b -> if (b) "#" else "." } + "\n" })
        }
        return 0

    }


    fun preprocessing(input: String): List<List<Int>> {
        return input.trim().split("\n").map { it.trim().toCharArray().map { c -> c.digitToInt() } }.toList()
    }


    val realInp = read_testInput("real11")
    val testInp = read_testInput("test11")
//    val testInp = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"

//    println("Test 1: ${part1(preprocessing(testInp))}")
//    println("Real 1: ${part1(preprocessing(realInp))}")
    println("-----------")
    println("Test 2: ${part2(preprocessing(testInp))}")
    println("Real 2: ${part2(preprocessing(realInp))}")

}
