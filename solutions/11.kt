data class Monkey(val items: MutableList<Long>, val mod: Long, var inspected: Long, val test: (Long, Long) -> Pair<Long, Int>)

fun readMonkey(id: Int) : Monkey? {
    var ln = readlnOrNull() ?: return null
    if (ln != "Monkey $id:") return null
    ln = readlnOrNull() ?: return null
    if (!ln.startsWith("  Starting items: ")) return null
    val listItems = ln.substring(18)
    val items = listItems.split(", ").mapNotNull { it.toLongOrNull() }.toMutableList()
    ln = readlnOrNull() ?: return null
    if (!ln.startsWith("  Operation: new = ")) return null
    val operationText = ln.substring(19)
    val operationStr = if (operationText == "old * old") "pow"
    else if (operationText.startsWith("old *")) "times"
    else "plus"
    ln = readlnOrNull() ?: return null
    if (!ln.startsWith("  Test: divisible by ")) return null
    val divBy = ln.substring(21).toLongOrNull() ?: return null
    ln = readlnOrNull() ?: return null
    if (!ln.startsWith("    If true: throw to monkey ")) return null
    val trueMonkey = ln.substring(29).toIntOrNull() ?: return null
    ln = readlnOrNull() ?: return null
    if (!ln.startsWith("    If false: throw to monkey ")) return null
    val falseMonkey = ln.substring(30).toIntOrNull() ?: return null
    return Monkey(items, divBy,0L) { num,mod ->
        val result = when (operationStr) {
            "pow" -> num * num
            "times" -> num * operationText.substring(6).toLong()
            "plus" -> num + operationText.substring(6).toLong()
            else -> throw RuntimeException("Should not happen: $operationStr")
        }
        val divided = result % mod // (result / 3.0).toLong()
        return@Monkey divided to (if (divided % divBy == 0L) trueMonkey else falseMonkey)
    }
}

fun eleventh() {
    val monkeys = mutableListOf<Monkey>()
    while (true) {
        val monkey = readMonkey(monkeys.size) ?: break
        monkeys.add(monkey)
        readlnOrNull() ?: break
    }
    val lcm = monkeys.fold(1L) { acc,monkey -> acc * monkey.mod }

    repeat(20) {
    // repeat(10000) {
        monkeys.forEach { monkey ->
            monkey.items.forEach { item ->
                val (result, where) = monkey.test(item, lcm)
                monkeys[where].items.add(result)
                monkey.inspected += 1L
            }
            monkey.items.clear()
        }
    }
    monkeys.sortByDescending { it.inspected }
    println(monkeys.take(2).fold(1L) { acc,monkey -> acc * monkey.inspected })
}
