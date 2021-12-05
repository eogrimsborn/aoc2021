package aoc

object Five : Day(5) {
    val exampleInput = listOf(
        "0,9 -> 5,9",
        "8,0 -> 0,8",
        "9,4 -> 3,4",
        "2,2 -> 2,1",
        "7,0 -> 7,4",
        "6,4 -> 2,0",
        "0,9 -> 2,9",
        "3,4 -> 1,4",
        "0,0 -> 8,8",
        "5,5 -> 8,2",
    )
    private val dataRegex = Regex("""(\d+),(\d+) -> (\d+),(\d+)""")

    data class Coord(val x: Int, val y: Int)

    private fun parseLine(data: String, allowDiagonal: Boolean = false): List<Coord> {
        // data: 432,708 -> 432,160
        val match = dataRegex.matchEntire(data) ?: throw Exception("Invalid coordinate")
        val (x1, y1, x2, y2) = match.destructured.toList().map(String::toInt)
        return when {
            x1 == x2 -> buildList { for (y in minOf(y1, y2)..maxOf(y1, y2)) add(Coord(x1, y)) }
            y1 == y2 -> buildList { for (x in minOf(x1, x2)..maxOf(x1, x2)) add(Coord(x, y1)) }
            !allowDiagonal -> emptyList()
            else -> {
                val xProgression = if (x1 < x2) x1..x2 else x1 downTo x2
                val yProgression = if (y1 < y2) y1..y2 else y1 downTo y2
                xProgression.zip(yProgression).map { (x, y) -> Coord(x, y) }
            }
        }
    }
    override fun a(): String {
        val result = puzzleInput
            .map(::parseLine)
            .flatten()
            .groupingBy { it }
            .eachCount()
            .filter { it.value >= 2 }
            .size
        return "There are $result coordinates with 2 or more vents"
    }

    override fun b(): String {
        val result = puzzleInput
            .map { parseLine(it, true) }
            .flatten()
            .groupingBy { it }
            .eachCount()
            .filter { it.value >= 2 }
            .size
        return "There are $result coordinates with 2 or more vents"
    }
}
