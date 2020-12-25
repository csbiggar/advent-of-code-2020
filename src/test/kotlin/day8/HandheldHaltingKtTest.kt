package day8

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Fail
import org.junit.jupiter.api.Test

internal class HandheldHaltingKtTest {

    @Test
    fun `noop should do nothing`() {
        val input = """
            nop +1
            nop +3
            nop -1
            """
        assertThat(Game(input).runProgram().accumulatedValue).isEqualTo(0)
    }

    @Test
    fun `acc +1 should add 1 to the the accumulated value`() {
        val input = """
            acc +1
            """
        assertThat(Game(input).runProgram().accumulatedValue).isEqualTo(1)
    }

    @Test
    fun `jmp +2 should move two forwards`() {
        val input = """
            jmp +2
            acc +10
            acc +3
            """
        assertThat(Game(input).runProgram().accumulatedValue).isEqualTo(3)
    }

    @Test
    fun `should give a return type of SUCCESS when end of program is reached with no repeated instructions`(){
        val input = "nop +1"
        assertThat(Game(input).runProgram().type).isEqualTo(ResultType.SUCCESS)
    }

    @Test
    fun `should abort on first repeat with a result of FAIL`() {
        val input = """
            nop +0
            nop +0
            jmp -1
            """

        val runProgram = Game(input).runProgram()

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

        assertThat(Game(input).runProgram().accumulatedValue).isEqualTo(5)
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
            Instruction(Operation.NOOP, 0),
            Instruction(Operation.ACCUMULATE, 1),
            Instruction(Operation.JUMP, 4),
            Instruction(Operation.JUMP, -3)
        )

    }
}