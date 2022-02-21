package pl.krystiankaniowski.composecharts.internal

import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.roundToInt

internal fun calculateYHelperLines(yMin: Float, yMax: Float): List<Float> {
    val diff = (yMax - yMin)
    val log = log10(diff)
    val rounded = log.toInt()
    val base = (diff * 10f.pow(-rounded)).toInt()

    val step = when (base) {
        4, 8 -> 4
        0, 1, 2, 5, 10 -> 5
        3, 6 -> 6
        7 -> 7
        9 -> 9
        else -> -1
    }

    return List(step) { i ->
        val temp = (yMin + (diff / step) * (i + 1))
        if (temp == 0f) {
            0f
        } else {
            val tempLog = log10(temp).toInt()
            val value = (temp * 10f.pow(-tempLog)).roundToInt() * 10f.pow(tempLog)
            value
        }
    }
}