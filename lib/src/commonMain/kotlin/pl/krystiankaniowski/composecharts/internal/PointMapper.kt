package pl.krystiankaniowski.composecharts.internal

class YMapper(
    private val minY: Float,
    private val maxY: Float,
    private val height: Float
) {
    fun y(y: Float) = ((maxY - y) / maxY) * height
}

class XMapper(
    private val minX: Float,
    private val maxX: Float,
    private val width: Float,
) {
    fun x(x: Float) = (x / maxX) * width
}