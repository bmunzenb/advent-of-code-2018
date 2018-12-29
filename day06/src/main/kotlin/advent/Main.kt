package advent

import java.io.File
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

typealias Coordinate = Pair<Int, Int>

fun main(args:Array<String>) {

    val input: List<Coordinate> = File(ClassLoader.getSystemResource("input.txt").file)
            .readLines()
            .map { line ->
                val s = line.split(",").map { it.trim().toInt() }
                s[0] to s[1]
            }

    val min: Coordinate = input.fold(Int.MAX_VALUE to Int.MAX_VALUE) { acc, c ->
        min(acc.first, c.first) to min(acc.second, c.second)
    }

    val max: Coordinate = input.fold(Int.MIN_VALUE to Int.MIN_VALUE) { acc, c ->
        max(acc.first, c.first) to max(acc.second, c.second)
    }

    println(solvePart1(input, min, max))
    println(solvePart2(input, min, max, 10000))
}

fun solvePart1(input: List<Coordinate>, min: Coordinate, max: Coordinate): Int {

    val map = mutableMapOf<Coordinate, Coordinate>()

    (min.first..max.first).forEach { x ->
        (min.second..max.second).forEach { y->
            val p: Coordinate = x to y

            val sorted = input.map { it to manhattan(p, it) }.sortedBy { it.second }

            if (sorted[0].second != sorted[1].second) {
                map[p] = sorted[0].first
            }
        }
    }

    val infinite = map.filter {
        it.key.first == min.first || it.key.first == max.first || it.key.second == min.second || it.key.second == max.second
    }.map { it.value }.distinct()

    val finite = map.filterNot { infinite.contains(it.value) }

    return finite.values.groupBy { it }.map { it.value.size }.max()!!
}

fun solvePart2(input: List<Coordinate>, min: Coordinate, max: Coordinate, distance: Int): Int {

    val map = mutableMapOf<Coordinate, Int>()

    (min.first..max.first).forEach { x ->
        (min.second..max.second).forEach { y->
            val p: Coordinate = x to y
            map[p] = input.fold(0) { acc, c ->
                acc + manhattan(p, c)
            }
        }
    }

    val area = map.filter { it.value < distance }

    return area.size
}

fun manhattan(p1: Coordinate, p2: Coordinate) = abs(p1.first - p2.first) + abs(p1.second - p2.second)