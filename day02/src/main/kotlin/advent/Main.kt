package advent

import java.io.File
import java.lang.IllegalStateException

fun main(args:Array<String>) {

    val input = File(ClassLoader.getSystemResource("input.txt").file).readLines()

    println(solvePart1(input))
    println(solvePart2(input))
}

fun solvePart1(input: List<String>): Int {

    val pair = input.fold(0 to 0) { acc, str ->

        val values = str.groupBy { it }.values

        val hasTwo = values.any { it.size == 2 }
        val hasThree = values.any { it.size == 3 }

        val first = acc.first + if (hasTwo) 1 else 0
        val second = acc.second + if (hasThree) 1 else 0

        first to second
    }

    return pair.first * pair.second
}

fun solvePart2(input: List<String>): String {
    input.forEach { first ->
        input.filter { it != first }.forEach { second ->

            val commonLetters = first.zip(second).fold("") { acc, pair ->
                acc + if (pair.first == pair.second) pair.first else ""
            }

            if (commonLetters.length == first.length - 1) {
                return commonLetters
            }
        }
    }
    throw IllegalStateException("did not find solution")
}
