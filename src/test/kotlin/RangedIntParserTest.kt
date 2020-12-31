import club.eridani.module.IntRangeValue
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun main() {
    println(Json.encodeToString(IntRangeValue(3, 0..5)))
}