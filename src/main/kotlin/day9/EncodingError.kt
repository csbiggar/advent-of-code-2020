package day9

import helpers.FileReader

private val input = FileReader("/day9/input.txt").readLines().map { it.toLong() }

/*
    D9#1 466456641
 */

fun main() {
    val result = findOddNumber(input, 25)
    println("Number that doesn't work is $result")
}

fun findOddNumber(input: List<Long>, preambleSize: Int): Long {
    val encoder = Encoder(input, preambleSize)
    input.forEachIndexed { index, number ->
        if (!encoder.isOk(index)) return number
    }
    throw IllegalArgumentException("No problem numbers found")
}

class Encoder(private val input: List<Long>, private val preambleSize: Int) {

    fun isOk(index: Int): Boolean {
        if (index < preambleSize) return true

        val numberToCheck = input[index]
        val availableNumbers = input.subList(index - preambleSize, index)

        availableNumbers.forEachIndexed { i, number ->
            val checkAgainst = availableNumbers - listOf(availableNumbers[i])
            checkAgainst.forEach {
                if (it + number == numberToCheck) {
                    return true
                }
            }
        }
        return false
    }
}