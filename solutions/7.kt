import java.util.*

abstract class Something (open val name: String) {
    abstract val size: Int
}
data class File(override val name: String, override val size: Int): Something(name)
data class Folder(override val name: String, val parent: Folder? = null, val children: MutableList<Something> = mutableListOf()): Something(name) {
    override val size: Int
        get() = children.sumOf { it.size }
    fun subfolder(name: String): Folder?
        = children.firstOrNull { it.name == name && it is Folder } as? Folder
    override fun toString() = "Folder(name=$name, children=$children)"
}

fun seventh() {
    val root = Folder("/")
    var current: Folder? = null
    while (true) {
        val ln = readlnOrNull() ?: break
        // println("Current: ${current?.name}")
        if (ln.startsWith("$ cd")) {
            val match = Regex("\\$ cd (.*)").matchEntire(ln) ?: break
            val dir = match.groupValues[1]
            current = if (dir == "/") root else {
                if (dir == "..") current?.parent
                else current?.subfolder(dir)
            }
            continue
        }
        if (ln == "$ ls") continue

        // listing current
        val dir = current ?: continue
        if (ln.startsWith("dir ")) {
            val match = Regex("dir (.*)").matchEntire(ln) ?: break
            val dirName = match.groupValues[1]
            dir.children.add(Folder(dirName, current))
        }
        else {
            val arr = ln.split(" ")
            val size = arr[0].toInt()
            val name = arr[1]
            dir.children.add(File(name, size))
        }
    }

    // part 1
    run {
        val validDirs = mutableListOf<Folder>()
        val q: Queue<Folder> = LinkedList()
        q.add(root)
        while (q.isNotEmpty()) {
            val dir = q.poll()
            if (dir.size < 100000)
                validDirs.add(dir)
            q.addAll(dir.children.mapNotNull { it as? Folder })
        }

        println(validDirs.map { it.name })
        println(validDirs.sumOf { it.size })
    }

    // part 2
    val unusedSpace = 70000000 - root.size
    val spaceForUpdate = 30000000 - unusedSpace

    val validDirs = mutableListOf<Folder>()
    val q: Queue<Folder> = LinkedList()
    q.add(root)
    while (q.isNotEmpty()) {
        val dir = q.poll()
        if (dir.size >= spaceForUpdate)
            validDirs.add(dir)
        q.addAll(dir.children.mapNotNull { it as? Folder })
    }
    val selectedDir = validDirs.minBy { it.size }
    println(selectedDir.name)
    println(selectedDir.size)
}
