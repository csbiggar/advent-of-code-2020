package day2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class PasswordsKtTest {

    @Nested
    inner class FirstRules {
        @Test
        fun `should include  valid password`() {
            val input = listOf("1-1 a: a")

            val result = findValidPasswordsUsingFirstRules(input)

            val expected = PasswordEntry(password = "a", requiredLetter = 'a', minOccurrence = 1, maxOccurrence = 1)
            assertThat(result).containsExactly(expected)
        }

        @Test
        fun `should exclude password with no occurrences of the letter`() {
            val input = listOf("1-1 a: b")

            val result = findValidPasswordsUsingFirstRules(input)

            assertThat(result).isEmpty()
        }

        @Test
        fun `should exclude password with too few occurrences  of letter`() {
            val input = listOf("2-2 a: a")

            val result = findValidPasswordsUsingFirstRules(input)

            assertThat(result).isEmpty()
        }

        @Test
        fun `should exclude password with too many of letter`() {
            val input = listOf("2-2 a: aaa")

            val result = findValidPasswordsUsingFirstRules(input)

            assertThat(result).isEmpty()
        }

        @Test
        fun `should find two valid passwords in example list`() {
            val input = listOf(
                "1-3 a: abcde",
                "1-3 b: cdefg",
                "2-9 c: ccccccccc"
            )

            val result = findValidPasswordsUsingFirstRules(input)

            assertThat(result.map { it.password }).containsExactlyInAnyOrder("abcde", "ccccccccc")
        }
    }

    @Nested
    inner class SecondRules {
        @Test
        fun `should pass if first position contains given letter`() {
            val input = listOf("1-2 a: abc")

            val result = findValidPasswordsUsingSecondRules(input)

            val expected = PasswordEntry(password = "abc", requiredLetter = 'a', minOccurrence = 1, maxOccurrence = 2)
            assertThat(result).containsExactly(expected)
        }

        @Test
        fun `should fail if both first and second position contains given letter`() {
            val input = listOf("1-2 a: aa")

            val result = findValidPasswordsUsingSecondRules(input)

            assertThat(result).isEmpty()
        }

        @Test
        fun `should find two valid passwords in example list`() {
            val input = listOf(
                "1-3 a: abcde",
                "1-3 b: cdefg",
                "2-9 c: ccccccccc"
            )

            val result = findValidPasswordsUsingSecondRules(input)

            assertThat(result.map { it.password }).containsExactlyInAnyOrder("abcde")
        }
    }
}