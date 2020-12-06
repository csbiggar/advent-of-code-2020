package day5

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class BinaryBoardingTest {

    @ParameterizedTest
    @CsvSource(
        "FFFFFFFLLL,0,0",
        "FFFFFFFRLL,0,4",
        "FBFFFFFLLL,32,0",
        "BBBBBBBRRR,127,7",

        "FBFBBFFRLR,44,5"
    )
    fun `resolve seat`(binarySpacePartition: String, row: Int, column: Int) {
        assertThat(findSeat(binarySpacePartition)).isEqualTo(Seat(row, column))
    }

    @Test
    fun `calculate seatId`() {
        assertThat(Seat(44,5).id).isEqualTo(357)
    }
}