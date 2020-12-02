package day1

import helpers.FileReader
import java.lang.IllegalArgumentException
import kotlin.system.measureTimeMillis

private val input = FileReader("/day1/input1.txt").readInts()


/*
For 2 components: Numbers [28, 1992], Result: 55776
For 3 components: Numbers [983, 314, 723], Result: 223162626
 */

fun main() {
    val time1 = measureTimeMillis {
        val resultFor2 = findComponentsSummingTo2020(input, 2)
        printResult(resultFor2)
    }
    println("Part 1 takes $time1 ms\n")
    
    val time2 = measureTimeMillis {
        val resultFor3 = findComponentsSummingTo2020(input, 3)
        printResult(resultFor3)
    }
    println("Part 2 takes $time2 ms")
}

private fun printResult(result: List<Int>) {
    println("For ${result.size} components: Numbers $result, Result: ${result.reduce { first, second -> first * second }}")
}

fun findComponentsSummingTo2020(report: List<Int>, numberOfComponents: Int): List<Int> {

    if (numberOfComponents == 2) return findTwoComponentsSummingTo2020(0, report)
        ?: throw IllegalArgumentException("No numbers sum to 2020")

    if (numberOfComponents == 3) return findThree(report) ?: throw IllegalArgumentException("No numbers sum to 2020")
    else TODO()

}

tailrec fun findTwoComponentsSummingTo2020(base: Int = 0, report: List<Int>): List<Int>? {

    val firstNumber = report.first()
    val possibleSecondNumbers = report.drop(1)

    val secondNumber = possibleSecondNumbers
        .firstOrNull { secondNumber -> base + firstNumber + secondNumber == 2020 }

    if (secondNumber != null) {
        // found!
        return listOf(firstNumber, secondNumber)
    }

    if (possibleSecondNumbers.size < 2) return null

    return findTwoComponentsSummingTo2020(base, possibleSecondNumbers)
}

tailrec fun findThree(report: List<Int>): List<Int>? {
    val first = report.first()
    val possibleSecondAndThirdNumbers = report.drop(1)
    val secondAndThird = findTwoComponentsSummingTo2020(first, possibleSecondAndThirdNumbers)

    if (secondAndThird != null) {
        return listOf(first) + secondAndThird
    }

    if (possibleSecondAndThirdNumbers.size < 3) throw IllegalArgumentException("No numbers sum to 2020")

    return findThree(possibleSecondAndThirdNumbers)
}