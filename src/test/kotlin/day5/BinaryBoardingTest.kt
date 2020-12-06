package day5

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

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
        assertThat(Seat(44, 5).id).isEqualTo(357)
    }

    @ParameterizedTest
    @MethodSource("generateMissingSeatIdData")
    fun `find missing from list of ids`(ids: List<Int>, missingId: Int) {
        assertThat(findMissingId(ids)).isEqualTo(missingId)
    }


    @ParameterizedTest
    @MethodSource("generateMissingSeatData")
    fun `find missing from list of seats`(seats: List<Seat>, missingId: Int) {
        assertThat(findMissingSeat(seats)).isEqualTo(missingId)
    }

    companion object {
        @JvmStatic
        fun generateMissingSeatIdData(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(listOf(4, 6), 5),
                Arguments.of(listOf(3, 5, 2), 4),
                Arguments.of(listOf(2, 3, 4, 5, 6, 8, 9), 7),
            )
        }

        @JvmStatic
        fun generateMissingSeatData(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    listOf(
                        Seat(0, 0),
                        Seat(0, 2)
                    ), 1
                ),
                Arguments.of(
                    listOf(
                        Seat(0, 3),
                        Seat(0, 5),
                        Seat(0, 2)
                    ), 4
                ),
                Arguments.of(
                    listOf(
                        Seat(0, 2),
                        Seat(0, 3),
                        Seat(0, 4),
                        Seat(0, 5),
                        Seat(0, 6),
                        Seat(1, 0),
                        Seat(1, 1)
                    ),
                    7
                ),
            )
        }
    }
}



