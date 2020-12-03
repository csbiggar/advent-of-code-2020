package day3

import helpers.FileReader
import kotlin.system.measureTimeMillis

private const val TREE = '#'
private val input = FileReader("/day3/input.txt").readText()

/*
    D3#1  164 trees
 */

fun main() {
    val time = measureTimeMillis {
        val numberOfTrees = findNumberOfTreesEncountered(input)
        println(numberOfTrees)
    }
    println("Part 1 takes $time ms")
}

fun findNumberOfTreesEncountered(input: String): Int {
    var x = 3
    var treeCount = 0

    extendLines(input)
        .drop(1)
        .forEach { line ->
            if (line[x] == TREE) {
                treeCount++
            }
            x += 3
        }

    return treeCount
}

private fun extendLines(input: String): List<String> {
    val lines = input.lines()
    val rowMultiplesRequired = findMultiplier(lines)
    return lines.map { it.repeat(rowMultiplesRequired) }
}

internal fun findMultiplier(lines: List<String>): Int {
    val numberOfRows = lines.size
    val pointsNeeded = (numberOfRows - 1) * 3 + 1
    val rowLength = lines.first().count() // assume all are same

    return (pointsNeeded / rowLength) + 1 // rough rounding!
}