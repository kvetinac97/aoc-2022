
fun part1() {
    val ln = readln()
    val grid = List(ln.length) { mutableListOf<Int>() }
    var line = ln
    for (i in 1 .. line.length) {
        for (j in line.indices)
            grid[i - 1].add(line[j].digitToInt())
        line = readlnOrNull() ?: break
    }
    grid.forEach { println(it) }

    var visible = 0
    for (i in ln.indices) {
        for (j in ln.indices) {
            var vis = 1

            if (i == 0 || i+1 == ln.length || j == 0 || j+1 == ln.length) {
                visible += 1
                continue
            }

            var visL = 1
            for (ix in 0 until i) {
                if (grid[ix][j] >= grid[i][j]) {
                    // println("${grid[ix][j]} is taller than ${grid[i][j]}")
                    visL = 0
                    break
                }
            }

            var visR = 1
            for (ix in i + 1 until ln.length) {
                if (grid[ix][j] >= grid[i][j]) {
                    // println("${grid[ix][j]} is taller than ${grid[i][j]}")
                    visR = 0
                    break
                }
            }

            var visU = 1
            for (jx in 0 until j) {
                if (grid[i][jx] >= grid[i][j]) {
                    // println("${grid[i][jx]} is taller than ${grid[i][j]}")
                    visU = 0
                    break
                }
            }

            var visB = 1
            for (jx in j + 1 until ln.length) {
                if (grid[i][jx] >= grid[i][j]) {
                    // println("${grid[i][jx]} is taller than ${grid[i][j]}")
                    visB = 0
                    break
                }
            }

            if (visL == 1 || visR == 1 || visB == 1 || visU == 1) {
                // println("${grid[i][j]} is visible")
                visible += 1
            }
        }
    }
    println("Visible: $visible")
}

fun part2() {
    val ln = readln()
    val grid = List(ln.length) { mutableListOf<Int>() }
    var line = ln
    for (i in 1 .. line.length) {
        for (j in line.indices)
            grid[i - 1].add(line[j].digitToInt())
        line = readlnOrNull() ?: break
    }
    grid.forEach { println(it) }

    var topScore = 0
    for (i in ln.indices) {
        for (j in ln.indices) {
            var visL = 0
            for (ix in i - 1 downTo 0) {
                visL += 1
                if (grid[ix][j] >= grid[i][j]) break
            }

            var visR = 0
            for (ix in i + 1 until ln.length) {
                visR += 1
                if (grid[ix][j] >= grid[i][j]) break
            }

            var visU = 0
            for (jx in j - 1 downTo 0) {
                visU += 1
                if (grid[i][jx] >= grid[i][j]) break
            }

            var visB = 0
            for (jx in j + 1 until ln.length) {
                visB += 1
                if (grid[i][jx] >= grid[i][j]) break
            }

            val score = visL * visR * visU * visB
            if (score > topScore)
                topScore = score
            // println("Score for $i, $j (${grid[i][j]}): $score ($visL $visU $visB $visR)")
        }
    }
    println("Top: $topScore")
}
