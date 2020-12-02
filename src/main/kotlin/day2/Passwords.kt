package day2

import helpers.FileReader

data class PasswordEntry(val password: String, val requiredLetter: Char, val minOccurrence: Int, val maxOccurrence: Int)

/*

D2#1 418 valid passwords

 */
private val input = FileReader("/day2/input.txt").readLines()

fun main() {
    val result = findValidPasswords(input)
    println(result.size)
}


fun findValidPasswords(input: List<String>): List<PasswordEntry> {
    val passwordEntries = input.map {
        val bits = it.split(" ")
        PasswordEntry(
            minOccurrence = bits[0].substringBefore("-").toInt(),
            maxOccurrence = bits[0].substringAfter("-").toInt(),
            requiredLetter = bits[1].toCharArray().first(),
            password = bits[2]
        )
    }

    return passwordEntries
        .filter {
            it.password.countLetter(it.requiredLetter) >= it.minOccurrence
                    && it.password.countLetter(it.requiredLetter) <= it.maxOccurrence
        }
}


private fun String.countLetter(letter: Char) = this.count() - this.replace(letter.toString(), "").count()