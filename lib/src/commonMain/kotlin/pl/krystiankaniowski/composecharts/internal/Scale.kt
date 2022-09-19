package pl.krystiankaniowski.composecharts.internal

import kotlin.math.*

class Scale private constructor(
    val min: Float,
    val max: Float,
    private val tickSpacing: Float,
) {

    private val decimalPower = abs(log10(tickSpacing).roundToInt())

    fun getHelperLines(): List<Float> {
        var v = min
        val limit = max
        val items = mutableListOf<Float>()
        while (v <= limit) {
            v += tickSpacing
            // double check to prevent adding cheated values eg. 0.1 + 0.1 + 0.1 == 0.30000000000000004
            if (v <= limit) {
                items.add(v)
            }
        }
        return items
    }

    /** Formats value in nice way basing on tickSpacing */
    fun formatValue(value: Float): String {
        return value.toBigDecimal()
            .setScale(decimalPower + 1, 6)
            .toString()
            .trimEnd('0').trimEnd { it == '.' || it == ',' }
    }

    companion object {
        internal fun create(min: Float, max: Float, maxTicks: Int = 10, forcedZero: Boolean = true): Scale {

            val minPointDouble = (if (forcedZero) minOf(min, 0f) else min).toDouble()
            val maxPointDouble = (if (forcedZero) maxOf(max, 0f) else max).toDouble()

            val range = niceNum(maxPointDouble - minPointDouble, false)
            val tickSpacing = niceNum(range / (maxTicks - 1), true)
            val niceMin = floor(minPointDouble / tickSpacing) * tickSpacing
            val niceMax = ceil(maxPointDouble / tickSpacing) * tickSpacing

            return Scale(
                min = niceMin.toFloat(),
                max = niceMax.toFloat(),
                tickSpacing = tickSpacing.toFloat(),
            )
        }
    }
}


/**
 * Returns a "nice" number approximately equal to range Rounds
 * the number if round = true Takes the ceiling if round = false.
 *
 * @param range the data range
 * @param round whether to round the result
 * @return a "nice" number to be used for the data range
 */
private fun niceNum(range: Double, round: Boolean): Double {
    val exponent: Double = floor(log10(range))
    val fraction: Double = range / 10.0.pow(exponent)
    val niceFraction: Double = if (round) {
        if (fraction < 1.5) 1.0 else if (fraction < 3) 2.0 else if (fraction < 7) 5.0 else 10.0
    } else {
        if (fraction <= 1) 1.0 else if (fraction <= 2) 2.0 else if (fraction <= 5) 5.0 else 10.0
    }
    return niceFraction * 10.0.pow(exponent)
}