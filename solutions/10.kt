import kotlin.math.abs

var cycle = 0
var register = 1
val helpers = arrayOf(20, 60, 100, 140, 180, 220)
var score = 0
var image = ""

fun inc() {
    if (register < 240) {
        image += if (abs(register - (cycle % 40)) < 2) '#' else '.'
        if (cycle % 40 == 39) image += "\n"
    }

    cycle += 1
    if (helpers.contains(cycle))
        score += (cycle * register)
}

fun tenth() {
    while (true) {
        val ln = readlnOrNull() ?: break
        when (ln.substring(0, 4)) {
            "addx" -> {
                inc()
                inc()
                register += ln.substring(5).toInt()
            }
            "noop" -> inc()
            else -> throw RuntimeException("Should not happen")
        }
    }
    println(score)
    println(image)
}
