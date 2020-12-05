package day4

import helpers.FileReader

private val input = FileReader("/day4/input.txt").readText()

private const val DELIMITER = "BREAK"

/*
 D4#1 250 passports with all mandatory fields
 D4#2 158 passports with all mandatory fields & all data valid

 */

fun main() {
    val withMandatoryFields = findPassportsWithAllMandatoryFields(input)
    println(withMandatoryFields.size)

    val validPassports = findValidPassports(input)
    println(validPassports.size)
}

fun findPassportsWithAllMandatoryFields(input: String): List<String> {
    return splitToPassports(input).filter { hasAllMandatoryFields(it) }
}


fun findValidPassports(input: String): List<RawPassport> {
    return splitToPassports(input)
        .map { RawPassport.fromInputString(it) }
        .filter { it.isValid() }
}

fun splitToPassports(input: String): List<String> {

    return input.lines()
        .joinToString(" ") {
            if (it.isBlank()) {
                DELIMITER
            } else it
        }
        .split(" $DELIMITER ")
}

fun hasAllMandatoryFields(passport: String): Boolean {

    val mandatoryKeys = listOf(
        "byr",
        "iyr",
        "eyr",
        "hgt",
        "hcl",
        "ecl",
        "pid",
    )
    return mandatoryKeys.all { passport.contains("$it:") }
}


data class RawPassport(
    val byr: String?,
    val iyr: String?,
    val eyr: String?,
    val hgt: String?,
    val hcl: String?,
    val ecl: String?,
    val pid: String?
) {

    companion object {
        fun fromInputString(string: String): RawPassport {
            val propertyMap = string.split(" ")
                .map { it.split(":") }
                .map { it[0] to it[1] }
                .toMap()

            return RawPassport(
                byr = propertyMap["byr"],
                iyr = propertyMap["iyr"],
                eyr = propertyMap["eyr"],
                hgt = propertyMap["hgt"],
                hcl = propertyMap["hcl"],
                ecl = propertyMap["ecl"],
                pid = propertyMap["pid"],
            )
        }
    }

    fun isValid(): Boolean {
        val result =  numberIsValid(byr, 1920, 2002)
                && numberIsValid(iyr, 2010, 2020)
                && numberIsValid(eyr, 2020, 2030)
                && heightIsValid(hgt)
                && hairColourIsValid(hcl)
                && eyeColourIsValid(ecl)
                && passportIdIsValid(pid)
        return result
    }

    private fun numberIsValid(byr: String?, minimumInclusive: Int, maximumInclusive: Int): Boolean {
        val birthYear = byr?.toIntOrNull() ?: return false
        if (birthYear < minimumInclusive || birthYear > maximumInclusive) return false
        return true
    }

    private fun heightIsValid(hgt: String?): Boolean {
        if (hgt == null) return false
        val measurement = hgt.takeLast(2)
        val height = hgt.dropLast(2)

        return when (measurement) {
            "cm" -> numberIsValid(height, 150, 193)
            "in" -> numberIsValid(height, 59, 76)
            else -> false
        }
    }

    private fun eyeColourIsValid(ecl: String?): Boolean {
        val validColours = listOf(
            "amb",
            "blu",
            "brn",
            "gry",
            "grn",
            "hzl",
            "oth",
        )

        return validColours.contains(ecl)
    }

    private fun hairColourIsValid(hcl: String?): Boolean {
        if (hcl == null) return false
        val regex = """#[a-f0-9]{6}""".toRegex()

        return regex.matches(hcl)
    }

    private fun passportIdIsValid(pid: String?): Boolean {
        if (pid == null) return false
        val regex = """[0-9]{9}""".toRegex()
        return regex.matches(pid)
    }
}


/*

a:b c:d


 */