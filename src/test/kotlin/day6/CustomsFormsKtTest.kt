package day6

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class CustomsFormsKtTest {

    @Nested
    inner class DistinctYeses {
        @Test
        fun `should deduplicate`() {
            val input = "dabbd"
            assertThat(countDistinctYeses(input)).isEqualTo(3)
        }

        @Test
        fun `should not count spaces`() {
            val input = "d   a"
            assertThat(countDistinctYeses(input)).isEqualTo(2)
        }

        @Test
        fun `should sum yeses`() {
            val input = """ |abc
                        |
                        |a
                        |b
                        |c
                        |
                        |ab
                        |ac
                        |
                        |a
                        |a
                        |a
                        |a
                        |
                        |b
        """.trimMargin("|")

            assertThat(sumOfDistinctYeses(input)).isEqualTo(11)
        }
    }

    @Nested
    inner class SharedYeses {
        @Test
        fun `should only count shared answers`() {
            val input = "ab ac"
            assertThat(countSharedYeses(input)).isEqualTo(1)
        }


        @Test
        fun `should sum shared yeses`() {
            val input = """ |abc
                        |
                        |a
                        |b
                        |c
                        |
                        |ab
                        |ac
                        |
                        |a
                        |a
                        |a
                        |a
                        |
                        |b
        """.trimMargin("|")

            assertThat(sumOfSharedYeses(input)).isEqualTo(6)
        }
    }
}