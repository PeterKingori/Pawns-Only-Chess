fun main() {
    val input = readln()
    for (ch in 'a'..'z') {
        if (input.contains(ch)) continue
        print(ch)
    }
}