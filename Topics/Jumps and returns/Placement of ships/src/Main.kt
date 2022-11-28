import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    val rows = (1..5).toMutableList()
    val cols = (1..5).toMutableList()
    repeat(3) {
        rows.remove(scanner.nextInt())
        cols.remove(scanner.nextInt())
    }
    println(rows.joinToString(" "))
    println(cols.joinToString(" "))
}
