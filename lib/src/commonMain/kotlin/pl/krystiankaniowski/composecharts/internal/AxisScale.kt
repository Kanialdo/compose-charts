package pl.krystiankaniowski.composecharts.internal

import kotlin.math.*

class AxisScale private constructor(
    private val preciseMin: Double,
    private val preciseMax: Double,
    private val preciseTickSpacing: Double,
) {

    val min: Float = preciseMin.toFloat()
    val max: Float = preciseMax.toFloat()
    val tickSpacing: Float = preciseTickSpacing.toFloat()

    fun getHelperLines(): List<Float> {
        var v = preciseMin
        val limit = preciseMax
        val items = mutableListOf<Double>()
        while (v <= limit) {
            v += preciseTickSpacing
            // double check to prevent adding cheated values eg. 0.1 + 0.1 + 0.1 == 0.30000000000000004
            if (v <= limit) {
                items.add(v)
            }
        }
        return items.map { it.toFloat() }
    }

    /** Formats value in nice way basing on tickSpacing */
    @Suppress("ForbiddenComment")
    fun formatValue(value: Float): String {
        // TODO: restore nice formatting
        // private val _decimalPower = abs(log10(preciseTickSpacing).roundToInt())
        // return value.toBigDecimal()
        //     .setScale(_decimalPower + 1, 6)
        //     .toString()
        //     .trimEnd('0').trimEnd { it == '.' || it == ',' }
        return value.toString()
    }

    companion object {

        // Basing on Nice Label Algorithm for Charts with minimum ticks - https://stackoverflow.com/a/16363437/5796683
        /** Calculate nice scale basic on min and max value */
        internal fun create(min: Float, max: Float, maxTicks: Int = 10, forcedZero: Boolean = true): AxisScale {

            val minPointDouble = (if (forcedZero) minOf(min, 0f) else min).toDouble()
            val maxPointDouble = (if (forcedZero) maxOf(max, 0f) else max).toDouble()

            val range = niceNum(maxPointDouble - minPointDouble, false)
            val tickSpacing = niceNum(range / (maxTicks - 1), true)
            val niceMin = floor(minPointDouble / tickSpacing) * tickSpacing
            val niceMax = ceil(maxPointDouble / tickSpacing) * tickSpacing

            return AxisScale(
                preciseMin = niceMin,
                preciseMax = niceMax,
                preciseTickSpacing = tickSpacing,
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

@Suppress("MagicNumber")
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