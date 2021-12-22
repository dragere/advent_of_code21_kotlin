import java.io.File
import java.lang.Integer.max

fun main() {

    val debug = false

    class Dice {
        var last = -1
        fun getNext(): Int {
            last += 1
            return last % 100 +1
        }
    }

    fun part1(playerPositions: List<Int>): Int {
        val scores = mutableListOf(0, 0)
        val poses = playerPositions.map { it -  1 }.toMutableList()
        val d = Dice()
        var round = 0
        while(true){
            val player = round % 2
            val moveBy = d.getNext() + d.getNext() + d.getNext()
            poses[player] = (poses[player] + moveBy) % 10
            scores[player] += poses[player] + 1
            if (scores[player] >= 1000){
                return scores[player + 1 % 2] * (d.last + 1)
            }
            round += 1
        }

    }


    fun part2(input: List<String>): Int {

        return 0
    }


    fun preprocessing(input: String): List<Int> {
//        val (a, b) = input.split("\r\n\r\n").map { it.split(": ")[0].toInt() }
//        return a to b
        return input.trim().split("\r\n").map { it.split(": ")[1].toInt() }
    }


    val realInp = read_testInput("real21")
    val testInp = read_testInput("test21")

//    val testInp = ""

//    println("Test 1: ${part1(preprocessing(testInp))}")
    println("Real 1: ${part1(preprocessing(realInp))}")
//    println("-----------")
//    println("Test 2: ${part2(preprocessing(testInp))}")
//    println("Real 2: ${part2(preprocessing(realInp))}")

}


