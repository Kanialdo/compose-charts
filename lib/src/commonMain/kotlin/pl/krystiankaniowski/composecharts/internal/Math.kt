package pl.krystiankaniowski.composecharts.internal

import androidx.compose.ui.geometry.Offset

data class PointMapper(
    val xMin: Float,
    val xMax: Float,
    val xTarget: Float,
    val yMin: Float,
    val yMax: Float,
    val yTarget: Float,
    val yInverted: Boolean = true
) {
    private val xDiff = xMax - xMin
    private val yDiff = yMax - yMin

    fun x(value: Float) = (value - xMin) * xTarget / xDiff

    fun x(value: Int) = (value - xMin) * xTarget / xDiff

    fun y(value: Float) = (if (yInverted) (yMax - value) else (value - yMin)) * yTarget / yDiff

    fun y(value: Int) = (if (yInverted) (yMax - value) else (value - yMin)) * yTarget / yDiff

    fun offset(x: Float, y: Float) = Offset(x(x), y(y))
}