package advent

import java.io.File

fun main(args:Array<String>) {

    val input = File(ClassLoader.getSystemResource("input.txt").file).readLines().map { it.toInt() }

    println(solvePart1(input))
    println(solvePart2(input))
}

fun solvePart1(input: List<Int>) = input.fold(0) { freq, delta -> freq + delta }

fun solvePart2(input: List<Int>): Int {

    val repeat = Repeat(input)
    val history = mutableMapOf<Int,Int>()
    var freq = repeat.next()

    while (!history.contains(freq)) {
        history[freq] = freq
        freq += repeat.next()
    }

    return freq
}

class Repeat<T>(private val items: List<T>) {

    private var index = 0

    fun next(): T {
        val value = items[index]
        index += 1
        if (index == items.size) {
            index = 0
        }
        return value
    }
}
