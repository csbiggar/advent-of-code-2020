package day3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class TobogganTrajectoryKtTest {

    @Test
    fun `one run, empty of trees`(){
        val input = """ |.....
                        |....."""
            .trimMargin("|")

        val result = findNumberOfTreesEncountered(input)

        assertThat(result).isEqualTo(0)
    }

    @Test
    fun `one run, has trees but none that we touch`(){
        val input = """ |.#...
                        |....."""
            .trimMargin("|")

        val result = findNumberOfTreesEncountered(input)

        assertThat(result).isEqualTo(0)
    }

    @Test
    fun `one run, has tree on our stopping point`(){
        val input = """ |.....
                        |...#."""
            .trimMargin("|")

        val result = findNumberOfTreesEncountered(input)

        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `two runs, no trees`(){
        val input = """ |.....
                        |.....
                        |....."""
            .trimMargin("|")

        val result = findNumberOfTreesEncountered(input)

        assertThat(result).isEqualTo(0)
    }

    @Test
    fun `two runs, no tree first run, tree second run `(){
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

        val result = findNumberOfTreesEncountered(input)

        assertThat(result).isEqualTo(1)
    }


    @Test
    fun `should extend rows far enough`(){
        val input = """ |....
                        |....
                        |....
                        |....
                        |....
                        |....
                        |....
                        |...."""
            .trimMargin("|")

        val result = findNumberOfTreesEncountered(input)

        assertThat(result).isEqualTo(0)


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

        val result = findNumberOfTreesEncountered(input)

        assertThat(result).isEqualTo(7)
    }


    @Test
    fun `find multiplier`(){
        val input = """ |....
                        |....
                        |....
                        |....
                        |....
                        |....
                        |....
                        |...."""
            .trimMargin("|")

        val result = findMultiplier(input.lines())

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
}


