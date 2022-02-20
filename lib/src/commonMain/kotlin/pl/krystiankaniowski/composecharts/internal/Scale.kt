package pl.krystiankaniowski.composecharts.internal

import kotlin.math.log10
import kotlin.math.pow

fun calculateYHelperLines(yMin: Float, yMax: Float): List<Float> {
    val diff = (yMax - yMin)
    val log = log10(diff)
    val rounded = log.toInt()
    val base = (diff * 10f.pow(-rounded)).toInt()

    val step = when (base) {
        4, 8 -> 4
        0, 1, 2, 5, 10 -> 5
        3, 6 -> 6
        7, 9 -> -1
        else -> -1
    }

    return List(step) { i ->
        val temp = (yMin + (diff / step) * i)
        val tempLog = log10(temp).toInt()
        (temp * 10f.pow(-tempLog)).toInt() * 10f.pow(tempLog)
    }
}