import kotlin.streams.toList

fun main() {

    class Node(val cost: Int, val pos: Pair<Int, Int>) {
        var parent: Node? = null
        var gScore = Int.MAX_VALUE

        fun addParent(n: Node) {
            parent = n
            this.gScore = this.cost + n.gScore
        }

        fun getFScore(end: Node) = gScore + h(end)

        fun h(end: Node): Int {
            return end.pos.first - pos.first + end.pos.second - pos.second
        }

        fun potNeighbors(): List<Pair<Int, Int>> {
//            return (-1..1).flatMap {
//                (-1..1).filter { jt -> it != 0 && jt != 0 }.map { jt -> Pair(pos.first + it, pos.second + jt) }
//            }
            return listOf(
                Pair(pos.first - 1, pos.second),
                Pair(pos.first + 1, pos.second),
                Pair(pos.first, pos.second - 1),
                Pair(pos.first, pos.second + 1)
            )
        }

        override fun hashCode(): Int {
            return pos.hashCode()
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Node

            if (pos != other.pos) return false

            return true
        }

        override fun toString(): String {
            return cost.toString()
        }
    }

    fun printNodes(field: Array<Array<Node>>) {
        for (r in field) {
            for (n in r) {
                print(n)
            }
            println()
        }
    }

    fun part1(input: Array<Array<Node>>): Int {
        val openSet = HashSet<Node>()
        val closedSet = HashSet<Node>()
        input[0][0].gScore = 0
        openSet.add(input[0][0])
        val goal = input.last().last()
        while (openSet.isNotEmpty()) {
            val current = openSet.minByOrNull { it.getFScore(goal) }!!
            if (current == goal) return goal.gScore
            openSet.remove(current)
            closedSet.add(current)
            for (neiPos in current.potNeighbors()) {
                if (!input.isIndex(neiPos.first) || !input[neiPos.first].isIndex(neiPos.second)) continue
                val nei = input[neiPos.first][neiPos.second]
                if (closedSet.contains(nei)) continue
                if (current.gScore + nei.cost < nei.gScore) nei.addParent(current)
                if (!openSet.contains(nei)) openSet.add(nei)

            }
//            printNodes(openSet, input)
//            println("----------------------")
        }
        return -99
    }

    fun expandInput(input: Array<Array<Node>>): Array<Array<Node>> {
        val height = input.size
        val width = input[0].size
        return Array(height * 5) {
            Array(width * 5) { jt ->
                if (input.isIndex(it) && input[it].isIndex(jt)) input[it][jt]
                else Node(
                    (input[it % height][jt % width].cost + it / height + jt / width).let { if (it > 9) it % 10 + 1 else it },
                    Pair(it, jt)
                )
            }
        }
    }

    fun part2(input: Array<Array<Node>>): Int {
//        println(tmp.joinToString { it.reversed().toString() + "\n" })

        return part1(expandInput(input))
    }


    fun preprocessing(input: String): Array<Array<Node>> {
        val tmp =
            input.trim().split("\n").map { it.trim().chars().map { c -> c.toChar().digitToInt() }.toList() }.toList()
        return Array(tmp.size) {
            Array(tmp[it].size) { jt ->
                Node(tmp[it][jt], Pair(it, jt))
            }
        }
    }


    val realInp = read_testInput("real15")
    val testInp = read_testInput("test15")
//    val testInp = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"

//    printNodes(expandInput(arrayOf(arrayOf(Node(8, Pair(0, 0))))))
//    println("Test 1: ${part1(preprocessing(testInp))}")
//    println("Real 1: ${part1(preprocessing(realInp))}")
//    println("-----------")
//    println("Test 2: ${part2(preprocessing(testInp))}")
    println("Real 2: ${part2(preprocessing(realInp))}")

}


