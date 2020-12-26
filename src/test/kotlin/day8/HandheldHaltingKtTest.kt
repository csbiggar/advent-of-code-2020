package day8

import day8.Operation.ACCUMULATE
import day8.Operation.JUMP
import day8.Operation.NOOP
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class HandheldHaltingKtTest {

    @Test
    fun `noop should do nothing`() {
        val input = """
            nop +1
            nop +3
            nop -1
            """
        assertThat(Game(parseInstructions(input)).runProgram().accumulatedValue).isEqualTo(0)
    }

    @Test
    fun `acc +1 should add 1 to the the accumulated value`() {
        val input = """
            acc +1
            """
        assertThat(Game(parseInstructions(input)).runProgram().accumulatedValue).isEqualTo(1)
    }

    @Test
    fun `jmp +2 should move two forwards`() {
        val input = """
            jmp +2
            acc +10
            acc +3
            """
        assertThat(Game(parseInstructions(input)).runProgram().accumulatedValue).isEqualTo(3)
    }

    @Test
    fun `should give a return type of SUCCESS when end of program is reached with no repeated instructions`() {
        val input = "nop +1"
        assertThat(Game(parseInstructions(input)).runProgram().type).isEqualTo(ResultType.SUCCESS)
    }

    @Test
    fun `should abort on first repeat with a result of FAIL`() {
        val input = """
            nop +0
            nop +0
            jmp -1
            """

        val runProgram = Game(parseInstructions(input)).runProgram()

        // Then program aborts
        assertThat(runProgram.accumulatedValue).isEqualTo(0)

        // And result is fail
        assertThat(runProgram.type).isEqualTo(ResultType.FAIL)
    }

    @Test
    fun `example program should give 5`() {
        val input = """
            nop +0
            acc +1
            jmp +4
            acc +3
            jmp -3
            acc -99
            acc +1
            jmp -4
            acc +6
        """.trimIndent()

        assertThat(Game(parseInstructions(input)).runProgram().accumulatedValue).isEqualTo(5)
    }

    @Test
    @Disabled("not implemented yet")
    fun `should find the program which runs to completion with no repeats`() {
        val input = """
            nop +0
            acc +1
            jmp +4
            acc +3
            jmp -3
            acc -99
            acc +1
            jmp -4
            acc +6
        """.trimIndent()

        assertThat(Game(parseInstructions(input)).findCorrectedProgram()).isEqualTo(8)

    }

}

class ReplaceInstructionTest {
    @Test
    fun `should replace nop with jmp`() {
        // Given
        val instructions = listOf(
            Instruction(NOOP, 1)
        )

        // When
        val result = replace(instructions, 0)

        // Then
        assertThat(result).containsExactly(
            Instruction(JUMP, 1)
        )
    }

    @Test
    fun `should replace jmp with nop`() {
        // Given
        val instructions = listOf(
            Instruction(JUMP, 1)
        )

        // When
        val result = replace(instructions, 0)

        // Then
        assertThat(result).containsExactly(
            Instruction(NOOP, 1)
        )
    }

    @Test
    fun `should do nothing with acc`() {
        // Given
        val instructions = listOf(
            Instruction(ACCUMULATE, 1)
        )

        // When
        val result = replace(instructions, 0)

        // Then
        assertThat(result).containsExactly(
            Instruction(ACCUMULATE, 1)
        )
    }

    @Test
    fun `should only transform given element`() {
        // Given
        val instructions = listOf(
            Instruction(JUMP, 1),
            Instruction(JUMP, 2),
            Instruction(JUMP, 3),
        )

        // When
        val result = replace(instructions, 1)

        // Then
        assertThat(result).containsExactly(
            Instruction(JUMP, 1),
            Instruction(NOOP, 2),
            Instruction(JUMP, 3),
        )
    }
}

class ParseInstructionsTest {
    @Test
    fun `should parse instructions`() {
        val input = """
            nop +0
            acc +1
            jmp +4
            jmp -3
        """.trimIndent()

        assertThat(parseInstructions(input)).containsExactly(
            Instruction(NOOP, 0),
            Instruction(ACCUMULATE, 1),
            Instruction(JUMP, 4),
            Instruction(JUMP, -3)
        )

    }
}