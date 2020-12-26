package day10

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class AdapterArrayTest {


    @Test
    fun `example set`() {

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