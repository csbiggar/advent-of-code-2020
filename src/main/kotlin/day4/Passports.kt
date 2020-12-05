package day4

import helpers.FileReader

private val input = FileReader("/day4/input.txt").readText()


private const val DELIMITER = "BREAK"

fun main() {
    val validPassports = findValidPassports(input)
    println(validPassports.size)
}

fun findValidPassports(input: String): List<String> {
    return splitToPassports(input).filter { isPassportValid(it) }
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

fun isPassportValid(passport: String): Boolean {

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


/*

xxx
xxx

xxx

xxx
xxx
xxx


 */