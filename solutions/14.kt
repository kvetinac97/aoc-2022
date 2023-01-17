import kotlin.math.max
import kotlin.math.min

fun fourteenth() {
    val posList = mutableMapOf<Pair<Int, Int>, Char>()
    var maxHeight = 0
    while (true) {
        val ln = readlnOrNull() ?: break
        val lines = ln.split(" -> ").map { coords ->
            coords.split(",").map { it.toInt() }.let { it[0] to it[1] }
        }
        val height = lines.maxOf { it.second }
        if (height > maxHeight)
            maxHeight = height
        val linesZipped = lines.zipWithNext()
        println(linesZipped)
        linesZipped.forEach { pair ->
            if (pair.first.first == pair.second.first)
                for (num in min(pair.first.second, pair.second.second) .. max(pair.first.second, pair.second.second))
                    posList[pair.first.first to num] = '#'
            else
                for (num in min(pair.first.first, pair.second.first) .. max(pair.first.first, pair.second.first))
                    posList[num to pair.first.second] = '#'
        }
    }

    println(maxHeight)
    while (true) {
        var sandPos = 500 to 0
        while (true) {
            if (sandPos.second > 1000) break
            val candidate = sandPos.copy(second = sandPos.second + 1)
            if (!posList.contains(candidate) && candidate.second < maxHeight + 2) { // remove second condition for part 1
                sandPos = candidate
                continue
            }
            val candidate2 = sandPos.copy(first = sandPos.first - 1, second = sandPos.second + 1)
            if (!posList.contains(candidate2) && candidate.second < maxHeight + 2) { // remove second condition for part 1
                sandPos = candidate2
                continue
            }
            val candidate3 = sandPos.copy(first = sandPos.first + 1, second = sandPos.second + 1)
            if (!posList.contains(candidate3) && candidate.second < maxHeight + 2) { // remove second condition for part 1
                sandPos = candidate3
                continue
            }
            break
        }
        if (sandPos.second > 1000) break
        posList[sandPos] = 'o'
        // println(sandPos)
        if (sandPos == 500 to 0) break
    }
    println(posList.count { it.value == 'o' })
}

