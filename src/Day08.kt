fun main() {

    fun decode(notes: List<String>): HashMap<Char, Char> {
        val segMap = HashMap<Char, Char>()
        val segCount = HashMap<Char, Int>()
        val decodeDigMap = HashMap<Int, String>()
        segCount['a'] = 0
        segCount['b'] = 0
        segCount['c'] = 0
        segCount['d'] = 0
        segCount['e'] = 0
        segCount['f'] = 0
        segCount['g'] = 0

        notes.sortedBy { it.length }.forEach {
            when (it.length) {
                2 -> decodeDigMap[1] = it
                3 -> decodeDigMap[7] = it
                4 -> decodeDigMap[4] = it
                7 -> decodeDigMap[8] = it
            }
            it.forEach { c -> segCount[c] = segCount[c]!! + 1 }
        }

        segMap['a'] = decodeDigMap[7]!!.filter { !decodeDigMap[1]!!.contains(it) }.chars().findAny().asInt.toChar()
        segMap['b'] = segCount.filterValues { it == 6 }.keys.first()
        segMap['c'] = segCount.filterValues { it == 8 }.filterKeys { it != segMap['a']!! }.keys.first()
        segMap['d'] = segCount.filterValues { it == 7 }.filterKeys { decodeDigMap[4]!!.contains(it) }.keys.first()
        segMap['e'] = segCount.filterValues { it == 4 }.keys.first()
        segMap['f'] = segCount.filterValues { it == 9 }.keys.first()
        segMap['g'] = segCount.filterValues { it == 7 }.filterKeys { it != segMap['d']!! }.keys.first()

        return segMap.entries.associate { (k, v) -> v to k } as HashMap<Char, Char>
    }

    fun part1(input: List<Pair<List<String>, List<String>>>): Int {
        return input.map { r ->
            val notes = r.first
            val inp = r.second


            val digMap = HashMap<String, Int>()
            digMap["abcefg"] = 0
            digMap["cf"] = 1
            digMap["acdeg"] = 2
            digMap["acdfg"] = 3
            digMap["bcdf"] = 4
            digMap["abdfg"] = 5
            digMap["abdefg"] = 6
            digMap["acf"] = 7
            digMap["abcdefg"] = 8
            digMap["abcdfg"] = 9

            val decodedSegMap = decode(notes)
            val tmp =
                inp.map { it.chars().toArray().map { c -> decodedSegMap[c.toChar()]!! }.sorted().joinToString("") }
                    .toList()
            tmp.map { digMap[it]!! }.count { listOf(1, 4, 7, 8).contains(it) }
        }.sum()

    }


    fun part2(input: List<Pair<List<String>, List<String>>>): Int {
        return input.map { r ->
            val notes = r.first
            val inp = r.second


            val digMap = HashMap<String, Int>()
            digMap["abcefg"] = 0
            digMap["cf"] = 1
            digMap["acdeg"] = 2
            digMap["acdfg"] = 3
            digMap["bcdf"] = 4
            digMap["abdfg"] = 5
            digMap["abdefg"] = 6
            digMap["acf"] = 7
            digMap["abcdefg"] = 8
            digMap["abcdfg"] = 9

            val decodedSegMap = decode(notes)
            val tmp = inp.map {
                it.chars().toArray().map { c -> decodedSegMap[c.toChar()]!! }.sorted().joinToString("")
            }//.toList()
                .map { digMap[it]!!.toString() }
            tmp.joinToString("").toInt()
        }.sum()
    }

    fun preprocessing(input: String): List<Pair<List<String>, List<String>>> {
        return input.trim().split("\r\n").map { r ->
            val tmp = r.trim().split(" | ").map { it.split(" ") }
            Pair(tmp[0], tmp[1])
        }.toList()
    }


    val realInp = read_testInput("real8")
    val testInp = read_testInput("test8")
//    val testInp = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"

//    println("Test 1: ${part1(preprocessing(testInp))}")
//    println("Real 1: ${part1(preprocessing(realInp))}")
//    println("-----------")
    println("Test 2: ${part2(preprocessing(testInp))}")
    println("Real 2: ${part2(preprocessing(realInp))}")

}
