package aoc

object Four : Day(4) {
    class BingoCoord(row: Int, column: Int, val value: Int, var drawn: Boolean = false) {
        val key = row to column
    }

    class BingoException(val board: BingoBoard, val drawNumber: Int) : Exception()
    class BingoBoard(data: List<String>) {
        private var bingoCache: Boolean? = null
        private val coordsByCoord: Map<Pair<Int, Int>, BingoCoord> = buildMap {
            data.forEachIndexed { row, rowData ->
                rowData.trim().split(Regex("""\s+""")).forEachIndexed { column, valueString ->
                    val coord = BingoCoord(row, column, valueString.toInt())
                    put(coord.key, coord)
                }
            }
        }
        private val coordsByValue: Map<Int, BingoCoord> = coordsByCoord.values.associateBy { it.value }
        private val rows: List<List<BingoCoord>> = buildList {
            for (row in 0 until 5) {
                add(
                    buildList {
                        for (col in 0 until 5) {
                            add(coordsByCoord[row to col]!!)
                        }
                    }
                )
            }
        }
        private val cols: List<List<BingoCoord>> = buildList {
            for (col in 0 until 5) {
                add(
                    buildList {
                        for (row in 0 until 5) {
                            add(coordsByCoord[row to col]!!)
                        }
                    }
                )
            }
        }

        init {
            assert(coordsByCoord.size == 25) { "Parsed board incorrectly" }
        }

        fun drawNumber(number: Int) {
            if (number in coordsByValue) {
                coordsByValue[number]?.drawn = true
                bingoCache = null
                if (hasBingo()) throw BingoException(this, number)
            }
        }

        fun hasBingo(): Boolean {
            if (bingoCache == null) {
                bingoCache = rows.any { row -> row.all { coord -> coord.drawn } } ||
                    cols.any { col -> col.all { coord -> coord.drawn } }
            }
            return bingoCache!!
        }

        fun calcWinning(drawNumber: Int): Int =
            coordsByCoord.values.filterNot { it.drawn }.sumOf { it.value } * drawNumber
    }

    override fun a(): String {
        val bingoNumbers = puzzleInput.first().split(",").map(String::toInt)
        val bingoBoards = puzzleInput
            .subList(1, puzzleInput.size)
            .chunked(6)
            .map {
                BingoBoard(it.subList(1, it.size))
            }

        try {
            bingoNumbers.forEach { drawNumber ->
                bingoBoards.forEach { board ->
                    board.drawNumber(drawNumber)
                }
            }
        } catch (e: BingoException) {
            val result = e.board.calcWinning(e.drawNumber)
            return "The bingo result is $result"
        }
        throw Exception("failed to bingo a board")
    }

    override fun b(): String {
        val bingoNumbers = puzzleInput.first().split(",").map(String::toInt)
        val bingoBoards = puzzleInput
            .subList(1, puzzleInput.size)
            .chunked(6)
            .map {
                BingoBoard(it.subList(1, it.size))
            }
        try {

            bingoNumbers.forEach { drawNumber ->
                bingoBoards.forEach { board ->
                    if (!board.hasBingo()) {
                        try {
                            board.drawNumber(drawNumber)
                        } catch (e: BingoException) {
                            if (bingoBoards.all { it.hasBingo() }) {
                                throw e
                            }
                        }
                    }
                }
            }
        } catch (e: BingoException) {
            val result = e.board.calcWinning(e.drawNumber)
            return "The bingo loser result is $result"
        }
        throw Exception("failed to bingo a board")
    }
}
