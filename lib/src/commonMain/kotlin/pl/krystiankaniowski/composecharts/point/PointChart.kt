package pl.krystiankaniowski.composecharts.point

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.DefaultTintBlendMode
import androidx.compose.ui.unit.dp
import pl.krystiankaniowski.composecharts.ChartsTheme
import pl.krystiankaniowski.composecharts.internal.AxisScale
import pl.krystiankaniowski.composecharts.internal.ChartChoreographer
import pl.krystiankaniowski.composecharts.internal.OneAxisMapper
import pl.krystiankaniowski.composecharts.internal.PointMapper
import pl.krystiankaniowski.composecharts.legend.LegendEntry
import pl.krystiankaniowski.composecharts.legend.LegendFlow
import pl.krystiankaniowski.composecharts.legend.LegendPosition

@Composable
fun PointChart(
    modifier: Modifier = Modifier,
    data: PointChartData,
    title: (@Composable () -> Unit)? = null,
    xAxis: PointChartXAxis.Drawer = PointChartXAxis.Auto(),
    yAxis: PointChartYAxis.Drawer = PointChartYAxis.Auto(),
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {

    val scaleX = remember(data) {
        AxisScale.create(
            min = data.minX,
            max = data.maxX,
        )
    }

    val scaleY = remember(data) {
        AxisScale.create(
            min = data.minY,
            max = data.maxY,
        )
    }

    ChartChoreographer(
        modifier = modifier,
        title = title,
        legend = { PointLegend(data) },
        legendPosition = legendPosition,
    ) {
        Canvas(Modifier.fillMaxSize()) {

            val contentArea = Rect(
                top = 0f, bottom = size.height - xAxis.requiredHeight(),
                left = yAxis.requiredWidth(), right = size.width,
            )
            val xAxisArea = Rect(
                top = contentArea.bottom, bottom = size.height,
                left = contentArea.left, right = contentArea.right,
            )
            val yAxisArea = Rect(
                top = contentArea.top, bottom = contentArea.bottom,
                left = 0f, right = contentArea.left,
            )

            val mapper = PointMapper(
                xSrcMin = data.minX,
                xSrcMax = data.maxX,
                xDstMin = contentArea.left,
                xDstMax = contentArea.right,
                ySrcMin = data.minY,
                ySrcMax = data.maxY,
                yDstMin = contentArea.top,
                yDstMax = contentArea.bottom,
            )

            xAxis.draw(this, contentArea, xAxisArea, mapper, scaleX)
            yAxis.draw(this, contentArea, yAxisArea, mapper, scaleY)

            data.series.forEach { series ->
                val points = series.values.map { Offset(mapper.x(it.x), mapper.y(it.y)) }
                val mode = when (series.mode) {
                    PointChartData.Mode.Points -> PointMode.Points
                    PointChartData.Mode.Line -> PointMode.Polygon
                }
                when (series.color) {
                    is PointChartData.ChartColor.Solid -> drawPoints(
                        points = points,
                        pointMode = mode,
                        color = series.color.color,
                        strokeWidth = series.strokeWidth,
                        cap = series.cap,
                        pathEffect = series.pathEffect,
                        alpha = series.alpha,
                        colorFilter = series.colorFilter,
                        blendMode = series.blendMode,
                    )

                    is PointChartData.ChartColor.Gradient -> drawPoints(
                        points = points,
                        pointMode = mode,
                        brush = when (series.color) {
                            is PointChartData.ChartColor.YGradient -> {
                                val gradientMapper = OneAxisMapper(srcMin = data.minY, srcMax = data.maxY, dstMin = 0f, dstMax = 1f, inverted = true)
                                val stops = series.color.stops.reversed().map { gradientMapper.map(it.first) to it.second }.toTypedArray()
                                Brush.verticalGradient(
                                    startY = mapper.yDstMin,
                                    endY = mapper.yDstMax,
                                    colorStops = stops,
                                )
                            }
                        },
                        strokeWidth = series.strokeWidth,
                        cap = series.cap,
                        pathEffect = series.pathEffect,
                        alpha = series.alpha,
                        colorFilter = series.colorFilter,
                        blendMode = series.blendMode,
                    )
                }
            }
        }
    }
}

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
        val color: ChartColor,
        val mode: Mode = Mode.Line,
        val strokeWidth: Float = Stroke.HairlineWidth,
        val cap: StrokeCap = StrokeCap.Butt,
        val pathEffect: PathEffect? = null,
        val alpha: Float = 1.0f,
        val colorFilter: ColorFilter? = null,
        val blendMode: BlendMode = DefaultTintBlendMode,
    )

    enum class Mode { Points, Line }

    sealed interface ChartColor {
        data class Solid(val color: Color) : ChartColor
        sealed interface Gradient : ChartColor
        data class YGradient(val stops: List<Pair<Float, Color>>) : Gradient
    }
}

@Composable
private fun PointLegend(data: PointChartData) {
    Box(modifier = Modifier.border(width = 1.dp, color = ChartsTheme.legendColor)) {
        LegendFlow(
            modifier = Modifier.padding(16.dp),
            data = data.series.map { series ->
                LegendEntry(
                    text = series.label,
                    color = when (series.color) {
                        is PointChartData.ChartColor.Solid -> series.color.color
                        is PointChartData.ChartColor.Gradient -> Color.Unspecified
                    },
                )
            },
        )
    }
}