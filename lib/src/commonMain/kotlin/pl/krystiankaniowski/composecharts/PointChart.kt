package pl.krystiankaniowski.composecharts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import pl.krystiankaniowski.composecharts.internal.ChartChoreographer
import pl.krystiankaniowski.composecharts.internal.PointMapper
import pl.krystiankaniowski.composecharts.internal.calculateYHelperLines

data class PointChartData(val points: List<Points>) {

    constructor(vararg points: Points) : this(points.toList())

    class Point(val x: Float, val y: Float)

    data class Points(
        val label: String,
        val values: List<Point>,
        val color: Color = Color.Unspecified
    )

    init {
        check(points.first().values.size.let { size -> points.all { it.values.size == size } }) {
            "All points set have to contains same amount of entries"
        }
    }

    internal val minX = points.minOf { it.values.minOf { point -> point.x } }
    internal val maxX = points.maxOf { it.values.maxOf { point -> point.x } }
    internal val minY = points.minOf { it.values.minOf { point -> point.y } }
    internal val maxY = points.maxOf { it.values.maxOf { point -> point.y } }

    internal val size: Int get() = points.first().values.size
}

@Composable
fun PointChart(
    data: PointChartData,
    title: @Composable () -> Unit = {},
    colors: Colors = AutoColors,
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {

    ChartChoreographer(
        title = title,
        legend = { PointLegend(data, colors) },
        legendPosition = legendPosition,
    ) {
        Canvas(Modifier.fillMaxSize()) {

            val xAxisHeight = 20f
            val yAxisWidth = 0f
            val contentWidth = size.width - yAxisWidth
            val contentHeight = size.height - xAxisHeight

            val contentArea = Rect(
                top = 0f, bottom = contentHeight,
                left = yAxisWidth, right = size.width
            )
            val xAxisArea = Rect(
                top = contentHeight, bottom = size.height,
                left = yAxisWidth, right = contentWidth
            )
            val yAxisArea = Rect(
                top = 0f, bottom = contentHeight,
                left = 0f, right = yAxisWidth
            )

            val mapper = PointMapper(
                xMin = data.minX, xMax = data.maxX, xTarget = contentWidth,
                yMin = data.minY, yMax = data.maxY, yTarget = contentHeight
            )

            drawIntoCanvas {
                it.drawYAxisHelperLines(mapper, calculateYHelperLines(0f, data.maxY))
            }

            data.points.forEachIndexed { index, series ->
                val color = colors.resolve(index, series.color)
                series.values.forEach { point ->
                    drawCircle(
                        color = color,
                        center = Offset(
                            x = mapper.x(point.x),
                            y = mapper.y(point.y),
                        ),
                        radius = 5f
                    )
                }
            }

            this.XAxis(
                mapper,
                labels = mapOf(0f to "0", 1f to "1", 2f to "2", 3f to "3", 4f to "4", 5f to "5"),
                drawableArea = Rect(
                    left = 0f,
                    top = size.height - xAxisHeight,
                    right = size.width,
                    bottom = size.height,
                )
            )

        }
    }
}

@Composable
private fun PointLegend(
    data: PointChartData,
    colors: Colors = AutoColors
) {
    Box(modifier = Modifier.border(width = 1.dp, color = Color.LightGray)) {
        LegendFlow(
            modifier = Modifier.padding(16.dp),
            data = data.points.mapIndexed { index, item ->
                LegendEntry(
                    item.label,
                    colors.resolve(index, item.color)
                )
            }
        )
    }
}