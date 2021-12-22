import java.io.File
import java.lang.Integer.max

fun main() {

    val debug = false


    fun part1(input: List<String>): Int {
        for (scanner in input) {
            val sc = scanner.split("\r\n")
            val fileName = sc[0].replace(" ", "")
            val vertCount = sc.size - 1
            val myfile = File("C:\\Users\\h\\IdeaProjects\\advent_of_code21_kotlin\\src\\day19\\$fileName.ply")
            myfile.createNewFile()
            myfile.printWriter().use { out ->

                out.println(
                    "ply\n" +
                            "format ascii 1.0"
                )
                out.println("element vertex $vertCount")
                out.println(
                    "property float x\n" +
                            "property float y\n" +
                            "property float z\n" +
                            "element face 0"
                )
                out.println("property list uchar int vertex_index")
                out.print("end_header")

                for (vert in sc.drop(1)) {
                    out.print("\n" + vert.replace(",", " "))
                }

            }
        }
        return 0
    }


    fun part2(input: List<String>): Int {

        return 0
    }


    fun preprocessing(input: String): List<String> {
        return input.split("\r\n\r\n")
    }


//    val realInp = read_testInput("real19")
    val testInp = read_testInput("test19")

//    val testInp = ""

    println("Test 1: ${part1(preprocessing(testInp))}")
//    println("Real 1: ${part1(preprocessing(realInp))}")
//    println("-----------")
//    println("Test 2: ${part2(preprocessing(testInp))}")
//    println("Real 2: ${part2(preprocessing(realInp))}")

}


