import java.io.File
import java.lang.Integer.max

fun main() {

    val debug = false


    fun getEnhanced(alg: String, img: HashMap<Pair<Int, Int>, Char>, round: Int, pos: Pair<Int, Int>): Char {
        val algIdx = pos.neighbors(center = true).map {
            if (img.getOrElse(pos) {
                    '.' //todo
                } == '#') '1' else '0'
        }.joinToString("").toInt(2)
        return ' '
    }

    fun part1(input: Pair<String, HashMap<Pair<Int, Int>, Char>>): Int {
        var (alg, img) = input
        for (round in 0 until 2) {
            val nextImg = HashMap<Pair<Int, Int>, Char>()
            val iRange = img.minOf { (k, _) -> k.first } - 1..img.maxOf { (k, _) -> k.first } + 1
            for (i in iRange) {
                val jRange = img.minOf { (k, _) -> k.second } - 1..img.maxOf { (k, _) -> k.second } + 1
                for (j in jRange) {
                    val pos = Pair(i, j)
                    nextImg[pos] = getEnhanced(alg, img, round, pos)
                }
            }
            img = nextImg
        }
        return img.count { it.value == '#' }
    }


    fun part2(input: List<String>): Int {

        return 0
    }


    fun preprocessing(input: String): Pair<String, HashMap<Pair<Int, Int>, Char>> {
        val (alg, imgString) = input.split("\r\n\r\n")
        val img = HashMap<Pair<Int, Int>, Char>()
        imgString.split("\r\n").forEachIndexed { i, s -> s.forEachIndexed { j, c -> img[Pair(i, j)] = c } }

        return Pair(alg, img)
    }


//    val realInp = read_testInput("real20")
    val testInp = read_testInput("test20")

//    val testInp = ""

    println("Test 1: ${part1(preprocessing(testInp))}")
//    println("Real 1: ${part1(preprocessing(realInp))}")
//    println("-----------")
//    println("Test 2: ${part2(preprocessing(testInp))}")
//    println("Real 2: ${part2(preprocessing(realInp))}")

}


