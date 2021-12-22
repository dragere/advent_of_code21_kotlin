import kotlin.math.min
import kotlin.math.max


fun main() {

    val NEXTROW = -100
    val GOON = -101
    val FOUND = 100

    fun plt(trajectory: List<Pair<Int, Int>>, target: Pair<IntRange, IntRange>) {
        val minX = min(min(trajectory.minOf { it.x }, target.x.minOf { it }), 0)
        val minY = min(min(trajectory.minOf { it.y }, target.y.minOf { it }), 0)
        val maxX = max(max(trajectory.maxOf { it.x }, target.x.maxOf { it }), 0)
        val maxY = max(max(trajectory.maxOf { it.y }, target.y.maxOf { it }), 0)

        for (y in maxY downTo minY) {
            for (x in minX..maxX) {
                val pos = Pair(x, y)
                print(
                    if (trajectory.contains(pos)) "#"
                    else if (target.x.contains(x) && target.y.contains(y)) "T"
                    else if (pos == Pair(0, 0)) "S"
                    else "."
                )
            }
            println()
        }
    }

    fun traj(xVelo: Int, yVelo: Int, target: Pair<IntRange, IntRange>): List<Pair<Int, Int>> {
        var x = 0
        var y = 0
        var xVel = xVelo
        var yVel = yVelo
        val out = mutableListOf<Pair<Int, Int>>()
        while (true) {
            x += xVel
            y += yVel
            xVel += if (xVel > 0) -1 else if (xVel < 0) 1 else 0
            yVel -= 1

            out.add(Pair(x, y))
            if (x > target.x.last) return out
            if (target.y.contains(y) && x < target.x.first) return out
            if (target.y.first > y) return out
            if (target.y.contains(y) && target.x.contains(x)) break
        }
        return out
    }

    fun maxY(xVelo: Int, yVelo: Int, target: Pair<IntRange, IntRange>): Int {
        var x = 0
        var y = 0
        var xVel = xVelo
        var yVel = yVelo
        val ys = mutableListOf<Int>()
        while (true) {
            x += xVel
            y += yVel
            xVel += if (xVel > 0) -1 else if (xVel < 0) 1 else 0
            yVel -= 1

            ys.add(y)
            if (x > target.x.last) return -99
            if (target.y.contains(y) && x < target.x.first) return -101
            if (target.y.first > y) return -100
            if (target.y.contains(y) && target.x.contains(x)) break
        }
        return ys.maxOf { it }
    }

    fun maxY2(xVelo: Int, yVelo: Int, target: Pair<IntRange, IntRange>): Int {
        var x = 0
        var y = 0
        var xVel = xVelo
        var yVel = yVelo
        val out = mutableListOf<Pair<Int, Int>>()
        while (true) {
            x += xVel
            y += yVel
            xVel += if (xVel > 0) -1 else if (xVel < 0) 1 else 0
            yVel -= 1

            out.add(Pair(x, y))
            if (target.y.first > y || target.x.last < x) break
        }
        if (out.any { target.x.contains(it.x) && target.y.contains(it.y) }) return FOUND
        if (out.size < 2) return NEXTROW
        val pre = out[out.size - 2]
        if (pre.x < target.x.first) return GOON
        if (pre.y > target.y.last) return NEXTROW
        return -1
    }

    fun part1(input: Pair<IntRange, IntRange>): Int {
        var last = -101
        var lastLast = 0
        var vx = 22
        var lastVx = 0
//        var lastVy = 0
        var vy = 70

//        plt(traj(22, 56, input), input)
//        return 0

        while (last != -100) {
            println("vx: $vx vy: $vy")
            lastLast = if (last > 0) {
                println("--> $last")
                last
            } else {
                lastLast
            }
            last = maxY(vx, vy, input)
            when (last) {
                -101 -> {
                    if (lastVx == vx + 1) {
                        vy += 1
                        lastVx = 0
                        continue
                    }
                    lastVx = vx
                    vx += 1
                }
                -99 -> {
                    if (lastVx == vx - 1) {
                        vy += 1
                        lastVx = 0
                        continue
                    }
                    lastVx = vx
                    vx -= 1
                }
                else -> {
                    vy += 1

                    lastVx = 0
                }
            }
        }
        return lastLast

    }


    fun part2(input: Pair<IntRange, IntRange>): Int {
        var last = -101
        var vx = 1
        var vy = input.y.first
        var count = 0
        val print = false
        if (print) {
            plt(traj(8, -2, input), input)
            return 0
        }
        while (last != -100 || vy < 170) {
            last = maxY2(vx, vy, input)
            when (last) {
                GOON -> {
                    vx += 1
                }
                FOUND -> {

                    count += 1
//                    println("$vx,$vy")
                    vx += 1
                }
                else -> {
                    vy += 1
                    vx = 1
                }
            }
        }
        return count

    }

//
//    fun preprocessing(input: String): Array<Array<Node>> {
//        val tmp =
//            input.trim().split("\n").map { it.trim().chars().map { c -> c.toChar().digitToInt() }.toList() }.toList()
//        return Array(tmp.size) {
//            Array(tmp[it].size) { jt ->
//                Node(tmp[it][jt], Pair(it, jt))
//            }
//        }
//    }


    val realInp = read_testInput("real15")
    val testInp = read_testInput("test15")
//    val testInp = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"


//    printNodes(expandInput(arrayOf(arrayOf(Node(8, Pair(0, 0))))))
//    println("Test 1: ${part1(Pair(207..263, -115..-63))}")
//    println("Test 1: ${part1(Pair(20..30, -10..-5))}")
//    println("Real 1: ${part1(preprocessing(realInp))}")
//    println("-----------")
//    println("Test 2: ${part2(Pair(20..30, -10..-5))}")
    println("Real 2: ${part2(Pair(207..263, -115..-63))}")

}


