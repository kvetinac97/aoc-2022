
fun fourth() {
    var same = 0
    var overlap = 0
    while (true) {
        val ln = readlnOrNull()?.split(",") ?: break
        val fr = ln[0].split("-")
        val sc = ln[1].split("-")
        val frLow = fr[0].toInt()
        val frUpp = fr[1].toInt()
        val scLow = sc[0].toInt()
        val scUpp = sc[1].toInt()
        if ((frLow >= scLow && frUpp <= scUpp) ||
            (scLow >= frLow && scUpp <= frUpp))
            same += 1
        var was = 0
        (frLow..frUpp).forEach {
            if (it in scLow..scUpp)
                was = 1
        }
        overlap += was
    }
    println(same)
    println(overlap)
}
