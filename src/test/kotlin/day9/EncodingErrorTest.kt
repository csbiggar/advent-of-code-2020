package day9

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class EncodingErrorTest {

    @Test
    fun `index less than preamble is ok `() {
        val input = listOf<Long>(1, 2, 3)

        assertThat(Encoder(input, 2).isOk(1)).isTrue()
    }

    @ParameterizedTest
    @MethodSource("generateTestData")
    fun `check if given digit is sum of two others in last x`(
        input: List<Long>,
        preambleSize: Int,
        index: Int,
        expectedResult: Boolean
    ) {
        assertThat(Encoder(input, preambleSize).isOk(index)).isEqualTo(expectedResult)
    }

    @ParameterizedTest
    @MethodSource("generatePart2TestData")
    fun `find run that sums to given target`(
        input: List<Long>,
        target: Long,
        expectedResult: List<Long>,
    ) {
        assertThat(Encoder(input, 1).findRunThatSumsTo(target)).isEqualTo(expectedResult)
    }

    @Test
    fun `example works`() {
        val preambleSize = 5
        val input = listOf<Long>(
            35,
            20,
            15,
            25,
            47,
            40,
            62,
            55,
            65,
            95,
            102,
            117,
            150,
            182,
            127,
            219,
            299,
            277,
            309,
            576
        )

        assertThat(findOddNumber(input, preambleSize)).isEqualTo(127)
    }

    companion object {
        @JvmStatic
        fun generateTestData(): Stream<Arguments> {
            return Stream.of(
                partOneArguments(listOf(1, 2, 4), 2, 2, false),
                partOneArguments(listOf(1, 2, 3), 2, 2, true),
                partOneArguments(listOf(1, 2, 3, 4), 2, 3, false),
                partOneArguments(listOf(1, 2, 3, 4, 5), 2, 4, false),
                partOneArguments(listOf(1, 2, 3, 4, 5), 3, 4, true),
            )
        }

        @JvmStatic
        fun generatePart2TestData(): Stream<Arguments> {
            return Stream.of(
                partTwoArguments(input = listOf(1, 2, 3), target = 3, expectedResult = listOf(1, 2)),
                partTwoArguments(input = listOf(1, 2, 3, 4), target = 5, expectedResult = listOf(2, 3)),
            )
        }

        private fun partTwoArguments(input: List<Long>, target: Long, expectedResult: List<Long>) = Arguments.of(
            input,
            target,
            expectedResult
        )

        private fun partOneArguments(input: List<Long>, preambleSize: Int, index: Int, expectedResult: Boolean) =
            Arguments.of(
                input,
                preambleSize,
                index,
                expectedResult
            )
    }
}