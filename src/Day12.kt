import kotlin.streams.toList

fun main() {

    class Node(val name: String) {
        val neighbors = HashMap<String, Node>()

        fun addNeighbor(n: Node) {
            this.neighbors[n.name] = n
            n.neighbors[this.name] = this
        }

        fun isBig() = name[0].isUpperCase()

        override fun hashCode(): Int {
            return name.hashCode()
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Node

            if (name != other.name) return false

            return true
        }

        override fun toString(): String {
            return name
        }
    }

    fun explore(n: Node, visited: HashMap<Node, Int>, maxVisits: Int,twiceVisit: Boolean ): List<MutableList<Node>> {
        if (n.name == "end") return listOf(mutableListOf(n))
//        for (nei in n.neighbors.values){
//            if (visited.contains(nei) && !nei.isBig()) continue
//
//        }
        visited[n] = if (visited[n] == null) 1 else visited[n]!! + 1
        val newTwiceVisit = twiceVisit || (visited[n]!! > 1 && !n.isBig())
        return n.neighbors.values
            .filter {
                if (it.name == "start") return@filter false
                visited[it] == null || it.isBig() || (visited[it]!! == 1 && !newTwiceVisit)

            }
            .flatMap {
                explore(it, HashMap(visited), maxVisits, twiceVisit || newTwiceVisit)
            }
            .map {
                it.add(n)
                it
            }
    }

    fun part1(input: Node): Int {

        return explore(input, HashMap(), 1, false).count()

    }

    fun part2(input: Node): Int {
        val tmp = explore(input, HashMap(), 2, false)
//        println(tmp.joinToString { it.reversed().toString() + "\n" })
        return tmp.count()

    }


    fun preprocessing(input: String): Node {
        val nodes = HashMap<String, Node>()
        val tmp = input.trim().split("\n").map { it.trim().split("-") }
        for (l in tmp) {
            val from = l[0]
            val to = l[1]
            if (nodes[from] == null) nodes[from] = Node(from)
            if (nodes[to] == null) nodes[to] = Node(to)
            nodes[from]!!.addNeighbor(nodes[to]!!)
        }

        return nodes["start"]!!
    }


    val realInp = read_testInput("real12")
    val testInp = read_testInput("test12")
//    val testInp = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"

//    println("Test 1: ${part1(preprocessing(testInp))}")
//    println("Real 1: ${part1(preprocessing(realInp))}")
//    println("-----------")
    println("Test 2: ${part2(preprocessing(testInp))}")
    println("Real 2: ${part2(preprocessing(realInp))}")

}
