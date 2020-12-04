package day3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class TobogganTrajectoryKtTest {

    @Nested
    inner class OriginalCoords {
        @Test
        fun `one run, empty of trees`() {
            val input = """ |.....
                        |....."""
                .trimMargin("|")

            val result = findNumberOfTreesEncountered(input, 3, 1)

            assertThat(result).isEqualTo(0)
        }

        @Test
        fun `one run, has trees but none that we touch`() {
            val input = """ |.#...
                        |....."""
                .trimMargin("|")

            val result = findNumberOfTreesEncountered(input, 3, 1)

            assertThat(result).isEqualTo(0)
        }

        @Test
        fun `one run, has tree on our stopping point`() {
            val input = """ |.....
                        |...#."""
                .trimMargin("|")

            val result = findNumberOfTreesEncountered(input, 3, 1)

            assertThat(result).isEqualTo(1)
        }

        @Test
        fun `two runs, no trees`() {
            val input = """ |.....
                        |.....
                        |....."""
                .trimMargin("|")

            val result = findNumberOfTreesEncountered(input, 3, 1)

            assertThat(result).isEqualTo(0)
        }

        @Test
        fun `two runs, no tree first run, tree second run `() {
            val input = """ |....
                        |....
                        |..#."""
                .trimMargin("|")

            /*
        Extrapolates to

        ........     ........
        ........     ...O....
        ..#...#.     ..#...X.


         */

            val result = findNumberOfTreesEncountered(input, 3, 1)

            assertThat(result).isEqualTo(1)
        }

        @Test
        fun `should extend rows far enough`() {
            val input = """ |....
                        |....
                        |....
                        |....
                        |....
                        |....
                        |....
                        |...."""
                .trimMargin("|")

            val result = findNumberOfTreesEncountered(input, 3, 1)

            assertThat(result).isEqualTo(0)


            /*

        8 rows
        22 x points needed = (num rows - 1) x 3 + 1

        Row length 4
        6 multiples needed ( 22 / 4 = 5.5 rounded up)


        ........................ no
        ...X.................... yes
        ......X.................
        .........X..............
        ............X...........
        ...............X........
        ..................X.....
        .....................X..


         */
        }

        @Test
        fun `should find number of trees for example`() {
            val input = """ |..##.......
                        |#...#...#..
                        |.#....#..#.
                        |..#.#...#.#
                        |.#...##..#.
                        |..#.##.....
                        |.#.#.#....#
                        |.#........#
                        |#.##...#...
                        |#...##....#
                        |.#..#...#.#"""
                .trimMargin("|")

            val result = findNumberOfTreesEncountered(input, 3, 1)

            assertThat(result).isEqualTo(7)
        }
    }

    @Test
    fun `different route`() {
        val input = """ |.....
                        |.#...
                        |..#.."""
            .trimMargin("|")

        val result = findNumberOfTreesEncountered(input, 1, 1)

        assertThat(result).isEqualTo(2)
    }

    @Test
    fun `different route where down step is greater than 1`() {
        val input = """ |.....
                        |.....
                        |.#..."""
            .trimMargin("|")

        val result = findNumberOfTreesEncountered(input, 1, 2)

        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `different route with different right and down`() {
        val input = """ |.....
                        |.....
                        |.....
                        |..#..
                        |....."""
            .trimMargin("|")

        val result = findNumberOfTreesEncountered(input, 2, 3)

        assertThat(result).isEqualTo(1)
    }


    @Test
    @Disabled("Multiplier temporarily hard coded to 1000")
    fun `find multiplier for original example`() {
        val input = """ |....
                        |....
                        |....
                        |....
                        |....
                        |....
                        |....
                        |...."""
            .trimMargin("|")

        val result = findMultiplier(input.lines(), 3, 1)

        /*

        8 rows
        22 x points needed = (num rows - 1) x 3 + 1

        Row length 4
        6 multiples needed ( 22 / 4 = 5.5 rounded up)


        ........................
        ...X....................
        ......X.................
        .........X..............
        ............X...........
        ...............X........
        ..................X.....
        .....................X..


         */

        assertThat(result).isEqualTo(6)

    }

    @Test
    @Disabled("Multiplier temporarily hard coded to 1000")
    fun `find multiplier for different right and down`() {
        val input = """ |....
                        |....
                        |....
                        |....
                        |....
                        |....
                        |....
                        |...."""
            .trimMargin("|")

        val result = findMultiplier(input.lines(), 1, 2)

        /*

        8 rows
        4 x points needed (8 - 1) / 2 * 1 + 1

        Row length 4
        1 multiples needed ( 4 / 4 = 1 )


        ....
        ....
        .X..
        ....
        ..X.
        ....
        ...X
        ....


         */

        assertThat(result).isEqualTo(1)


        // Check a different run
        val result2 = findMultiplier(input.lines(), 3, 2)

        /*

        8 rows
        10 x points needed = (8 - 2) / 2 * 3   + 1

        Row length 4
        3 multiples needed ( 10 / 4 = 2.5 rounded )


        ................................
        ................................
        ...X............................
        ................................
        ......X.........................
        ................................
        .........X......................
        ................................


         */

        assertThat(result2).isEqualTo(3)

    }

    @Test
    fun `should multiply route results`() {

        val input = """ |..##.......
                        |#...#...#..
                        |.#....#..#.
                        |..#.#...#.#
                        |.#...##..#.
                        |..#.##.....
                        |.#.#.#....#
                        |.#........#
                        |#.##...#...
                        |#...##....#
                        |.#..#...#.#"""
            .trimMargin("|")

        val result = productOfAllTrees(input)

        assertThat(result).isEqualTo(336)
    }

    @Test
    fun `example route with down step greater than one`() {

        val input = """ |..##.......
                        |#...#...#..
                        |.#....#..#.
                        |..#.#...#.#
                        |.#...##..#.
                        |..#.##.....
                        |.#.#.#....#
                        |.#........#
                        |#.##...#...
                        |#...##....#
                        |.#..#...#.#"""
            .trimMargin("|")

//
//        val input = 1""" |..##.......
//                    2    |#...#...#..
//                 x  3    |.X....#..#.
//                    4    |..#.#...#.#
//                    5    |.#0..##..#.
//                    6    |..#.##.....
//                 x  7    |.#.X.#....#
//                    8    |.#........#
//                    9    |#.##0..#...
//                   10    |#...##....#
//                   11    |.#..#0..#.#

        val result = findNumberOfTreesEncountered(input, 1, 2)

        assertThat(result).isEqualTo(2)
    }
}


