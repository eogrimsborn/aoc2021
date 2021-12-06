package aoc

import java.util.Collections

object Six : Day(6) {
    class Ocean() {
        private val fishByAge = MutableList(9) { 0L }

        fun addFish(age: Int = 6, amount: Long = 1) {
            fishByAge[age] += amount
        }

        fun advance() {
            val fishToAdd = fishByAge[0]
            Collections.rotate(fishByAge, -1)
            addFish(amount = fishToAdd)
        }

        fun amountOfFishInTheOcean(): Long = fishByAge.sum()
    }

    data class LanternFish(var timer: UInt = 6u) {
        fun age(): LanternFish? = when (timer) {
            0u -> {
                timer = 8u
                LanternFish()
            }
            else -> {
                timer -= 1u
                null
            }
        }
    }

    override fun a(): String {
        val fishies = puzzleInput.first().split(',').map(String::toInt).map { LanternFish(it.toUInt()) }.toMutableList()
        for (i in 1..80) {
            fishies.addAll(fishies.mapNotNull { it.age() })
        }
        return "After 80 days there are ${fishies.size} fishies"
    }

    override fun b(): String {
        val ocean = Ocean()
        puzzleInput.first().split(',').map(String::toInt).forEach { ocean.addFish(age = it) }
        for (i in 1..256) {
            ocean.advance()
        }
        return "After 256 days there are ${ocean.amountOfFishInTheOcean()} fishies"
    }
}
