
fun third1() {
    var result = 0
    while (true) {
        val ln = readlnOrNull() ?: break
        val fr = ln.substring(0, ln.length / 2)
        val sc = ln.substring(ln.length / 2)
        val chr = fr.toCharArray().intersect(sc.toCharArray().asIterable().toSet()).first()
        val score = if (chr <= 'Z') chr.code - '&'.code else chr.code - '`'.code
        result += score
    }
    println(result)
}

fun third2() {
    var result = 0
    while (true) {
        val ln1 = readlnOrNull() ?: break
        val ln2 = readlnOrNull() ?: break
        val ln3 = readlnOrNull() ?: break
        val chr = ln1.toCharArray().intersect(ln2.toCharArray().asIterable().toSet())
            .intersect(ln3.toCharArray().asIterable().toSet()).first()
        val score = if (chr <= 'Z') chr.code - '&'.code else chr.code - '`'.code
        result += score
    }
    println(result)
}
