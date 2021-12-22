import kotlin.reflect.KType
import kotlin.reflect.typeOf
import kotlin.streams.toList

fun main() {
    fun part1(input: List<Int>): Int {
        return input.stream().skip(1).toList().zip(input).count { it.component1() > it.component2() }
    }

    fun part2(input: List<Int>): Int {
        val summed = (2 until input.size).map { input.slice(it-2..it).sum() }
        return summed.stream().skip(1).toList().zip(summed).count { it.component1() > it.component2() }
    }

    fun preprocessing(input: List<String>): List<Int> {
        return input.stream().map { it.toInt() }.toList()
    }


    val realInp = request_Input(1)
    val testInp = read_testInput("test1")

//    println("Test 1: ${part1(preprocessing(testInp))}")
//    println("Real 1: ${part1(preprocessing(realInp))}")
//    println("-----------")
//    println("Test 2: ${part2(preprocessing(testInp))}")
//    println("Real 2: ${part2(preprocessing(realInp))}")

}
