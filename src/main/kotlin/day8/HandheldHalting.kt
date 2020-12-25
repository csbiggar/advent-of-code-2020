package day8

import helpers.FileReader

private val input = FileReader("/day8/input.txt").readText()

/*
    D8#1 Result 1928
 */
fun main() {
    val result = Game(input).runProgram()
    println("Result $result")
}

class Game(input: String) {
    var accumulatedValue: Long = 0
        private set

    private val instructions: List<Instruction> = parseInstructions(input)

    private val visitedIndexes = mutableListOf<Int>()

    tailrec fun runProgram(instructionIndex: Int = 0): Long {

        if (visitedIndexes.contains(instructionIndex)) return accumulatedValue
        visitedIndexes.add(instructionIndex)

        val instruction = instructions[instructionIndex]

        if (instruction.operation == Operation.ACCUMULATE) accumulatedValue += instruction.argument

        val nextIndex = when (instruction.operation) {
            Operation.NOOP, Operation.ACCUMULATE -> instructionIndex + 1
            Operation.JUMP -> instructionIndex + instruction.argument
        }

        return if (nextIndex + 1 > instructions.size) accumulatedValue
        else runProgram(nextIndex)
    }

}

fun parseInstructions(input: String): List<Instruction> {
    return input.lines()
        .filterNot { it.isBlank() }
        .map {
            val bits = it.trim().split(" ")
            Instruction(
                operation = when (bits[0]) {
                    "acc" -> Operation.ACCUMULATE
                    "jmp" -> Operation.JUMP
                    "nop" -> Operation.NOOP
                    else -> throw IllegalArgumentException("Unknown operation [${bits[0]}] in instruction $it")
                },
                argument = bits[1].toInt()
            )
        }

}

enum class Operation {
    NOOP, ACCUMULATE, JUMP
}

data class Instruction(
    val operation: Operation,
    val argument: Int
)