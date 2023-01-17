import java.lang.System.exit
import kotlin.streams.asStream

data class Tower (val blocks: MutableList<Char>)

fun fifth() {
    val towers = Array(9) {
        Tower(mutableListOf())
    }

    while (true) {
        val ln = readln()
        if (ln.isEmpty()) break
        val lnAlt = " $ln "
        towers.forEachIndexed { i, tower ->
            val ss = lnAlt.substring(i * 4, (i + 1) * 4).removePrefix(" ")
            if (!ss.matches(Regex("\\[.]")))
                return@forEachIndexed
            tower.blocks.add(ss[1])
        }
    }
    println(towers.map { it.blocks })
    while (true) {
        val ln = readlnOrNull() ?: break
        val match = Regex("move (.*) from (\\d)* to (\\d)*")
        val cnt = match.find(ln)!!.groupValues ?: break
        println(cnt)
        val count = cnt[1].toInt()
        val from = cnt[2].toInt() - 1
        val to = cnt[3].toInt() - 1
        println("Moving $count from $from (${towers[from].blocks.size}) to $to (${towers[to].blocks.size})")
        towers[to].blocks.addAll(0, towers[from].blocks.subList(0, count))
        for (i in 1..count) {
            towers[from].blocks.removeFirst()
        }
        println(towers.map { it.blocks })
        println("Moved $count from $from (${towers[from].blocks.size}) to $to (${towers[to].blocks.size})")
    }

    println(towers.map { it.blocks.first() }.joinToString(""))
}
