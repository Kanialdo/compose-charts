package pl.krystiankaniowski.composecharts.internal

import kotlin.math.*

// Basing on Nice Label Algorithm for Charts with minimum ticks - https://stackoverflow.com/a/16363437/5796683

data class NiceScale constructor(
    val niceMin: Double,
    val niceMax: Double,
    val tickSpacing: Double,
) {

    private val decimalPower = abs(log10(tickSpacing).roundToInt())

    /** Calculate helper lines basic on min and max values of scale */
    fun getHelperLines(beforeMinValues: Int = 0, afterMaxValues: Int = 0): List<Double> {
        var v = niceMin - beforeMinValues * tickSpacing
        val limit = niceMax + afterMaxValues * tickSpacing
        val items = mutableListOf<Double>()
        while (v < limit) {
            v += tickSpacing
            // double check to prevent adding cheated values eg. 0.1 + 0.1 + 0.1 == 0.30000000000000004
            if (v <= limit) {
                items.add(v)
            }
        }
        return items
    }

    fun getHelperLinesFloat(): List<Float> = getHelperLines().map { it.toFloat() }

    /** Formats value in nice way basing on tickSpacing */
    fun formatValue(value: Double): String {
        return value.toBigDecimal()
            .setScale(decimalPower + 1, 6)
            .toString()
            .trimEnd('0').trimEnd { it == '.' || it == ',' }
    }
}

/** Calculate nice scale basic on min and max value */

internal fun niceScale(minPoint: Float, maxPoint: Float, maxTicks: Int = 10): NiceScale {

    val minPointDouble = minPoint.toDouble()
    val maxPointDouble = maxPoint.toDouble()

    val range = niceNum(maxPointDouble - minPointDouble, false)
    val tickSpacing = niceNum(range / (maxTicks - 1), true)
    val niceMin = floor(minPointDouble / tickSpacing) * tickSpacing
    val niceMax = ceil(maxPointDouble / tickSpacing) * tickSpacing

    return NiceScale(
        niceMin = niceMin,
        niceMax = niceMax,
        tickSpacing = tickSpacing,
    )
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