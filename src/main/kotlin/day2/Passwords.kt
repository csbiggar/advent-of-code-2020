package day2

import helpers.FileReader

data class PasswordEntry(val password: String, val requiredLetter: Char, val minOccurrence: Int, val maxOccurrence: Int)

/*

D2#1 418 valid passwords

D2#2 616 valid passwords

 */
private val input = FileReader("/day2/input.txt").readLines()

fun main() {
    val result1 = findValidPasswordsUsingFirstRules(input)
    println("Using first rules: ${result1.size}")

    val result2 = findValidPasswordsUsingSecondRules(input)
    println("Using second rules: ${result2.size}")
}

fun findValidPasswordsUsingFirstRules(input: List<String>): List<PasswordEntry> {
    return mapToPasswordEntries(input)
        .filter {
            it.password.countLetter(it.requiredLetter) >= it.minOccurrence
                    && it.password.countLetter(it.requiredLetter) <= it.maxOccurrence
        }
}

fun findValidPasswordsUsingSecondRules(input: List<String>): List<PasswordEntry> {
    return mapToPasswordEntries(input)
        .filter { entry ->
            positionValues(entry).filter { it == entry.requiredLetter }.size == 1
        }

}

private fun positionValues(entry: PasswordEntry) = listOf(
    entry.password[entry.minOccurrence - 1],
    entry.password[entry.maxOccurrence - 1]
)

private fun mapToPasswordEntries(input: List<String>) = input.map {
    val bits = it.split(" ")
    PasswordEntry(
        minOccurrence = bits[0].substringBefore("-").toInt(),
        maxOccurrence = bits[0].substringAfter("-").toInt(),
        requiredLetter = bits[1].toCharArray().first(),
        password = bits[2]
    )
}


private fun String.countLetter(letter: Char) = this.count() - this.replace(letter.toString(), "").count()