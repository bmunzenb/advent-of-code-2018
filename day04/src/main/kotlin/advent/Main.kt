package advent

import java.io.File

typealias Schedule = Map<Pair<String,Int>, List<IntRange>>

fun main(args:Array<String>) {

    val input = File(ClassLoader.getSystemResource("input.txt").file)
            .readLines()
            .map { it.toRecord() }
            .sorted()
            .schedule()

    println(solvePart1(input))
    println(solvePart2(input))
}

fun List<Record>.schedule(): Schedule {

    // schedule = (date, guard) -> [start..end]
    val schedule = mutableMapOf<Pair<String,Int>, List<IntRange>>()

    var guard: Int = -1
    var beginSleep: Int = -1

    forEach {
        with(it.action) {
            val start = indexOf('#')
            when {
                start > 0 -> {
                    val end = indexOf(' ', start)
                    guard = substring(start + 1, end).toInt()
                }
                this == "falls asleep" -> beginSleep = it.minute
                this == "wakes up" -> {
                    val key = it.date to guard
                    val sleep = beginSleep..(it.minute-1)
                    val list = listOf(sleep)
                    schedule[key] = schedule[key]?.plus(list) ?: list
                }
            }
        }
    }

    return schedule
}

fun solvePart1(schedule: Schedule): Int {

    val guardWithMostMinutesAsleep = mutableMapOf<Int,Int>().apply {
        // build a map of guard -> total minutes asleep
        schedule.forEach { entry ->
            val guard = entry.key.second
            val minutes = entry.value.map { it.count() + 1 }.sum()
            this[guard] = this[guard]?.plus(minutes) ?: minutes
        }
    }.maxBy { it.value }!!.key

    val minuteWithMostDaysAsleep = mutableMapOf<Int,Int>().apply {
        // build a map of minute -> total days asleep
        schedule.filter { it.key.second == guardWithMostMinutesAsleep }.values.forEach { ranges ->
            ranges.forEach { range ->
                range.forEach {
                    this[it] = this[it]?.plus(1) ?: 1
                }
            }
        }
    }.maxBy { it.value }!!.key

    return guardWithMostMinutesAsleep * minuteWithMostDaysAsleep
}

fun solvePart2(schedule: Schedule): Int {

    val solution = mutableMapOf<Int, MutableMap<Int,Int>>().apply {
        // build map of guard -> (minute -> count)
        schedule.forEach { entry ->
            entry.value.forEach { range ->
                val guard = entry.key.second
                range.forEach {
                    val map = this[guard] ?: mutableMapOf()
                    map[it] = map[it]?.plus(1) ?: 1
                    this[guard] = map
                }
            }
        }
    }.entries.fold(0 to (0 to 0)) { acc, entry ->
        // acc = (guard, (minute, days))
        val max = entry.value.entries.maxBy { it.value }!!
        if (max.value > acc.second.second) entry.key to (max.key to max.value) else acc
    }

    return solution.first * solution.second.first
}

fun String.toRecord() = Record(
        date = substring(1..10),
        hour = substring(12..13).toInt(),
        minute = substring(15..16).toInt(),
        action = substring(19)
)

data class Record(val date: String, val hour: Int, val minute: Int, val action: String) : Comparable<Record> {
    override fun compareTo(other: Record) =
            if (date == other.date)
                if (hour == other.hour)
                    minute.compareTo(other.minute)
                else hour.compareTo(other.hour)
            else date.compareTo(other.date)
}