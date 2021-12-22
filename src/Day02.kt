import kotlin.reflect.KType
import kotlin.reflect.typeOf
import kotlin.streams.toList

fun main() {
    fun part1(input: List<Pair<String, Int>>): Int {
        var h = 0
        var d = 0
        for (c in input){
            when (c.first){
                "forward" -> h += c.second
                "down" -> d += c.second
                "up" -> d -= c.second
            }
        }
        return h*d
    }

    fun part2(input: List<Pair<String, Int>>): Int {
        var h = 0
        var d = 0
        var a = 0
        for (c in input){
            when (c.first){
                "forward" -> {
                    h += c.second
                    d += c.second*a
                }
                "down" -> a += c.second
                "up" -> a -= c.second
            }
        }
        return h*d
    }

    fun preprocessing(input: List<String>): List<Pair<String, Int>> {
        return input.map {
                val tmp = it.split(" ")
                Pair(tmp[0], tmp[1].toInt())
        }.toList()
    }


    val realInp = request_Input(2)
    val testInp = read_testInput("test2")

//    println("Test 1: ${part1(preprocessing(testInp))}")
//    println("Real 1: ${part1(preprocessing(realInp))}")
//    println("-----------")
//    println("Test 2: ${part2(preprocessing(testInp))}")
//    println("Real 2: ${part2(preprocessing(realInp))}")

}
