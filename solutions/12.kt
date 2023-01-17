import java.util.LinkedList
import java.util.Queue

fun MutableList<String>.get(pair: Pair<Int, Int>)
    = this[pair.first][pair.second]

fun bfs(maze: MutableList<String>, start: Pair<Int, Int>, end: Pair<Int, Int>): Int? {
    val q: Queue<Pair<Pair<Int,Int>, Int>> = LinkedList()
    val visited = mutableSetOf<Pair<Int, Int>>()
    q.add(start to 0)
    while (!q.isEmpty()) {
        val el = q.poll()
        if (visited.contains(el.first)) continue

        // println("Visiting $el ${maze.get(el.first)}")
        visited.add(el.first)
        if (el.first == end)
            return el.second // ende
        val a1 = el.first.copy(first = el.first.first + 1)
        if (el.first.first + 1 < maze.size && maze.get(a1) - maze.get(el.first) < 2) // good diff
            q.add(a1 to el.second + 1)
        val a2 = el.first.copy(first = el.first.first - 1)
        if (el.first.first > 0 && maze.get(a2) - maze.get(el.first) < 2) // good diff
            q.add(a2 to el.second + 1)
        val b1 = el.first.copy(second = el.first.second + 1)
        if (el.first.second + 1 < maze[0].length && maze.get(b1) - maze.get(el.first) < 2) // good diff
            q.add(b1 to el.second + 1)
        val b2 = el.first.copy(second = el.first.second - 1)
        if (el.first.second > 0 && maze.get(b2) - maze.get(el.first) < 2) // good diff
            q.add(b2 to el.second + 1)
    }
    return null
}

fun twelfth() {
    val maze = mutableListOf<String>()
    var start = 0 to 0
    var end = 0 to 0
    while (true) {
        val ln = readlnOrNull() ?: break
        val ix = ln.indexOf('S')
        if (ix != -1) start = maze.size to ix
        val ixx = ln.indexOf('E')
        if (ixx != -1) end = maze.size to ixx
        maze.add(ln.replace('S', 'a').replace('E', 'z'))
    }

    // Part 1
    println(bfs(maze, start, end))

    // Part 2
    var lowest = Int.MAX_VALUE
    maze.forEachIndexed { i,str ->
        str.forEachIndexed { j,chr ->
            if (chr != 'a') return@forEachIndexed
            bfs(maze, i to j, end)?.let { if (it < lowest) lowest = it }
        }
    }
    println(lowest)
}
