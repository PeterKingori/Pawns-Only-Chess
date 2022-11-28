fun main() {
    val urlParams = readln().split("?").last().splitToSequence("&").toList()
    var password = ""
    urlParams.forEach {
        val (key, value) = it.split("=")
        if (key == "pass") password = value
        println("$key : ${value.ifEmpty { "not found" }}")
    }
    if (password.isNotEmpty()) println("password : $password")
}