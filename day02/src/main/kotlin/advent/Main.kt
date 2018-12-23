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

        val hasTwo = hasExactlyCountOfAnyLetter(str, 2)
        val hasThree = hasExactlyCountOfAnyLetter(str, 3)

        val first = if (hasTwo) acc.first + 1 else acc.first
        val second = if (hasThree) acc.second + 1 else acc.second

        first to second
    }

    return pair.first * pair.second
}

fun solvePart2(input: List<String>): String {
    input.forEach { first ->
        input.filter { it != first }.forEach { second ->
            val commonLetters = commonLetters(first, second)
            if (commonLetters.length == first.length - 1) {
                return commonLetters
            }
        }
    }
    throw IllegalStateException("did not find solution")
}

fun hasExactlyCountOfAnyLetter(input: String, count: Int) =
    input.groupBy { it }.values.any { it.size == count }

fun commonLetters(first: String, second: String) =
    first.zip(second).fold("") { acc, pair ->
        if (pair.first == pair.second) acc + pair.first else acc
    }
