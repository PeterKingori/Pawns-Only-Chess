fun main() {
    val input = readln()
    for (ch in input) {
        if (ch.isDigit()) {
            println(ch)
            break
        }
    }
}