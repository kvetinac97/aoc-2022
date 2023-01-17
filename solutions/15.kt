import kotlin.math.abs

const val BLOCKED_Y = 2000000

fun main() {
    val pairs = mutableSetOf<Pair<Pair<Int,Int>, Pair<Int,Int>>>()
    var minX = Int.MAX_VALUE
    var maxX = Int.MIN_VALUE
    while (true) {
        val ln = readlnOrNull() ?: break
        val matches = Regex("Sensor at x=(.*), y=(.*): closest beacon is at x=(.*), y=(.*)")
            .matchEntire(ln)?.groupValues ?: break
        val sensor = matches[1].toInt() to matches[2].toInt()
        val beacon = matches[3].toInt() to matches[4].toInt()
        pairs.add(sensor to beacon)

        println("$sensor: $beacon")
        val maxDiff = abs(sensor.first - beacon.first) + abs(sensor.second - beacon.second)
        val smallX = sensor.first - maxDiff
        val greatX = sensor.first + maxDiff
        if (smallX < minX)
            minX = smallX
        if (greatX > maxX)
            maxX = greatX
    }

    var blocked = 0
    for (x in minX .. maxX) {
        for (pair in pairs) {
            val sensor = pair.first
            val beacon = pair.second
            if (x to BLOCKED_Y == beacon)
                break
            val beaconDiff = abs(sensor.first - beacon.first) + abs(sensor.second - beacon.second)
            val sensorDiff = abs(sensor.first - x) + abs(sensor.second - BLOCKED_Y)
            if (sensorDiff <= beaconDiff) {
                blocked += 1
                break
            }
        }
    }
    println("Range: $minX to $maxX")
    println("Blocked: $blocked")

    for (x in 0 .. BLOCKED_Y * 2) {
        var y = 0
        while (y <= BLOCKED_Y * 2) {
            var close: Pair<Pair<Int,Int>,Int>? = null
            for (pair in pairs) {
                val sensor = pair.first
                val beacon = pair.second
                val beaconDiff = abs(sensor.first - beacon.first) + abs(sensor.second - beacon.second)
                val sensorDiff = abs(sensor.first - x) + abs(sensor.second - y)
                if (sensorDiff <= beaconDiff) {
                    close = sensor to beaconDiff
                    break
                }
            }
            if (close == null) {
                println("Win: ${x to y} ${4000000L*x + y}")
                break
            }
            else
                y = close.first.second + close.second - abs(close.first.first - x) + 1
        }

    }
}

