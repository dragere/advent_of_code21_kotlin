import kotlin.streams.toList

fun main() {


    fun part1(input: Pair<String, Map<String, String>>): Int {
        val map = input.second
        var str = input.first

        for (round in 1..10) {
            var nextStr = ""
            for (i in str.indices) {
                nextStr += str[i]
                if (i == str.indices.last) {
                    continue
                }

                val k = str.slice(i..i + 1)
                if (map.containsKey(k)) {
                    nextStr += map[k]
                }
            }
            str = nextStr
        }

        val countmap = HashMap<Char, Int>()
        str.forEach {
            countmap[it] = if (countmap.containsKey(it)) countmap[it]!! + 1 else 1
        }

        return countmap.maxOf { it.value } - countmap.minOf { it.value }

    }

//    fun rLsys(str: String, rules: Map<String, String>, remainingIterations: Int): String {
//
//    }

    fun part2(input: Pair<String, Map<String, String>>): Long {
        val map = input.second

        val strRules = map.mapValues {
            var str = it.key[0] + it.value + it.key[1]
            for (round in 1..11) {
                var nextStr = ""

                for (i in str.indices) {
                    nextStr += str[i]
                    if (i == str.indices.last) {
                        continue
                    }

                    val k = str.slice(i..i + 1)
                    if (map.containsKey(k)) {
                        nextStr += map[k]
                    }
                }
//                println("Round $round")
                str = nextStr
            }
            str
        }

        val valRules = strRules.mapValues {
            val str = it.value
            val countmap = HashMap<Char, Long>()
            str.removeSuffix(str.last().toString()).forEach { c ->
                countmap[c] = if (countmap.containsKey(c)) countmap[c]!! + 1 else 1
            }
            countmap
        }

        val rules = strRules.mapValues {
            val str = it.value
            val totalMap = HashMap<Char, Long>()
            for (i in str.indices) {
                if (i == str.indices.last) {
                    continue
                }

                val k = str.slice(i..i + 1)
                valRules[k]!!.forEach { it1 ->
                    totalMap[it1.key] = totalMap.getOrDefault(it1.key, 0) + it1.value
                }
            }
            totalMap
        }

        var str = input.first
        for (round in 1..16) {
            var nextStr = ""

            for (i in str.indices) {
                nextStr += str[i]
                if (i == str.indices.last) {
                    continue
                }

                val k = str.slice(i..i + 1)
                if (map.containsKey(k)) {
                    nextStr += map[k]
                }
            }
//            println("Round $round")
            str = nextStr
        }

        val totalMap = HashMap<Char, Long>()

        for (i in str.indices) {
            if (i == str.indices.last) {
                totalMap[str[i]] = totalMap[str[i]]!! + 1
                continue
            }

            val k = str.slice(i..i + 1)
            rules[k]!!.forEach {
                totalMap[it.key] = totalMap.getOrDefault(it.key, 0) + it.value
            }
        }

        return totalMap.maxOf { it.value } - totalMap.minOf { it.value }

    }


    fun preprocessing(input: String): Pair<String, Map<String, String>> {


        val tmp = input.trim().split("\r\n\r\n")
        val t = tmp[0]
        val i = tmp[1].split("\r\n").associate {
            val i = it.split(" -> ")
            Pair(i[0], i[1])
        }
        return Pair(t, i)
    }


    val realInp = read_testInput("real14")
    val testInp = read_testInput("test14")
//    val testInp = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"

//    println("Test 1: ${part1(preprocessing(testInp))}")
//    println("Real 1: ${part1(preprocessing(realInp))}")
//    println("-----------")
    println("Test 2: ${part2(preprocessing(testInp))}")
    println("Want  : 2188189693529")
    println("Real 2: ${part2(preprocessing(realInp))}")
}
