package pl.krystiankaniowski.composecharts.line

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import pl.krystiankaniowski.composecharts.AutoColors
import pl.krystiankaniowski.composecharts.Colors
import pl.krystiankaniowski.composecharts.internal.ChartChoreographer
import pl.krystiankaniowski.composecharts.internal.PointMapper
import pl.krystiankaniowski.composecharts.legend.LegendEntry
import pl.krystiankaniowski.composecharts.legend.LegendFlow
import pl.krystiankaniowski.composecharts.legend.LegendPosition

data class LineChartData(val lines: List<Line>) {

    constructor(vararg lines: Line) : this(lines.toList())

    data class Line(
        val label: String,
        val values: List<Float>,
        val color: Color? = null,
        val lineStyle: LineChartStyle.LineStyle? = null,
        val pointStyle: LineChartStyle.PointStyle? = null,
    )

    init {
        check(lines.first().values.size.let { size -> lines.all { it.values.size == size } }) {
            "All lines have to contains same amount of entries"
        }
    }

    internal val minValue = lines.minOf { it.values.minOf { it } }
    internal val maxValue = lines.maxOf { it.values.maxOf { it } }

    internal val size: Int get() = lines.first().values.size
}

data class LineChartStyle(
    val colors: Colors = AutoColors,
    val lineStyle: LineStyle = LineStyle(),
    val pointStyle: PointStyle = PointStyle.Filled(),
) {

    data class LineStyle(
        val width: Float = 5f
    )

    sealed class PointStyle {
        object None : PointStyle()
        data class Filled(val size: Float = 5f) : PointStyle()
    }
}

@Composable
fun LineChart(
    data: LineChartData,
    title: @Composable () -> Unit = {},
    style: LineChartStyle = LineChartStyle(),
    xAxis: LineChartXAxis = LineChartXAxis.Linear(),
    yAxis: LineChartYAxis = LineChartYAxis.Linear(),
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {

    ChartChoreographer(
        title = title,
        legend = { LineLegend(data, style.colors) },
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
                xSrcMin = 0f,
                xSrcMax = data.size.toFloat(),
                xDstMin = contentArea.left,
                xDstMax = contentArea.right,
                ySrcMin = 0f,
                ySrcMax = data.maxValue,
                yDstMin = contentArea.top,
                yDstMax = contentArea.bottom
            )

            xAxis.draw(this, contentArea, xAxisArea, mapper, data)
            yAxis.draw(this, contentArea, yAxisArea, mapper, data)
            data.lines.forEachIndexed { index, line ->
                drawLine(index, line, style, mapper)
                drawPoints(index, line, style, mapper)
            }
        }
    }
}

private fun DrawScope.drawLine(
    index: Int,
    line: LineChartData.Line,
    style: LineChartStyle,
    mapper: PointMapper
) {
    val color = line.color ?: style.colors.getColor(index)
    val path = Path()
    line.values.forEachIndexed { dataIndex, point ->
        if (dataIndex == 0) {
            path.moveTo(
                x = mapper.x(dataIndex + 0.5f),
                y = mapper.y(point)
            )
        } else {
            path.lineTo(
                x = mapper.x(dataIndex + 0.5f),
                y = mapper.y(point)
            )
        }
    }
    drawPath(
        color = color,
        path = path,
        style = Stroke(width = line.lineStyle?.width ?: style.lineStyle.width)
    )
}

private fun DrawScope.drawPoints(
    index: Int,
    line: LineChartData.Line,
    style: LineChartStyle,
    mapper: PointMapper
) {
    when (val pointStyle = line.pointStyle ?: style.pointStyle) {
        LineChartStyle.PointStyle.None -> {}
        is LineChartStyle.PointStyle.Filled -> {
            line.values.forEachIndexed { dataIndex, point ->
                drawCircle(
                    color = line.color ?: style.colors.getColor(index),
                    center = mapper.offset(dataIndex + 0.5f, point),
                    radius = pointStyle.size
                )
            }
        }
    }
}

@Composable
private fun LineLegend(
    data: LineChartData,
    colors: Colors = AutoColors
) {
    Box(modifier = Modifier.border(width = 1.dp, color = Color.LightGray)) {
        LegendFlow(
            modifier = Modifier.padding(16.dp),
            data = data.lines.mapIndexed { index, item ->
                LegendEntry(
                    item.label,
                    item.color ?: colors.getColor(index)
                )
            }
        )
    }
}