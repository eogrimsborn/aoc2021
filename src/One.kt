package aoc

import java.io.File

object One: Day(1) {
    override fun a(): String {
        val sonarReadings = File("inputs/input1.txt").readLines().map(String::toInt)
        val result = sonarReadings.windowed(2){ (first, second) -> if (first < second) 1 else 0}.sum()
        return "$result measurements are larger"
    }

    override fun b(): String {
        val sonarReadings = File("inputs/input1.txt").readLines().map(String::toInt)
        val sonarSums = sonarReadings.windowed(3){it.sum()}
        val result = sonarSums.windowed(2){ (first, second) -> if (first < second) 1 else 0}.sum()
        return "$result sums are larger"
    }
}