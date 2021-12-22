import kotlin.streams.toList

fun main() {

    fun part1(input: List<List<Char>>): Int {
        val stk = ArrayDeque<Char>()
        val open = listOf('(', '[', '{', '<')
        val close = listOf(')', ']', '}', '>')
        val scores = mapOf(
            Pair(')', 3),
            Pair(']', 57),
            Pair('}', 1197),
            Pair('>', 25137),
        )
        var out = 0

        for (l in input) {
            for (c in l) {
                if (open.contains(c)){
                    stk.add(c)
                } else {
                    if (open.indexOf(stk.removeLast()) == close.indexOf(c)) continue
                    out += scores[c]!!
                    break
                }
            }

            stk.clear()
        }

        return out

    }

    fun part2(input: List<List<Char>>): Long {
        val open = listOf('(', '[', '{', '<')
        val close = listOf(')', ']', '}', '>')
        val scores = mapOf(
            Pair('(', 1),
            Pair('[', 2),
            Pair('{', 3),
            Pair('<', 4),
        )

        val results = mutableListOf<Long>()

        for (l in input) {

            var out: Long = 0
            val stk = ArrayDeque<Char>()
            var broken = false
            for (c in l) {
                if (open.contains(c)){
                    stk.add(c)
                } else {
                    if (open.indexOf(stk.removeLast()) == close.indexOf(c)) continue
                    broken = true
                    break
                }
            }
            if (broken) continue
            for (c in stk.reversed()){
                out *= 5
                out += scores[c]!!
            }
            results.add(out)
        }

        return results.sorted()[results.size/2]
    }


    fun preprocessing(input: String): List<List<Char>> {
        return input.trim().split("\n").map { it.trim().toCharArray().toList() }.toList()
    }


    val realInp = read_testInput("real10")
    val testInp = read_testInput("test10")
//    val testInp = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"

//    println("Test 1: ${part1(preprocessing(testInp))}")
//    println("Real 1: ${part1(preprocessing(realInp))}")
    println("-----------")
    println("Test 2: ${part2(preprocessing(testInp))}")
    println("Real 2: ${part2(preprocessing(realInp))}")

}
