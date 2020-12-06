package day6

import helpers.FileReader
import helpers.splitByBlankLines

private val input = FileReader("/day6/input.txt").readText()

/*
  D6#1  6443 sum of distinct yeses
  D6#2  3232 sum of shared yeses
 */

fun main() {
    val sumOfDistinctYeses = sumOfDistinctYeses(input)
    println(sumOfDistinctYeses)

    val sumOfSharedYeses = sumOfSharedYeses(input)
    println(sumOfSharedYeses)
}

fun sumOfDistinctYeses(input: String): Int {
    return input.splitByBlankLines()
        .map { countDistinctYeses(it) }
        .sum()
}

fun sumOfSharedYeses(input: String): Int {
    return input.splitByBlankLines()
        .map { countSharedYeses(it) }
        .sum()
}

fun countDistinctYeses(groupAnswers: String): Int {
    return groupAnswers.toCharArray().filterNot { it == ' ' }.distinct().size
}

fun countSharedYeses(groupAnswers: String): Int {
    val numberOfPeopleInGroup = groupAnswers
        .split(" ")
        .size

    val result = groupAnswers.toCharArray()
        .filterNot { it == ' ' }
        .groupBy { it }
        .filter { (_, yeses) -> yeses.size == numberOfPeopleInGroup }
        .size

    return result
}
