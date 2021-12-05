package aoc

object Three : Day(3) {
    override fun a(): String {
        val width = puzzleInput[0].length
        val counts = MutableList(width) { 0 }
        puzzleInput.forEach {
            it.forEachIndexed { index, c ->
                if (c == '1') counts[index] += 1
            }
        }
        val midPoint = puzzleInput.size / 2

        val gamma = counts.map { it > midPoint }
        val epsilon = gamma.map { !it }

        val gammaNumber = gamma.map { if (it) '1' else '0' }.joinToString("").toInt(2)
        val epsilonNumber = epsilon.map { if (it) '1' else '0' }.joinToString("").toInt(2)

        return "The Gamma Epsilon product is ${gammaNumber * epsilonNumber}"
    }

    private fun countBits(rows: List<String>, position: Int): Pair<Int, Int> {
        return rows.fold(0 to 0) { acc, s ->
            when (s[position]) {
                '0' -> acc.first + 1 to acc.second
                '1' -> acc.first to acc.second + 1
                else -> throw Exception("Invalid diagnostic bit")
            }
        }
    }

    override fun b(): String {
        val width = puzzleInput[0].length
        val oxygenCodes = puzzleInput.toMutableList()
        for (i in 0..width) {
            if (oxygenCodes.size == 1) break
            val (zeros, ones) = countBits(oxygenCodes, i)
            val x = when {
                zeros > ones -> oxygenCodes.retainAll { it[i] == '0' }
                zeros < ones -> oxygenCodes.retainAll { it[i] == '1' }
                else -> oxygenCodes.retainAll { it[i] == '1' }
            }
        }
        val co2Codes = puzzleInput.toMutableList()
        for (i in 0..width) {
            if (co2Codes.size == 1) break
            val (zeros, ones) = countBits(co2Codes, i)
            val x = when {
                zeros > ones -> co2Codes.retainAll { it[i] == '1' }
                zeros < ones -> co2Codes.retainAll { it[i] == '0' }
                else -> co2Codes.retainAll { it[i] == '0' }
            }
        }
        val lifeSupportRating = oxygenCodes.first().toInt(2) * co2Codes.first().toInt(2)
        return "The life support rating is $lifeSupportRating"
    }
}
