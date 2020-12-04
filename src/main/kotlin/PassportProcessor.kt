fun main() {
    val text = PassportProcessor::class.java.getResource("passports.txt").readText()
    print("result : ${PassportProcessor().getValidCount(text)}")
}

class PassportProcessor {

    private val validFields = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

    fun getValidCount(text: String): Int {
        val passports = text.lines().fold(mutableListOf(Passport())) { list, line ->
            if (line.isBlank()) {
                list.add(Passport())
            } else {
                val kvs = line.split(" ")
                for (kv in kvs) {
                    list.last().fields[kv.split(":")[0]] = kv.split(":")[1]
                }
            }
            return@fold list
        }
        return passports.count { it.hasRequiredFields && it.fieldsFormatValid }
    }

    private inner class Passport {
        val fields = mutableMapOf<String, String>()
        val hasRequiredFields: Boolean
            get() = fields.keys.containsAll(validFields)
        val fieldsFormatValid: Boolean
            get() = fields.all { (key, value) ->
                return@all when (key) {
                    "byr" -> value.isInRange(1920..2002)
                    "iyr" -> value.isInRange(2010..2020)
                    "eyr" -> value.isInRange(2020..2030)
                    "hgt" -> when {
                        value.endsWith("in") -> value.substring(0, value.length - 2).isInRange(59..76)
                        value.endsWith("cm") -> value.substring(0, value.length - 2).isInRange(150..193)
                        else -> false
                    }
                    "hcl" -> value.startsWith("#") &&
                            value.length == 7 &&
                            value.substring(1).all { it.isDigit() || it in 'a'..'z' }
                    "ecl" -> setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(value)
                    "pid" -> value.length == 9 && value.all(Char::isDigit)
                    "cid" -> true
                    else -> false
                }
            }

        private fun String.isInRange(range: IntRange): Boolean =
            when (toIntOrNull()) {
                in range -> true
                else -> false
            }
    }
}