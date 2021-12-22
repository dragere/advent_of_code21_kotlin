import java.util.regex.Pattern

class Bingo(fie: String, num: String, var marked: Array<IntArray> = Array(5) { IntArray(5) { 0 } }) {

    val field: List<List<Int>>
    val numbers: List<Int>
    var counter = 0

    init {
        numbers = num.split(",").map { it.toInt() }
        field = fie.split("\r\n").map { it.trim().split(Pattern.compile(" +")).map { s -> s.toInt() } }
    }

    fun next(): Bingo {
        print("")
        for (i in 0 until 5) {
            for (j in 0 until 5) {
                if (field[i][j] == numbers[counter]) {
                    marked[i][j] = 1
                }
            }
        }
        counter++
        return this
    }

    fun check(): Boolean {
        marked.forEachIndexed { i, r ->
            if (r.sum() == 5) {
                return true
            }
            if (this.marked.map { it[i] }.sum() == 5) {
                return true
            }
        }
        return false
    }

    fun get_winning_sum() = field.mapIndexed { i, r -> r.filterIndexed { j, _ -> marked[i][j] == 0 } }
        .map { it.sum() }.sum() * numbers[counter - 1]


    override fun toString(): String {
        var o = ""
        field.forEachIndexed { i, r ->
            r.forEachIndexed { j, n ->
                o += if (marked[i][j] == 1) {
                    ("   ")
                } else if (n < 9) {
                    ("  $n")
                } else {
                    (" $n")
                }
            }
            o += ('\n')
        }
        return o
    }
}

fun main() {
    fun part1(input: List<Bingo>): Int {
        while (true) {
            for (it in input) {
                if (it.next().check()) {
                    return it.get_winning_sum()
                }
            }
        }
    }

    fun part2(input: List<Bingo>): Int {
        val mutIn = input.toMutableList()
        while (mutIn.size > 0) {
            val toRemove = mutableListOf<Bingo>()
            for (i in mutIn.indices) {
                if (mutIn[i].next().check()) {
                    toRemove.add(mutIn[i])
                }
            }
            mutIn.removeAll(toRemove)

            if (mutIn.size == 0) {
                return toRemove[0].get_winning_sum()
            }
        }
        return 0
//        println("Winner:\n${winner}")
//        return winner!!.get_winning_sum()
    }

    fun preprocessing(input: String): List<Bingo> {
        val (numbers, fields) = input.split(Pattern.compile("\r\n"), 2)
        return fields.trim().split("\r\n\r\n").map { Bingo(it, numbers.trim()) }
    }


    val realInp = read_testInput("real4")
    val testInp = read_testInput("test4")

//        println("Test 1: ${part1(preprocessing(testInp))}")
//        println("Real 1: ${part1(preprocessing(realInp))}")
//        println("-----------")
    println("Test 2: ${part2(preprocessing(testInp))}")
    println("Real 2: ${part2(preprocessing(realInp))}")

}
