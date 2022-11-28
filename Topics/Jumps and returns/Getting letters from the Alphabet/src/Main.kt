fun main() {
    val letter = readln()
    for (ch in 'a'..'z') {
        if (ch == letter[0]) return
        print(ch)
    }
}