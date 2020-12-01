package day1

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

internal class ExpensesReportKtTest {

    @Test
    fun `should return the only two numbers if they sum to 2020`() {
        val input = listOf(2019, 1)
        assertThat(findComponentsSummingTo2020(input, 2)).containsExactlyInAnyOrder(1, 2019)
    }

    @Test
    fun `should return the only two numbers if they sum to 2020 the other way around`() {
        val input = listOf(2019, 1)
        assertThat(findComponentsSummingTo2020(input, 2)).containsExactlyInAnyOrder(1, 2019)
    }

    @Test
    fun `should return the two numbers that sum to 2020 when they are not first in the list`() {
        val input = listOf(1, 2, 2019)
        assertThat(findComponentsSummingTo2020(input, 2)).containsExactlyInAnyOrder(1, 2019)

    }

    @Test
    fun `should throw exception if no numbers sum to 2020`() {
        val input = listOf(2018, 1)
        assertThrows<IllegalArgumentException> { findComponentsSummingTo2020(input, 2) }
    }

    @Test
    fun `should find the two numbers whose sum is 2020`() {
        val input = listOf(
            1721,
            979,
            366,
            299,
            675,
            1456
        )

        assertThat(findComponentsSummingTo2020(input, 2)).containsExactlyInAnyOrder(1721, 299)
    }

    @Test
    fun `should return the three numbers even when it hits the two that add up to 2020 first`() {
        val input = listOf(33, 55, 2019, 1, 2015, 3, 2)

        // When
        val result = findComponentsSummingTo2020(input, 3)

        // Then
        assertThat(result).containsExactlyInAnyOrder(2015, 3, 2)
    }

    @Test
    fun `should find the three numbers whose sum is 2020`() {
        val input = listOf(
            1721,
            979,
            366,
            299,
            675,
            1456
        )

        // When
        val result = findComponentsSummingTo2020(input, 3)

        // Then
        assertThat(result).containsExactlyInAnyOrder(979, 366, 675)
    }
}