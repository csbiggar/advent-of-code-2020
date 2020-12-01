package day1

import helpers.FileReader
import java.lang.IllegalArgumentException

private val input = FileReader("/day1/input1.txt").readInts()

fun main() {
    val result = findComponentsSummingTo2020(input)

    println(result)
    println("Answer: ${result.first * result.second}")
}

tailrec fun findComponentsSummingTo2020(report: List<Int>): Pair<Int, Int> {

    val firstNumber = report.first()
    val possibleSecondNumbers = report.drop(1)

    possibleSecondNumbers.forEach { secondNumber ->
        if (secondNumber + firstNumber == 2020) return Pair(firstNumber, secondNumber)
    }

    if (possibleSecondNumbers.size < 2) throw IllegalArgumentException("No numbers sum to 2020")

    return findComponentsSummingTo2020(possibleSecondNumbers)
}