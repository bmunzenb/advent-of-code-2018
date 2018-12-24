package advent

import java.io.File
import kotlin.math.min

fun main(args:Array<String>) {

    val input = File(ClassLoader.getSystemResource("input.txt").file).readText()

    println(solvePart1(input))
    println(solvePart2(input))
}

fun solvePart1(polymer: String) = polymer.react()

fun solvePart2(polymer: String) =
        ('a'..'z').fold(Int.MAX_VALUE) { acc, c ->
            min(acc, polymer.filterNot { it.toLowerCase() == c }.react())
        }

fun String.react(): Int {
    val p = StringBuilder(this)
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