fun main() {
    val text = Trajectory::class.java.getResource("trajectory.txt").readText()
    val lines = text.lines()
    val slopes = listOf(
        Slope(1, 1),
        Slope(3, 1),
        Slope(5, 1),
        Slope(7, 1),
        Slope(1, 2)
    )
    val trajectory = Trajectory(lines)
    val result = slopes.map(trajectory::getResult).reduce { acc, result -> acc * result }
    print("result: $result")
}

class Slope(val right: Int, val down: Int)

class Trajectory(
    private val lines: List<String>
) {
    fun getResult(slope: Slope): Long {
        var currentIndex = 0
        var result = 0L
        lines.asSequence()
            .withIndex()
            .filter { (index, _) -> index % slope.down == 0 }

        for (i in lines.indices step slope.down) {
            val line = lines[i]
            if (line[currentIndex] == '#') {
                result++
            }
            currentIndex = (currentIndex + slope.right) % line.length
        }
        return result
    }
}