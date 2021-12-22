import java.lang.Integer.max

fun main() {

    val debug = false

    class SnakeNum(val str: String, var parent: SnakeNum?, var pointer: Int, var level: Int) {
        var leftChild: SnakeNum? = null
        var rightChild: SnakeNum? = null
        var value: Int = -1


        init {
            while (pointer < str.length) {
                if (str[pointer].isDigit()) {
                    value = str[pointer].toString().toInt()
                    pointer += 1
                    break
                }
                if (str[pointer] == '[') {
                    pointer += 1
                    leftChild = SnakeNum(str, this, pointer, level + 1)
                    pointer = leftChild!!.pointer
                }
                if (str[pointer] == ',') {
                    pointer += 1
                    rightChild = SnakeNum(str, this, pointer, level + 1)
                    pointer = rightChild!!.pointer
                }
                if (str[pointer] == ']') {
                    pointer += 1
                    value = -1
                    break
                }
            }
        }

        fun add(other: SnakeNum) {
            leftChild = this.shallowCopy()
            rightChild = other
            value = -1
            setLev(0)
        }

        fun shallowCopy(): SnakeNum {
            val tmp = SnakeNum(str, this, str.length, level)
            tmp.leftChild = leftChild
            tmp.rightChild = rightChild
            tmp.value = value
            return tmp
        }

        private fun setLev(l: Int) {
            level = l
            leftChild?.setLev(l + 1)
            rightChild?.setLev(l + 1)
            leftChild?.parent = this
            rightChild?.parent = this
        }

        fun isExplodable(): Boolean {
            if (level > 4) return true
            if (value != -1) return false
            return leftChild!!.isExplodable() && rightChild!!.isExplodable()
        }


        fun isReduced(): Boolean {
            if (level > 4) return false
            if (value != -1) return value < 10
            return leftChild!!.isReduced() && rightChild!!.isReduced()
        }

        fun isNumberPair(): Boolean {
            return value == -1 && leftChild!!.value != -1 && rightChild!!.value != -1
        }

        private fun addLeftUp(v: Int, source: SnakeNum) {
            if (leftChild !== source) {
                leftChild!!.addRightDown(v)
                return
            }
            parent?.addLeftUp(v, this)
        }

        private fun addRightUp(v: Int, source: SnakeNum) {

            if (rightChild !== source) {
                rightChild!!.addLeftDown(v)
                return
            }
            parent?.addRightUp(v, this)
        }

        private fun addRightDown(v: Int) {
            if (value != -1) {
                value += v
                return
            }
            rightChild!!.addRightDown(v)
        }

        private fun addLeftDown(v: Int) {
            if (value != -1) {
                value += v
                return
            }
            leftChild!!.addLeftDown(v)
        }

        private fun explode(): Boolean {
            if (value != -1) return false
            if (level >= 4 && isNumberPair()) {
                if (debug) println("Exploding: $this")
                value = 0
                if (parent != null) {
                    parent!!.addLeftUp(leftChild!!.value, this)
                    parent!!.addRightUp(rightChild!!.value, this)
                }
                leftChild = null
                rightChild = null
                return true
            }
            if (leftChild!!.explode()) {
                return true
            }
            return rightChild!!.explode()
        }

        private fun split(): Boolean {
            if (value != -1 && value < 10) return false
            if (value != -1 && value >= 10) {
                if (debug) println("Splitting: $this")
                var newV = value / 2
                leftChild = SnakeNum(newV.toString(), this, 99, level + 1)
                leftChild!!.value = newV
                newV += if (value % 2 == 0) 0 else 1
                rightChild = SnakeNum(newV.toString(), this, 99, level + 1)
                rightChild!!.value = newV
                value = -1
                return true
            }
            if (leftChild!!.split()) {
                return true
            }
            return rightChild!!.split()
        }

        fun reduce() {
            while (!isReduced()) {
                if (debug) println("Result: $this")
                if (explode()) {
                    continue
                }
                split()

            }
        }

        fun magnitude(): Int {
            if (value != -1) return value
            return leftChild!!.magnitude() * 3 + rightChild!!.magnitude() * 2
        }

        override fun toString(): String {
            return if (value == -1) "[$leftChild,$rightChild]" else value.toString()
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as SnakeNum

            if (pointer != other.pointer) return false
            if (value != other.value) return false

            return true
        }

        override fun hashCode(): Int {
            var result = pointer
            result = 31 * result + value
            return result
        }
    }


    fun part1(input: List<SnakeNum>): Int {
        val num = input.first()
        for (n in input.drop(1)) {
            num.add(n)
            num.reduce()
//            println(num)
        }
        return num.magnitude()
    }


    fun getSnailNum(i: String): SnakeNum = SnakeNum(i, null, 0, 0)
    fun part2(input: List<SnakeNum>): Int {

        var max = 0
        var id = 0
        for (a in input) {
            for (b in input) {
                id += 1
//                println(id)
                if (a == b) continue
                val aProxy = getSnailNum(a.str)
                val bProxy = getSnailNum(b.str)
                aProxy.add(bProxy)
                aProxy.reduce()
                val m = aProxy.magnitude()
                max = max(m,max)
            }
        }
        return max
    }


    fun preprocessing(input: String): List<SnakeNum> {
        return input.split("\n").map { getSnailNum(it) }
    }


//    val realInp = read_testInput("real15")
//    val testInp = read_testInput("test15")

    val testInp = ""
//    println("Test 1: ${part1(preprocessing(testInp))}")

//    println("Real 1: ${part1(preprocessing(realInp))}")
//    println("-----------")
    println("Test 2: ${part2(preprocessing(testInp))}")
//println("Real 2: ${part2(preprocessing(realInp))}")

}


