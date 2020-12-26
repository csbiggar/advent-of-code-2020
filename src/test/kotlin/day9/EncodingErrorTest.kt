package day9

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
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


    @Test
    @Disabled("Not implemented yet")
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
                arguments(listOf(1, 2, 4), 2, 2, false),
                arguments(listOf(1, 2, 3), 2, 2, true),
                arguments(listOf(1, 2, 3, 4), 2, 3, false),
                arguments(listOf(1, 2, 3, 4, 5), 2, 4, false),
                arguments(listOf(1, 2, 3, 4, 5), 3, 4, true),
            )
        }

        private fun arguments(input: List<Long>, preambleSize: Int, index: Int, expectedResult: Boolean) = Arguments.of(
            input,
            preambleSize,
            index,
            expectedResult
        )
    }
}