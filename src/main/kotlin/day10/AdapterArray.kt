package day10

import helpers.FileReader
import kotlin.math.absoluteValue

private val input = FileReader("/day10/input.txt").readLines().map { it.toInt() }


data class Counts(val diffOne: Int, val diffThree: Int)

fun main() {
    val result = findDiffCounts(input)
    println("$result, product is ${result.diffOne * result.diffThree}")
}


fun findDiffCounts(input: List<Int>): Counts {

    val max = input.maxOrNull() ?: throw IllegalArgumentException("Err ... there should be a max ...")

    val result = (input + listOf(0, max + 3))
        .sorted()
        .windowed(2, 1)
        .map { (a, b) -> (a - b).absoluteValue }
        .groupBy { it }

    return Counts(
        diffOne = result[1]?.size ?: 0,
        diffThree = result[3]?.size ?: 0
    )
}