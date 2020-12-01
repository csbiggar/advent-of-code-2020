@file:JvmName("FileReader")

package helpers

import java.io.File

class FileReader(fileName: String) {
    private val file = File(FileReader::class.java.getResource(fileName).file)

    fun readLines(): List<String> {
        return file.readLines()
    }

    fun readInts(): List<Int> {
        return file.readLines().map { it.toInt() }
    }
}