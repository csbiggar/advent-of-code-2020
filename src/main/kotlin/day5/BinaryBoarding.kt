package day5

import helpers.FileReader

private val input = FileReader("/day5/input.txt").readText()

data class Seat(val row: Int, val column: Int) {
    val id = row * 8 + column
}

enum class Matcher(
    val max: Int,
    val lowerIndicator: Char,
    val upperIndicator: Char
) {
    ROW(127, 'F', 'B'),
    COLUMN(7, 'L', 'R')
}

/*
  D5#1 Max seat id = 818
  D5#2 Missing seat id = 559
 */

fun main() {
    val maxSeatId = findMaxSeatId(seats())
    println(maxSeatId)

    val missingSeat = findMissingSeat(seats())
    println(missingSeat)
}

fun findMissingSeat(seats: List<Seat>): Int {
    val surroundingSeats = seats
        .sortedBy { it.id }
        .windowed(size = 2, step = 1)
        .first {
            it[1].id - it[0].id > 1
        }
    return surroundingSeats.first().id + 1
}

private fun findMaxSeatId(seats: List<Seat>) = seats
    .map { it.id }
    .maxOrNull()

private fun seats() = input.lines()
    .map {
        findSeat(it)
    }

fun findSeat(input: String): Seat {
    val row = resolve(input.take(7), Matcher.ROW)
    val column = resolve(input.takeLast(3), Matcher.COLUMN)
    return Seat(row, column)
}

private fun resolve(
    pattern: String,
    matcher: Matcher
): Int {
    var rows = (0..matcher.max).toList()
    pattern.forEach { char ->
        when (char) {
            matcher.lowerIndicator -> rows = rows.take(rows.size / 2)
            matcher.upperIndicator -> rows = rows.takeLast(rows.size / 2)
        }
    }
    if (rows.size != 1) throw Exception("Unique row not found $rows")
    return rows.first()
}