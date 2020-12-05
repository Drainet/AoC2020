fun main() {
    val text = PassportProcessor::class.java.getResource("seats.txt").readText()
    val seats = BinaryBoarding().getSeats(text.lines())
    for (seat in seats) {
        println(seat)
    }
    seats.forEachIndexed { index, list ->
        println("index: $index, $list")
    }
}

class BinaryBoarding {

    private val seats: List<MutableList<Int>> by lazy {
        val seats = mutableListOf<MutableList<Int>>()
        for (i in 0 until 127) {
            seats.add(MutableList(8) { 0 })
        }
        return@lazy seats
    }

    fun getSeats(lines: List<String>): List<List<Int>> {
        for (line in lines) {
            val binary = line.map { char ->
                when (char) {
                    'F', 'L' -> '0'
                    'B', 'R' -> '1'
                    else -> error("not fbrl : ${char}")
                }
            }
            val first = binary.subList(0, binary.size - 3)
            val second = binary.subList(binary.size - 3, binary.size)
            val firstNum = Integer.parseInt(first.joinToString(""), 2)
            val secondNum = Integer.parseInt(second.joinToString(""), 2)
            seats[firstNum][secondNum] = 1
        }
        return seats
    }
}