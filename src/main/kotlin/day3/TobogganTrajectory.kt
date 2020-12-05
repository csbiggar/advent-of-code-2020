package day3

import helpers.FileReader
import kotlin.system.measureTimeMillis

private val input = FileReader("/day3/input.txt").readText()

private const val TREE = '#'
private val rightAndDownCoords = """  |1,1
                                      |3,1
                                      |5,1
                                      |7,1
                                      |1,2""".trimMargin("|")

/*
    D3#1  164 trees
    D3#2 product is 5007658656
 */

fun main() {
    val time = measureTimeMillis {
        val numberOfTrees = findNumberOfTreesEncountered(input, right = 3, down = 1)
        println(numberOfTrees)
    }
    println("Part 1 takes $time ms\n")

    val time2 = measureTimeMillis {
        val product = productOfAllTrees(input)
        println(product)

    }
    println("Part 2 takes $time2 ms")

}

fun findNumberOfTreesEncountered(input: String, right: Int, down: Int): Long {
    var x = right
    var treeCount: Long = 0

    extendLines(input, right, down)
        .drop(down)
        .forEachIndexed { zeroBasedY, line ->
            val lineInUse = (zeroBasedY + down) % down == 0
            if (lineInUse) {
                if (line[x] == TREE) {
                    treeCount++
                }
                x += right
            }
        }

    return treeCount
}

fun productOfAllTrees(input: String): Long {
    return rightAndDownCoords
        .lines()
        .map {
            Pair(it.substringBefore(",").toInt(), it.substringAfter(",").toInt())
        }
        .map { rightDown ->
            findNumberOfTreesEncountered(input, rightDown.first, rightDown.second)

        }
        .reduce { runningTotal, next -> runningTotal * next }
}

private fun extendLines(input: String, right: Int, down: Int): List<String> {
    val lines = input.lines()
    val rowMultiplesRequired = findMultiplier(lines, right, down)
    return lines.map { it.repeat(rowMultiplesRequired) }
}

internal fun findMultiplier(lines: List<String>, right: Int, down: Int): Int {
    return 1000
    //TODO Make this work
//    val numberOfRows = lines.size
//    val pointsNeeded = ((numberOfRows - down) / down) * right + 1
//    val rowLength = lines.first().count() // assume all are same
//
//    return (pointsNeeded / rowLength) + 1 // rough rounding!
}