import kotlin.reflect.KType
import kotlin.reflect.typeOf
import kotlin.streams.toList

fun main() {

    fun day(pop: HashMap<Int, Int>): HashMap<Int, Int> {
        val new = pop[0]!!
        for (i in 1..8) {
            pop[i - 1] = pop[i]!!
        }
        pop[6] = new + pop[6]!!
        pop[8] = new
        return pop
    }

    fun day(pop: HashMap<Int, Long>): HashMap<Int, Long> {
        val new = pop[0]!!
        for (i in 1..8) {
            pop[i - 1] = pop[i]!!
        }
        pop[6] = new + pop[6]!!
        pop[8] = new
        return pop
    }

    fun part1(input: List<Int>): Int {
        var pop = HashMap<Int, Int>()
        for (i in 0..8) {
            pop[i] = input.count { it == i }
        }

        for (d in 1..80) {
            pop = day(pop)
            println("Day $d: ${pop.map { it.value }.sum()}")
        }

        return pop.map { it.value }.sum()
    }

    fun part2(input: List<Int>): Long {
        var pop = HashMap<Int, Long>()
        for (i in 0..8) {
            pop[i] = input.count { it == i }.toLong()
        }

        for (d in 1..256) {
            pop = day(pop)
//            println("Day $d: ${pop.map { it.value }.sum()}")
        }

        return pop.map { it.value }.sum()
    }

    fun preprocessing(input: String): List<Int> {
        return input.split(",").stream().map { it.toInt() }.toList()
    }


    val realInp = request_Input(6)
    val testInp = "3,4,3,1,2"

//    println("Test 1: ${part1(preprocessing("3,4,3,1,2"))}")
//    println("Real 1: ${part1(preprocessing("2,5,2,3,5,3,5,5,4,2,1,5,5,5,5,1,2,5,1,1,1,1,1,5,5,1,5,4,3,3,1,2,4,2,4,5,4,5,5,5,4,4,1,3,5,1,2,2,4,2,1,1,2,1,1,4,2,1,2,1,2,1,3,3,3,5,1,1,1,3,4,4,1,3,1,5,5,1,5,3,1,5,2,2,2,2,1,1,1,1,3,3,3,1,4,3,5,3,5,5,1,4,4,2,5,1,5,5,4,5,5,1,5,4,4,1,3,4,1,2,3,2,5,1,3,1,5,5,2,2,2,1,3,3,1,1,1,4,2,5,1,2,4,4,2,5,1,1,3,5,4,2,1,2,5,4,1,5,5,2,4,3,5,2,4,1,4,3,5,5,3,1,5,1,3,5,1,1,1,4,2,4,4,1,1,1,1,1,3,4,5,2,3,4,5,1,4,1,2,3,4,2,1,4,4,2,1,5,3,4,1,1,2,2,1,5,5,2,5,1,4,4,2,1,3,1,5,5,1,4,2,2,1,1,1,5,1,3,4,1,3,3,5,3,5,5,3,1,4,4,1,1,1,3,3,2,3,1,1,1,5,4,2,5,3,5,4,4,5,2,3,2,5,2,1,1,1,2,1,5,3,5,1,4,1,2,1,5,3,5,2,1,3,1,2,4,5,3,4,3"))}")
//    println("-----------")
    println("Test 2: ${part2(preprocessing("3,4,3,1,2"))}")
    println("Real 2: ${part2(preprocessing(realInp))}")

}
