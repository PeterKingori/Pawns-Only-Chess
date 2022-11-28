fun main() {
    val report = readLine()!!
    val regex = Regex("\\d wrong answers?")
    println(regex.matches(report))
}