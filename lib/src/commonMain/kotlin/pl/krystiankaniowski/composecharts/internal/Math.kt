package pl.krystiankaniowski.composecharts.internal

import androidx.compose.ui.geometry.Offset

data class PointMapper(
    val xSrcMin: Float, val xSrcMax: Float, val xDstMin: Float, val xDstMax: Float,
    val ySrcMin: Float, val ySrcMax: Float, val yDstMin: Float, val yDstMax: Float,
    val yInverted: Boolean = true,
) : XMapper, YMapper {

    val xScale = (xDstMax - xDstMin) / (xSrcMax - xSrcMin)
    val yScale = (yDstMax - yDstMin) / (ySrcMax - ySrcMin)

    override fun x(value: Float) = (value - xSrcMin) * xScale + xDstMin

    override fun x(value: Int) = x(value.toFloat())

    override fun y(value: Float) =
        (if (yInverted) (ySrcMax - value) else (value - ySrcMin)) * yScale + yDstMin

    override fun y(value: Int) = y(value.toFloat())

    fun offset(x: Float, y: Float) = Offset(x(x), y(y))
}

data class OneAxisMapper(
    val srcMin: Float, val srcMax: Float,
    val dstMin: Float, val dstMax: Float,
    val inverted: Boolean = true,
) {
    private val scale = (dstMax - dstMin) / (srcMax - srcMin)
    fun map(value: Float) = (if (inverted) (srcMax - value) else (value - srcMin)) * scale + dstMin
    fun map(value: Int) = map(value.toFloat())
}

interface XMapper {
    fun x(value: Float): Float
    fun x(value: Int): Float
}

interface YMapper {
    fun y(value: Float): Float
    fun y(value: Int): Float
}