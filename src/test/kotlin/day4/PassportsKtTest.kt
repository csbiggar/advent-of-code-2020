package day4

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class PassportsKtTest {

    @Test
    fun `should split  single line into passports`() {
        val input = "x:y"

        val result = splitToPassports(input)

        assertThat(result).containsExactlyInAnyOrder("x:y")
    }

    @Test
    fun `should split two lines into single passport`() {
        val input = """ |x:y
                        |a:b
        """.trimMargin("|")

        val result = splitToPassports(input)

        assertThat(result).containsExactlyInAnyOrder("x:y a:b")
    }

    @Test
    fun `should split two groups separated by a blank line into two passports`() {
        val input = """ |x:y
                        |a:b
                        | 
                        |c:d
        """.trimMargin("|")

        val result = splitToPassports(input)

        assertThat(result).containsExactlyInAnyOrder("x:y a:b", "c:d")
    }

    @Test
    fun `passport missing some fields is invalid`() {
        val passport = "iyr:b"

        assertThat(isPassportValid(passport)).isFalse()
    }

    @Test
    fun `passport with all fields is valid`() {
        val passport = listOf(
            "byr:x",
            "iyr:x",
            "eyr:x",
            "hgt:x",
            "hcl:x",
            "ecl:x",
            "pid:x",
            "cid:x"
        ).joinToString(" ")

        assertThat(isPassportValid(passport)).isTrue()


    }

    @Test
    @Disabled
    fun `find valid passwords in example`() {
        val input = """     |ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
                            |byr:1937 iyr:2017 cid:147 hgt:183cm
                            |
                            |iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
                            |hcl:#cfa07d byr:1929
                            |
                            |hcl:#ae17e1 iyr:2013
                            |eyr:2024
                            |ecl:brn pid:760753108 byr:1931
                            |hgt:179cm
                            |
                            |hcl:#cfa07d eyr:2025 pid:166559648
                            |iyr:2011 ecl:brn hgt:59in
        """.trimMargin()

        assertThat(findValidPassports(input)).containsExactlyInAnyOrder(
            "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd byr:1937 iyr:2017 cid:147 hgt:183cm",
            "hcl:#ae17e1 iyr:2013 eyr:2024 ecl:brn pid:760753108 byr:1931 hgt:179cm"
        )
    }

}