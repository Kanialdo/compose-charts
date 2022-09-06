package pl.krystiankaniowski.composecharts.internal

import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

// Basing on Nice Label Algorithm for Charts with minimum ticks - https://stackoverflow.com/a/16363437/5796683

data class NiceScale constructor(
    val niceMin: Double,
    val niceMax: Double,
    val tickSpacing: Double,
) {

    fun getHelperLines(): List<Double> {
        var v = niceMin
        val items = mutableListOf<Double>()
        while (v < niceMax) {
            v += tickSpacing
            items.add(v)
        }
        return items
    }
}

fun niceScale(minPoint: Float, maxPoint: Float, maxTicks: Int = 10): NiceScale {

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