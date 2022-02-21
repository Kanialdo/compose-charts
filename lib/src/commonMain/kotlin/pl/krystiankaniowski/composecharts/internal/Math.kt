package pl.krystiankaniowski.composecharts.internal

data class PointMapper(
    private val xMin: Float,
    private val xMax: Float,
    private val xTarget: Float,
    private val yMin: Float,
    private val yMax: Float,
    private val yTarget: Float,
    private val yInverted: Boolean = true
) {
    private val xDiff = xMax - xMin
    private val yDiff = yMax - yMin

    fun x(value: Float) = (value - xMin) * xTarget / xDiff

    fun x(value: Int) = (value - xMin) * xTarget / xDiff

    fun y(value: Float) = (if (yInverted) (yMax - value) else (value - yMin)) * yTarget / yDiff

    fun y(value: Int) = (if (yInverted) (yMax - value) else (value - yMin)) * yTarget / yDiff
}