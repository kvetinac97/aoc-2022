import kotlin.math.abs
import kotlin.math.sign

val headPos = Pos(0, 0)
val tailPos = headPos.copy()
val visited = mutableSetOf(tailPos)

data class Pos(var x: Int, var y: Int)

fun move1(xDiff: Int, yDiff: Int) {
    headPos.x += xDiff
    headPos.y += yDiff

    val move = when (abs(headPos.x - tailPos.x) to abs(headPos.y - tailPos.y)) {
        1 to 0 -> 0 to 0
        0 to 1 -> 0 to 0
        1 to 1 -> 0 to 0
        0 to 0 -> 0 to 0
        2 to 0 -> (headPos.x - tailPos.x).sign to 0
        0 to 2 -> 0 to (headPos.y - tailPos.y).sign
        1 to 2 -> (headPos.x - tailPos.x).sign to (headPos.y - tailPos.y).sign
        2 to 1 -> (headPos.x - tailPos.x).sign to (headPos.y - tailPos.y).sign
        else -> throw RuntimeException("Should not happen")
    }

    tailPos.x += move.first
    tailPos.y += move.second

    visited.add(tailPos.copy())
}

fun part1() {
    while (true) {
        val ln = readlnOrNull() ?: break
        when(ln.first()) {
            'L' -> repeat(ln.substring(2).toInt()) { move1(1, 0) }
            'R' -> repeat(ln.substring(2).toInt()) { move1(-1, 0) }
            'U' -> repeat(ln.substring(2).toInt()) { move1(0, 1) }
            'D' -> repeat(ln.substring(2).toInt()) { move1(0, -1) }
        }
    }
    println(visited.size)
}

const val MAX = 9
val positions = List(MAX + 1) { Pos(0, 0) }
val visited2 = mutableSetOf(positions[MAX])

fun move2(xDiff: Int, yDiff: Int) {
    positions[0].x += xDiff
    positions[0].y += yDiff
    for (i in 0 until MAX) {
        val headPos = positions[i]
        val tailPos = positions[i + 1]

        val move = when (abs(headPos.x - tailPos.x) to abs(headPos.y - tailPos.y)) {
            1 to 0 -> 0 to 0
            0 to 1 -> 0 to 0
            1 to 1 -> 0 to 0
            0 to 0 -> 0 to 0
            2 to 0 -> (headPos.x - tailPos.x).sign to 0
            0 to 2 -> 0 to (headPos.y - tailPos.y).sign
            1 to 2 -> (headPos.x - tailPos.x).sign to (headPos.y - tailPos.y).sign
            2 to 1 -> (headPos.x - tailPos.x).sign to (headPos.y - tailPos.y).sign
            2 to 2 -> (headPos.x - tailPos.x).sign to (headPos.y - tailPos.y).sign
            else -> throw RuntimeException("Should not happen")
        }

        tailPos.x += move.first
        tailPos.y += move.second
    }

    visited2.add(positions[MAX].copy())
}


fun part2() {
    while (true) {
        val ln = readlnOrNull() ?: break
        when(ln.first()) {
            'L' -> repeat(ln.substring(2).toInt()) { move2(1, 0) }
            'R' -> repeat(ln.substring(2).toInt()) { move2(-1, 0) }
            'U' -> repeat(ln.substring(2).toInt()) { move2(0, 1) }
            'D' -> repeat(ln.substring(2).toInt()) { move2(0, -1) }
        }
    }
    println(visited2.size)
}
