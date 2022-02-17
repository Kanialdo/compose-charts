package pl.krystiankaniowski.composecharts.internal

class XYMapper(
    private val minX: Float,
    private val maxX: Float,
    private val minY: Float,
    private val maxY: Float,
    private val width: Float,
    private val height: Float
) {
    fun x(x: Float) = (x / maxX) * width
    fun x(x: Int) = (x / maxX) * width
    fun y(y: Float) = ((maxY - y) / maxY) * height
    fun y(y: Int) = ((maxY - y) / maxY) * height
}

class YMapper(
    private val minY: Float,
    private val maxY: Float,
    private val height: Float
) {
    fun y(y: Float) = ((maxY - y) / maxY) * height
    fun y(y: Int) = ((maxY - y) / maxY) * height
}

class XMapper(
    private val minX: Float,
    private val maxX: Float,
    private val width: Float,
) {
    fun x(x: Float) = (x / maxX) * width
    fun x(x: Int) = (x / maxX) * width
}