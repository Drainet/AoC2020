fun main() {
    val text = Sum2020::class.java.getResource("sum2020.txt").readText()
    val nums = text.lines().map(String::toInt)
    val result = Sum2020().getResult(nums)
    print("result: $result")
}

class Sum2020 {
    fun getResult(nums: List<Int>): Int {
        val sorted = nums.sorted()
        for (i in 0 until (sorted.size - 2)) {
            val target = 2020 - sorted[i]
            var left = i + 1
            var right = sorted.size - 1
            while (right > left) {
                val currentSum = sorted[left] + sorted[right]
                when {
                    currentSum > target -> right--
                    currentSum == target -> return sorted[i] * sorted[left] * sorted[right]
                    else -> left++
                }
            }
        }
        return -1
    }
}