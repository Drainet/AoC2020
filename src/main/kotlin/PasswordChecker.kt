fun main() {
    val result = PasswordChecker().getResult()
    print("result: $result")
}

class PasswordChecker {
    fun getResult(): Int {
        val text = PasswordChecker::class.java.getResource("passwords.txt").readText()
        val lines = text.lines()
        var result = 0
        for (line in lines) {
            val parts = line.split(" ")
            val firstParts = parts[0].split("-")
            val indexes = listOf(firstParts[0].toInt() - 1, firstParts[1].toInt() - 1)
            val char = parts[1][0]
            var count = 0
            val string = parts[2]
            for (index in indexes) {
                if (string[index] == char) {
                    count++
                }
            }
            if (count == 1) {
                result++
            }
        }
        return result
    }
}
