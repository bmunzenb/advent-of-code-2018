package advent

import java.io.File
import kotlin.math.min

fun main(args:Array<String>) {

    val input = File(ClassLoader.getSystemResource("input.txt").file).readText()

    println(solvePart1(input))
    println(solvePart2(input))
}

fun solvePart1(polymer: String) = react(polymer)

fun solvePart2(polymer: String): Int {

    var solution = Int.MAX_VALUE

    val allChars = polymer.map { it.toLowerCase() }.distinct()

    allChars.forEach { c ->
        polymer.filterNot { it.toLowerCase() == c }.run {
            solution = min(solution, react(this))
        }
    }

    return solution
}

fun react(polymer: String): Int {

    val p = StringBuffer(polymer)

    var i = 0

    while (i < p.length-1) {
        val (x, y) = p[i] to p[i+1]
        if (x != y && x.toUpperCase() == y.toUpperCase()) {
            p.delete(i, i+2)
            i = when (i) { 0 -> 0 else -> i-1 }
        } else {
            i++
        }
    }

    return p.length
}