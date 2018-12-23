package advent

import java.io.File

fun main(args:Array<String>) {

    val input = File(ClassLoader.getSystemResource("input.txt").file).readLines().map { it.toClaim() }

    val solution = solveBothParts(input)

    println(solution.first)
    println(solution.second)
}

fun solveBothParts(input: List<Claim>): Pair<Int,String> {

    val map = mutableMapOf<Pair<Int,Int>, List<String>>()

    input.forEach { claim ->
        claim.points().forEach {
            map[it] = map[it]?.plus(claim.id) ?: listOf(claim.id)
        }
    }

    val overlapped = map.values.filter { it.size > 1 }
    val remaining = with(overlapped.flatten()) { input.map { it.id }.filter { !contains(it) } }

    return overlapped.size to remaining.first()
}

fun String.toClaim(): Claim {
    val words = split("#", "@", ",", ":", "x", " ")
    return Claim(words[1], words[4].toInt(), words[5].toInt(), words[7].toInt(), words[8].toInt())
}

data class Claim(val id: String, val left: Int, val top: Int, val width: Int, val height: Int) {
    fun points(): List<Pair<Int,Int>> = mutableListOf<Pair<Int,Int>>().apply {
            for (x in left..(left + width - 1)) {
                for (y in top..(top + height - 1)) {
                    add(x to y)
                }
            }
        }
}
