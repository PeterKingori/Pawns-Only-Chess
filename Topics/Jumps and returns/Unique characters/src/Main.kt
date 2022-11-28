fun main() {
    val word = readln()
    val charCount = word.filter { ch -> word.count { it == ch } == 1 }
    println(charCount.length)
}