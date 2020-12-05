package day4

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class PassportTest {

    @Test
    fun `should map from string`() {
        val passport = listOf(
            "byr:1",
            "iyr:2",
            "eyr:3",
            "hgt:4",
            "hcl:5",
            "ecl:6",
            "pid:7"
        ).joinToString(" ")

        val expected = RawPassport(
            byr = "1",
            iyr = "2",
            eyr = "3",
            hgt = "4",
            hcl = "5",
            ecl = "6",
            pid = "7"
        )

        assertThat(RawPassport.fromInputString(passport)).isEqualTo(expected)
    }


    @Test
    fun `testing testing`() {

    }

    @Test
    fun `should map passport with missing values as Invalid from string`() {
        val passport = "hgt:4"

        val expected = RawPassport(
            byr = null,
            iyr = null,
            eyr = null,
            hgt = "4",
            hcl = null,
            ecl = null,
            pid = null
        )
        assertThat(RawPassport.fromInputString(passport)).isEqualTo(expected)
    }

    @Test
    fun `passport with any missing fields is invalid`() {
        val passport = validPassport().copy(hcl = null)

        assertThat(passport.isValid()).isFalse()
    }

    @Test
    fun `valid passport`() {
        assertThat(validPassport().isValid()).isTrue()
    }

    @Nested
    inner class BirthYearValidation() {
        @Test
        fun `birth year not a number is invalid`() {
            val passport = validPassport().copy(byr = "x")
            assertThat(passport.isValid()).isFalse()
        }

        @Test
        fun `birth year before 1920 is invalid`() {
            val passport = validPassport().copy(byr = "1919")
            assertThat(passport.isValid()).isFalse()
        }

        @Test
        fun `birth year after 2002 is invalid`() {
            val passport = validPassport().copy(byr = "2003")
            assertThat(passport.isValid()).isFalse()
        }

    }

    @Nested
    inner class IssueYearValidation() {
        @Test
        fun `issue year not a number is invalid`() {
            val passport = validPassport().copy(iyr = "x")
            assertThat(passport.isValid()).isFalse()
        }

        @Test
        fun `issue year before 2010 is invalid`() {
            val passport = validPassport().copy(iyr = "2009")
            assertThat(passport.isValid()).isFalse()
        }

        @Test
        fun `issue year after 2020 is invalid`() {
            val passport = validPassport().copy(iyr = "2021")
            assertThat(passport.isValid()).isFalse()
        }

    }

    @Nested
    inner class ExpirationYearValidation() {
        @Test
        fun `expiration year not a number is invalid`() {
            val passport = validPassport().copy(eyr = "x")
            assertThat(passport.isValid()).isFalse()
        }

        @Test
        fun `expiration year before 2020 is invalid`() {
            val passport = validPassport().copy(eyr = "2019")
            assertThat(passport.isValid()).isFalse()
        }

        @Test
        fun `expiration year after 2030 is invalid`() {
            val passport = validPassport().copy(eyr = "2031")
            assertThat(passport.isValid()).isFalse()
        }
    }

    @Nested
    inner class HeightValidation {

        @ParameterizedTest
        @CsvSource(
            "70,false",
            "70xx,false",

            "149cm,false",
            "150cm,true",
            "193cm,true",
            "194cm,false",

            "58in,false",
            "59in,true",
            "76in,true",
            "77in,false"
        )
        fun `height has measurement and is in bounds`(height: String, expectedIsValid: Boolean) {
            val passportOther = validPassport().copy(hgt = height)
            assertThat(passportOther.isValid()).isEqualTo(expectedIsValid)
        }
    }

    @Nested
    inner class HairColourValidation {
        @ParameterizedTest
        @CsvSource(
            ",#123abc,true",
            ",#b6652a,true",
            ",#123abz,false",
            ",123abc,false",
            ",#123abc#123abc,false",
            ",,false",
        )
        fun `should be a valid haircolour`(fakeToForceCsvParsingToHandleStringsStartingWithHash: String?, colour: String?, expectedIsValid: Boolean) {
            val passportOther = validPassport().copy(hcl = colour)
            assertThat(passportOther.isValid()).isEqualTo(expectedIsValid)
        }
    }

    @Nested
    inner class EyeColourValidation {
        @ParameterizedTest
        @CsvSource(
            "amb,true",
            "blu,true",
            "brn,true",
            "gry,true",
            "grn,true",
            "hzl,true",
            "oth,true",
            "x,false",
            ",false",
        )
        fun `should be a valid eyecolour`(colour: String?, expectedIsValid: Boolean) {
            val passportOther = validPassport().copy(ecl = colour)
            assertThat(passportOther.isValid()).isEqualTo(expectedIsValid)
        }
    }

    @Nested
    inner class PassportIdValidation {
        @ParameterizedTest
        @CsvSource(
            "000000001,true",
            "x,false",
            "00000001,false",
            "0000000001,false",
            ",false",
        )
        fun `should be a valid passportId`(id: String?, expectedIsValid: Boolean) {
            val passportOther = validPassport().copy(pid = id)
            assertThat(passportOther.isValid()).isEqualTo(expectedIsValid)
        }
    }


    /*


    byr (Birth Year) - four digits; at least 1920 and at most 2002.
iyr (Issue Year) - four digits; at least 2010 and at most 2020.
eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
hgt (Height) - a number followed by either cm or in:
If cm, the number must be at least 150 and at most 193.
If in, the number must be at least 59 and at most 76.
hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
pid (Passport ID) - a nine-digit number, including leading zeroes.
cid (Country ID) - ignored, missing or not.

     */


}

private fun validPassport() = RawPassport(
    byr = "2000",
    iyr = "2015",
    eyr = "2025",
    hgt = "190cm",
    hcl = "#123abc",
    ecl = "blu",
    pid = "000000001"
)