package aoc


sealed class Day(val day: Int) {
    abstract fun a(): String
    abstract fun b(): String
}

val allDays = Day::class.sealedSubclasses
    .mapNotNull { it.objectInstance }
    .sortedBy { it.day }
