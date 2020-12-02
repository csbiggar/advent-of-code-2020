package day2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class PasswordsKtTest {

    @Test
    fun `should include  valid password`() {
        val input = listOf("1-1 a: a")

        val result = findValidPasswords(input)

        val expected = PasswordEntry(password = "a", requiredLetter = 'a', minOccurrence = 1, maxOccurrence = 1)
        assertThat(result).containsExactly(expected)
    }

    @Test
    fun `should exclude password with no occurrences of the letter`() {
        val input = listOf("1-1 a: b")

        val result = findValidPasswords(input)

        assertThat(result).isEmpty()
    }

    @Test
    fun `should exclude password with too few occurrences  of letter`() {
        val input = listOf("2-2 a: a")

        val result = findValidPasswords(input)

        assertThat(result).isEmpty()
    }

    @Test
    fun `should exclude password with too many of letter`() {
        val input = listOf("2-2 a: aaa")

        val result = findValidPasswords(input)

        assertThat(result).isEmpty()
    }

    @Test
    fun `should find two valid passwords in example list`() {
        val input = listOf(
            "1-3 a: abcde",
            "1-3 b: cdefg",
            "2-9 c: ccccccccc"
        )

        val result = findValidPasswords(input)

        assertThat(result.map { it.password }).containsExactlyInAnyOrder("abcde", "ccccccccc")
    }
}