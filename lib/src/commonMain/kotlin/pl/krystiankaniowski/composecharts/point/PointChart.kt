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
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.unit.dp
import pl.krystiankaniowski.composecharts.AutoColors
import pl.krystiankaniowski.composecharts.ChartsTheme
import pl.krystiankaniowski.composecharts.Colors
import pl.krystiankaniowski.composecharts.internal.ChartChoreographer
import pl.krystiankaniowski.composecharts.internal.PointMapper
import pl.krystiankaniowski.composecharts.legend.LegendEntry
import pl.krystiankaniowski.composecharts.legend.LegendFlow
import pl.krystiankaniowski.composecharts.legend.LegendPosition
import pl.krystiankaniowski.composecharts.resolve

data class PointChartData(
    val series: List<Series>,
    internal val minX: Float = series.minOf { it.values.minOf { point -> point.x } },
    internal val maxX: Float = series.maxOf { it.values.maxOf { point -> point.x } },
    internal val minY: Float = series.minOf { it.values.minOf { point -> point.y } },
    internal val maxY: Float = series.maxOf { it.values.maxOf { point -> point.y } },
) {
    data class Series(
        val label: String,
        val values: List<Offset>,
        val color: Color = Color.Unspecified,
        val strokeWidth: Float = 5f
    )
}

@Composable
fun PointChart(
    modifier: Modifier = Modifier,
    data: PointChartData,
    title: @Composable () -> Unit = {},
    colors: Colors = AutoColors,
    xAxis: PointChartXAxis.Drawer = PointChartXAxis.Auto(),
    yAxis: PointChartYAxis.Drawer = PointChartYAxis.Auto(),
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {

    ChartChoreographer(
        modifier = Modifier,
        title = title,
        legend = { PointLegend(data, colors) },
        legendPosition = legendPosition,
    ) {
        Canvas(Modifier.fillMaxSize()) {

            val contentArea = Rect(
                top = 0f, bottom = size.height - xAxis.requiredHeight(),
                left = yAxis.requiredWidth(), right = size.width
            )
            val xAxisArea = Rect(
                top = contentArea.bottom, bottom = size.height,
                left = contentArea.left, right = contentArea.right
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
            xAxis.draw(this, contentArea, xAxisArea, mapper, data)

            data.series.forEachIndexed { index, series ->
                drawPoints(
                    points = series.values.map { Offset(mapper.x(it.x), mapper.y(it.y)) },
                    pointMode = PointMode.Polygon,
                    strokeWidth = series.strokeWidth,
                    color = colors.resolve(index, series.color),
                )
            }
        }
    }
}

@Composable
private fun PointLegend(
    data: PointChartData,
    colors: Colors = AutoColors
) {
    Box(modifier = Modifier.border(width = 1.dp, color = ChartsTheme.legendColor)) {
        LegendFlow(
            modifier = Modifier.padding(16.dp),
            data = data.series.mapIndexed { index, series ->
                LegendEntry(
                    series.label,
                    colors.resolve(index, series.color)
                )
            }
        )
    }
}