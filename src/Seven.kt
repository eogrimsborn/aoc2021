package aoc

import kotlin.math.abs

object Seven : Day(7) {
    fun fuelUsage(distance: Int): Int = when {
        distance > 1 -> distance * (distance + 1) / 2
        else -> distance
    }

    override fun a(): String {
        // The median is where they should align
        val startingPoints = puzzleInput.first().split(',').map(String::toInt)
        val alignAtPoint = startingPoints
            .sorted()
            .let { it[(it.size - 1) / 2] }
        val fuelUsed = startingPoints.sumOf { abs(it - alignAtPoint) }
        return "The fuel usage is $fuelUsed"
    }

    override fun b(): String {
        val startingPoints = puzzleInput.first().split(',').map(String::toInt).sorted()
        val alignAtPoint = (startingPoints.first()..startingPoints.last())
            .minByOrNull { alignmentPoint ->
                startingPoints.sumOf { startPoint ->
                    fuelUsage(abs(alignmentPoint - startPoint))
                }
            }!!
        println("alignAtPoint $alignAtPoint")
        val fuelUsed = startingPoints.sumOf { startPoint -> fuelUsage(abs(alignAtPoint - startPoint)) }
        return "The fuel usage is $fuelUsed"
    }
}
