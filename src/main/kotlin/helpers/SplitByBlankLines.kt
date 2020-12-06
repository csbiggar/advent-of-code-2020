package helpers

private const val DELIMITER = "BREAK"

fun String.splitByBlankLines(): List<String> {
    return lines()
        .joinToString(" ") {
            if (it.isBlank()) DELIMITER else it
        }
        .split(" $DELIMITER ")
}