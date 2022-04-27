package pl.krystiankaniowski.composecharts.internal

import kotlin.math.ceil
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.round

internal fun calculateHelperValues(from: Float, to: Float, desiredCount: Int, withBottomOffset: Boolean = true, withTopOffset: Boolean = true): List<Float> {

    val range = (to - from)

    val unroundedTickSize = range / (desiredCount - 1)
    val x = ceil(log10(unroundedTickSize) - 1)
    val pow10x = 10.0f.pow(x)

    val correctedPow10x = when {
        pow10x < 0.1 -> 0.1
        pow10x <= 0.2 -> 0.2
        pow10x <= 0.25 -> 0.25
        pow10x <= 0.3 -> 0.3
        pow10x <= 0.4 -> 0.4
        pow10x <= 0.5 -> 0.5
        pow10x <= 0.6 -> 0.6
        pow10x <= 0.7 -> 0.7
        pow10x <= 0.75 -> 0.75
        pow10x <= 0.8 -> 0.8
        pow10x <= 0.9 -> 0.9
        pow10x <= 1.0 -> 1.0
        else -> 1.0
    }

    val roundedTickRange = ceil(unroundedTickSize / correctedPow10x) * correctedPow10x

    val newLowerBound = roundedTickRange * round(from / roundedTickRange)
    val newUpperBound = roundedTickRange * round(1 + to / roundedTickRange)

    val items = mutableListOf<Float>()
    var v = if (newLowerBound < from) newLowerBound else newLowerBound - roundedTickRange
    if (withBottomOffset) {
        items.add(v.toFloat())
    }
    while (v < newUpperBound && v <= to) {
        v += roundedTickRange
        items.add(v.toFloat())
    }
    return if (withTopOffset) items else items.dropLast(1)
}