import kotlin.math.min

sealed class Expression {
    data class ANumber (val number: Int) : Expression() {
        override fun toString() = "$number"
    }
    data class AList   (val list: MutableList<Expression>) : Expression() {
        override fun toString() = list.toString()
    }
    fun lower(that: Expression): Int
        = compExpression(this, that)
}

fun compExpression(left: Expression, right: Expression): Int
    = when (left) {
        is Expression.ANumber -> when (right) {
            is Expression.ANumber -> left.number.compareTo(right.number)
            is Expression.AList -> compExpression(Expression.AList(mutableListOf(left)), right)
        }
        is Expression.AList -> when (right) {
            is Expression.ANumber -> compExpression(left, Expression.AList(mutableListOf(right)))
            is Expression.AList -> {
                var ix = 0
                var result = 0
                while (ix < min(left.list.size, right.list.size)) {
                    result = compExpression(left.list[ix], right.list[ix])
                    if (result != 0) break
                    ix += 1
                }

                if (result == 0) left.list.size.compareTo(right.list.size)
                else result
            }
        }
    }

fun parseExpression(str: String): Pair<Expression, String>? =
    when (str.first()) {
        '[' -> {
            val lst = mutableListOf<Expression>()
            var string = str.replaceFirst('[', ',')
            while (true) {
                if (string.first() != ',') break
                string = string.substring(1)
                val exp = parseExpression(string) ?: break
                lst.add(exp.first)
                string = exp.second
            }
            if (string.first() == ']') Expression.AList(lst) to string.substring(1)
            else null
        }
        else -> {
            val ix = str.indexOfFirst { !it.isDigit() }
            str.substring(0, ix).toIntOrNull()?.let { Expression.ANumber(it) to str.substring(ix) }
        }
    }

fun part1() {
    var i = 1
    var sum = 0
    while (true) {
        val frs = readlnOrNull() ?: break
        val sec = readlnOrNull() ?: throw RuntimeException("Could not read second")
        val empty = readlnOrNull() ?: throw RuntimeException("Could not read empty $frs $sec")
        if (empty != "") return
        val lst1 = parseExpression(frs) ?: throw RuntimeException("Could not parse $frs")
        val lst2 = parseExpression(sec) ?: throw RuntimeException("Could not parse $sec")
        if (lst1.first.lower(lst2.first) < 0)
            sum += i
        i += 1
    }
    println(sum)
}

fun part2() {
    val distress1: Expression = Expression.AList(mutableListOf(Expression.AList(mutableListOf(Expression.ANumber(2)))))
    val distress2: Expression = Expression.AList(mutableListOf(Expression.AList(mutableListOf(Expression.ANumber(6)))))
    val expressions = mutableListOf(distress1, distress2)
    while (true) {
        val ln = readlnOrNull() ?: break
        if (ln.isEmpty()) continue
        expressions.add(parseExpression(ln)?.first ?: throw RuntimeException("Could not parse $ln"))
    }
    expressions.sortWith { o1, o2 -> o1.lower(o2) }
    println((1 + expressions.indexOf(distress1)) * (1 + expressions.indexOf(distress2)))
}
