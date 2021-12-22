fun main() {

    class Packet(val s: String) {

        val version: Int = s.substring(0, 3).toInt(2)
        val type: Int = s.substring(3, 6).toInt(2)

        val subPackets: List<Packet>
        val value: Long
        val leftOver: String

        init {
            if (type != 4) {
                value = 0
                val lengthType = if (s[6] == '0') 22 else 18
                var leng = s.substring(7, lengthType).toInt(2)
                if (lengthType == 22) {
                    leng += lengthType
                    leftOver = s.substring(leng)
                    var subStr = s.substring(lengthType, leng)
                    val tmp = mutableListOf<Packet>()
                    while (subStr.isNotEmpty()) {
                        tmp.add(Packet(subStr))
                        subStr = tmp.last().leftOver
                    }
                    subPackets = tmp.toList()
                } else {
                    var subStr = s.substring(lengthType)
                    val tmp = mutableListOf<Packet>()
                    var i = 0
                    while (i < leng) {
                        tmp.add(Packet(subStr))
                        subStr = tmp.last().leftOver
                        i++
                    }
                    leftOver = subStr
                    subPackets = tmp.toList()

                }

            } else {
                subPackets = listOf()
                var o = ""
                var i = 6
                while (true) {
                    o += s.substring(i + 1, i + 5)
                    i += 5
                    if (s[i - 5] == '0') break
                }
                value = o.toLong(2)
//                i -= 6
//                leftOver = s.substring((if (i % 4 == 0) i else 4 - i % 4 + i)+6)
                leftOver = s.substring(i)
            }
        }

        fun versionSum(): Int = version + subPackets.sumOf { it.versionSum() }

        fun calcVal(): Long {
            return when (type) {
                0 -> subPackets.sumOf { it.calcVal() }
                1 -> subPackets.map { it.calcVal() }.reduce { acc, i -> acc * i }
                2 -> subPackets.minOf { it.calcVal() }
                3 -> subPackets.maxOf { it.calcVal() }
                4 -> value
                5 -> if (subPackets[0].calcVal() > subPackets[1].calcVal()) 1 else 0
                6 -> if (subPackets[0].calcVal() < subPackets[1].calcVal()) 1 else 0
                7 -> if (subPackets[0].calcVal() == subPackets[1].calcVal()) 1 else 0
                else -> 0
            }
        }

        override fun hashCode(): Int {
            return s.hashCode()
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Packet

            if (s != other.s) return false

            return true
        }

        override fun toString(): String {
            return "P<V$version,T$type>(${if (type != 4) subPackets.toString() else value})"
        }
    }


    fun part1(input: Packet): Int {

        return input.versionSum()
    }


    fun part2(input: Packet): Long {
//        println(tmp.joinToString { it.reversed().toString() + "\n" })

        return input.calcVal()
    }


    fun preprocessing(input: String): Packet {
        return Packet(input.map {
            when (it) {
                '0' -> "0000"
                '1' -> "0001"
                '2' -> "0010"
                '3' -> "0011"
                '4' -> "0100"
                '5' -> "0101"
                '6' -> "0110"
                '7' -> "0111"
                '8' -> "1000"
                '9' -> "1001"
                'A' -> "1010"
                'B' -> "1011"
                'C' -> "1100"
                'D' -> "1101"
                'E' -> "1110"
                'F' -> "1111"
                else -> ""
            }
        }.joinToString(""))

    }


    val realInp = read_testInput("real16")
//    val testInp = read_testInput("test16")
    val testInp = "880086C3E88112"

//    println(preprocessing(testInp))
//    println("Test 1: ${part1(preprocessing(testInp))}")
//    println("Real 1: ${part1(preprocessing(realInp))}")
//    println("-----------")
    println("Test 2: ${part2(preprocessing(testInp))}")
    println("Real 2: ${part2(preprocessing(realInp))}")

}


