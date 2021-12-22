import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.streams.toList

fun main() {
    fun part1(input: List<Int>): Int {
        val pos = input.sorted()[input.size / 2]
        return input.sumOf { (it - pos).absoluteValue }
    }


    fun singleCost(from: Int, to: Int): Int {
        val x = (from-to).absoluteValue.toFloat()
        return (x + (x / 2.0 * (x-1))).roundToInt()
    }

    fun cost(l: List<Int>, to: Int): Int {
        return l.sumOf { singleCost(it, to) }
    }


    fun part2(input: List<Int>): Int {
        var pos = input.average().roundToInt()

        var dir = if (cost(input, pos) > cost(input, pos - 1)) {
            -1
        } else if (cost(input, pos) > cost(input, pos + 1)) {
            1
        } else {
            return cost(input, pos)
        }

        while (true) {
            pos += dir

            dir = if (cost(input, pos) > cost(input, pos - 1)) {
                -1
            } else if (cost(input, pos) > cost(input, pos + 1)) {
                1
            } else {
                return cost(input, pos)
            }
        }
    }

    fun preprocessing(input: String): List<Int> {
        return input.trim().split(",").stream().map { it.toInt() }.toList()
    }


    val realInp = read_testInput("real7")
    val testInp = "16,1,2,0,4,2,7,1,2,14"

//    println("Test 1: ${part1(preprocessing(testInp))}")
//    println("Real 1: ${part1(preprocessing(realInp))}")
//    println("-----------")
//    println("Test 2: ${part2(preprocessing(testInp))}")
    println("Real 2: ${part2(preprocessing(realInp))}")

}
