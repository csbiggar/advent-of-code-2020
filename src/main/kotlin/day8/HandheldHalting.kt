package day8

import day8.Operation.ACCUMULATE
import day8.Operation.JUMP
import day8.Operation.NOOP
import helpers.FileReader

private val input = FileReader("/day8/input.txt").readText()

/*
    D8#1 Result 1928
    D8#2 Result 1319
 */
fun main() {
    val instructions = parseInstructions(input)

    val result = Game(instructions).accumulationUpToFirstRepeatedInstruction()
    println("Result before first repeated instruction $result")

    val correctedResult = Game(instructions).correctedGameResult()
    println("Result after correction $correctedResult")

}


enum class ResultType {
    SUCCESS, FAIL
}

data class Result(val type: ResultType, val accumulatedValue: Long)

class Game(private val instructions: List<Instruction>) {
    var accumulatedValue: Long = 0
        private set

    private val visitedIndexes = mutableListOf<Int>()

    tailrec fun correctedGameResult(index: Int = 0): Long {
        val newInstructions = replace(instructions, index)
        val result = Game(newInstructions).accumulationUpToFirstRepeatedInstruction()
        return if (result.type == ResultType.SUCCESS) {
            println("Successful replacement occurred at index $index, ${instructions[index]} ")
            result.accumulatedValue
        } else {
            correctedGameResult(index + 1)
        }
    }

    tailrec fun accumulationUpToFirstRepeatedInstruction(instructionIndex: Int = 0): Result {

        if (visitedIndexes.contains(instructionIndex)) return Result(ResultType.FAIL, accumulatedValue)
        visitedIndexes.add(instructionIndex)

        val instruction = instructions[instructionIndex]

        if (instruction.operation == ACCUMULATE) accumulatedValue += instruction.argument

        val nextIndex = when (instruction.operation) {
            NOOP, ACCUMULATE -> instructionIndex + 1
            JUMP -> instructionIndex + instruction.argument
        }

        return if (nextIndex + 1 > instructions.size) return Result(ResultType.SUCCESS, accumulatedValue)
        else accumulationUpToFirstRepeatedInstruction(nextIndex)
    }
}

fun replace(list: List<Instruction>, index: Int): List<Instruction> {
    return list.mapIndexed { i, instruction ->
        if (i != index) {
            instruction
        } else {
            val newOperation = when (instruction.operation) {
                JUMP -> NOOP
                NOOP -> JUMP
                ACCUMULATE -> ACCUMULATE
            }
            Instruction(newOperation, instruction.argument)
        }
    }
}

fun parseInstructions(input: String): List<Instruction> {
    return input.lines()
        .filterNot { it.isBlank() }
        .map {
            val bits = it.trim().split(" ")
            Instruction(
                operation = when (bits[0]) {
                    "acc" -> ACCUMULATE
                    "jmp" -> JUMP
                    "nop" -> NOOP
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