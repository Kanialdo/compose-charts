package pl.krystiankaniowski.composecharts.point

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
import androidx.compose.ui.unit.dp
import pl.krystiankaniowski.composecharts.AutoColors
import pl.krystiankaniowski.composecharts.Colors
import pl.krystiankaniowski.composecharts.internal.ChartChoreographer
import pl.krystiankaniowski.composecharts.internal.PointMapper
import pl.krystiankaniowski.composecharts.legend.LegendEntry
import pl.krystiankaniowski.composecharts.legend.LegendFlow
import pl.krystiankaniowski.composecharts.legend.LegendPosition
import pl.krystiankaniowski.composecharts.resolve

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
    yAxis: PointChartYAxis = PointChartYAxis.Linear(),
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {

    ChartChoreographer(
        title = title,
        legend = { PointLegend(data, colors) },
        legendPosition = legendPosition,
    ) {
        Canvas(Modifier.fillMaxSize()) {

            val contentArea = Rect(
                top = 0f, bottom = size.height - 0,
                left = yAxis.requiredWidth(), right = size.width
            )
            val yAxisArea = Rect(
                top = contentArea.top, bottom = contentArea.bottom,
                left = 0f, right = contentArea.left
            )

            val mapper = PointMapper(
                xSrcMin = data.minX,
                xSrcMax = data.maxX,
                xDstMin = contentArea.left,
                xDstMax = contentArea.right,
                ySrcMin = data.minY,
                ySrcMax = data.maxY,
                yDstMin = contentArea.top,
                yDstMax = contentArea.bottom
            )

            yAxis.draw(this, contentArea, yAxisArea, mapper, data)

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