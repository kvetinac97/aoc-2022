
fun second() {
    var total1 = 0
    var total2 = 0
    while (true) {
        val ln = readlnOrNull()?.split(" ") ?: break
        val elf = ln[0][0].code - '@'.code
        val you = ln[1][0].code - 'W'.code

        val score1 = ((3 + you - elf + 1) % 3) * 3 + you
        total1 += score1

        val your = (elf + you) % 3 + 1
        val score2 = ((3 + your - elf + 1) % 3) * 3 + your
        total2 += score2
    }
    println(total1)
    println(total2)
}
