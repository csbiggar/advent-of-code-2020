package day10

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class AdapterArrayTest {

    companion object {

        @JvmStatic
        fun combinationsTests(): Stream<Arguments> {
            return Stream.of(
                combinationsArguments(input = listOf(1), expectedResult = 1),
                /*
                [1,2] [2]
                 */
                combinationsArguments(input = listOf(1, 2), expectedResult = 2),
                /*
                [1,2,3] [1,3] [2,3] [3]
                 */
                combinationsArguments(input = listOf(1, 2, 3), expectedResult = 4),
                combinationsArguments(input = example1Input, expectedResult = 8),
                combinationsArguments(input = example2Input, expectedResult = 19208),
            )
        }

        private fun combinationsArguments(input: List<Int>, expectedResult: Long) = Arguments.of(
            input,
            expectedResult
        )
    }

    @ParameterizedTest
    @MethodSource("combinationsTests")
    fun `find number of possible combinations`(
        input: List<Int>,
        expectedResult: Long
    ) {
        assertThat(Combinations(input).findCombinations()).isEqualTo(expectedResult)
    }

    @Test
    fun `should count number of 3 and number of 1 step differences`() {

        val input = listOf(
            16,
            10,
            15,
            5,
            1,
            11,
            7,
            19,
            6,
            12,
            4
        )

        assertThat(findDiffCounts(input)).isEqualTo(Counts(diffOne = 7, diffThree = 5))
    }
}

private val example1Input = listOf(16, 10, 15, 5, 1, 11, 7, 19, 6, 12, 4)

private val example2Input = listOf(
    28,
    33,
    18,
    42,
    31,
    14,
    46,
    20,
    48,
    47,
    24,
    23,
    49,
    45,
    19,
    38,
    39,
    11,
    1,
    32,
    25,
    35,
    8,
    17,
    7,
    9,
    4,
    2,
    34,
    10,
    3
)
