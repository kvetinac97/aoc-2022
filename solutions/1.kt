
fun first() {
    val sums = mutableListOf<Int>()
    var sum = 0
    while (true) {
        val ln = readlnOrNull() ?: break
        val num = ln.toIntOrNull()
        if (num == null) {
            sums.add(sum)
            sum = 0
            continue
        }
        sum += num
    }
    print(sums.apply { sortDescending() }.take(3).sum())
}