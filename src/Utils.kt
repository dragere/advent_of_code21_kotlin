import java.io.File
import java.math.BigInteger
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.security.MessageDigest
import kotlin.math.max
import kotlin.math.abs
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObject

fun starter(day: Int) {

    val realInp = request_Input(day)
    val testInp = read_testInput("test$day")


//
//    }catch (_:Exception){
//
//    }
}


/**
 * Reads lines from the given input txt file.
 */
fun read_testInput(name: String) = File("src", "$name.txt").readText()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun request_Input(day: Int): String {
    val client = HttpClient.newBuilder().build()
    val request = HttpRequest.newBuilder()
        .uri(URI.create("https://adventofcode.com/2021/day/$day/input"))
        .header("cookie", read_testInput("session")[0].toString())
        .build()

    val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    return response.body().trim()
}


fun Pair<Int, Int>.map(function: (Int) -> Int): Pair<Int, Int> {
    return Pair(function(this.first), function(this.second))

}

fun Pair<Int, Int>.normalize(): Pair<Int, Int> {
    val tmp = max(this.first, this.second)
    return Pair(this.first / tmp, this.second / tmp)

}

fun Pair<Int, Int>.total() = this.first + this.second

fun Pair<Int, Int>.abs() = abs(this.first) to abs(this.second)

fun Pair<Int, Int>.neighbors(diagonal: Boolean = true, center: Boolean = false): List<Pair<Int, Int>> {
    return (-1..1).flatMap { i ->
        (-1..1)
            .filter { !(!diagonal && abs(i) + abs(it) > 1) && !center && i == 0 && it == 0 }
            .map { j -> i to j }
    }
}


operator fun Pair<Int, Int>.minus(that: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(this.first - that.first, this.second - that.second)
}

operator fun Pair<Int, Int>.plus(that: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(this.first + that.first, this.second + that.second)
}

operator fun Pair<Int, Int>.times(that: Int): Pair<Int, Int> {
    return Pair(this.first * that, this.second * that)
}

operator fun Pair<Int, Int>.times(that: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(this.first * that.first, this.second * that.second)
}


fun <T> Array<T>.isIndex(i: Int) = i >= this.indices.first && i <= this.indices.last

fun parsePair(s: String, d: String): Pair<Int, Int> {
    val tmp = s.split(d).map { it.toInt() }
    return Pair(tmp[0], tmp[1])
}

val <A, B> Pair<A, B>.x: A
    get() {
        return this.first
    }
val <A, B> Pair<A, B>.y: B
    get() {
        return this.second
    }

