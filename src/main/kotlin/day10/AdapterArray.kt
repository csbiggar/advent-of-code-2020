package day10

import helpers.FileReader
import kotlin.math.absoluteValue

private val puzzleInput = FileReader("/day10/input.txt").readLines().map { it.toInt() }


data class Counts(val diffOne: Int, val diffThree: Int)

fun main() {
    val result = findDiffCounts(puzzleInput)
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


class Combinations(unsortedInput: List<Int>) {

    private val sortedOriginalInput = unsortedInput.sorted()

    init {
        println(sortedOriginalInput)
    }

    fun findCombinations(): Long {
        TODO()
    }

    /*
[1,2] [2]

[1,2,3] [1,3] [2,3] [3]

(0), 1, 4, 5, 6, 7, 10, 11, 12, 15, 16, 19, (22)
(0), 1, 4, 5, 6, 7, 10, 12, 15, 16, 19, (22)
(0), 1, 4, 5, 7, 10, 11, 12, 15, 16, 19, (22)
(0), 1, 4, 5, 7, 10, 12, 15, 16, 19, (22)
(0), 1, 4, 6, 7, 10, 11, 12, 15, 16, 19, (22)
(0), 1, 4, 6, 7, 10, 12, 15, 16, 19, (22)
(0), 1, 4, 7, 10, 11, 12, 15, 16, 19, (22)
(0), 1, 4, 7, 10, 12, 15, 16, 19, (22)
 */

}