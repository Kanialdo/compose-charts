package pl.krystiankaniowski.composecharts.line

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.rememberTextMeasurer
import pl.krystiankaniowski.composecharts.axis.Axis
import pl.krystiankaniowski.composecharts.axis.AxisLabel
import pl.krystiankaniowski.composecharts.axis.XAxisDrawer
import pl.krystiankaniowski.composecharts.axis.YAxisDrawer
import pl.krystiankaniowski.composecharts.data.ChartColor
import pl.krystiankaniowski.composecharts.data.Series
import pl.krystiankaniowski.composecharts.internal.ChartChoreographer
import pl.krystiankaniowski.composecharts.internal.ChartMeasurer
import pl.krystiankaniowski.composecharts.internal.PointMapper
import pl.krystiankaniowski.composecharts.legend.Legend
import pl.krystiankaniowski.composecharts.legend.LegendPosition

object LineChart2 {

    data class Data(
        val lines: List<Line>,
    ) {

        init {
            check(lines.first().values.size.let { size -> lines.all { it.values.size == size } }) {
                "All lines have to contains same amount of entries"
            }
        }

        internal val minValue = lines.minOf { it.values.minOf { it } }
        internal val maxValue = lines.maxOf { it.values.maxOf { it } }

        internal val size: Int get() = lines.first().values.size
    }

    data class Line(
        override val label: String,
        val values: List<Float>,
        override val color: ChartColor.Solid,
        val lineStyle: Style.LineStyle? = null,
        val pointStyle: Style.PointStyle? = null,
    ) : Series

    data class Style(
        val lineStyle: LineStyle = LineStyle(),
        val pointStyle: PointStyle = PointStyle.Filled(),
    ) {

        data class LineStyle(
            val width: Float = 5f,
        )

        sealed class PointStyle {
            data object None : PointStyle()
            data class Filled(val size: Float = 5f) : PointStyle()
        }
    }
}

@Composable
fun LineChart2(
    modifier: Modifier = Modifier,
    data: LineChart2.Data,
    title: (@Composable () -> Unit)? = null,
    style: LineChart2.Style = LineChart2.Style(),
    xAxis: Axis = Axis(),
    yAxis: Axis = Axis(),
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {

    val xAxisDrawer = remember(xAxis, data) { XAxisDrawer(xAxis, 0f, data.size - 1f) }
    val xAxisValues = remember(data) {
        (0 until data.size).map { i -> AxisLabel(label = xAxis.mapper(i.toFloat()), value = i.toFloat()) }
    }
    val yAxisDrawer = remember(yAxis, data) { YAxisDrawer(yAxis, data.minValue, data.maxValue) }
    val yAxisValues = remember(yAxisDrawer) { yAxisDrawer.calculateValues() }

    ChartChoreographer(
        modifier = modifier,
        title = title,
        legend = { Legend(data = data.lines) },
        legendPosition = legendPosition,
    ) {

        val textMeasurer = rememberTextMeasurer()
        val xOffset = 0.5f

        Canvas(Modifier.fillMaxSize()) {

            val measurer = ChartMeasurer(
                drawScope = this,
                textMeasurer = textMeasurer,
                xAxisDrawer = xAxisDrawer,
                yAxisDrawer = yAxisDrawer,
                minX = -xOffset,
                maxX = data.size - 1 + xOffset,
                minY = 0f,
                maxY = yAxisValues.last().value,
            )

            xAxisDrawer.draw(this, measurer, xAxisValues)
            yAxisDrawer.draw(this, measurer, yAxisValues)
            data.lines.forEach { line ->
                drawLine(line, style, measurer.mapper)
                drawPoints(line, style, measurer.mapper)
            }
        }
    }
}

private fun DrawScope.drawLine(
    line: LineChart2.Line,
    style: LineChart2.Style,
    mapper: PointMapper,
) {
    val path = Path()
    line.values.forEachIndexed { dataIndex, point ->
        if (dataIndex == 0) {
            path.moveTo(
                x = mapper.x(dataIndex),
                y = mapper.y(point),
            )
        } else {
            path.lineTo(
                x = mapper.x(dataIndex),
                y = mapper.y(point),
            )
        }
    }
    drawPath(
        color = line.color.value,
        path = path,
        style = Stroke(width = line.lineStyle?.width ?: style.lineStyle.width),
    )
}

private fun DrawScope.drawPoints(
    line: LineChart2.Line,
    style: LineChart2.Style,
    mapper: PointMapper,
) {
    when (val pointStyle = line.pointStyle ?: style.pointStyle) {
        LineChart2.Style.PointStyle.None -> {}
        is LineChart2.Style.PointStyle.Filled -> {
            line.values.forEachIndexed { dataIndex, point ->
                drawCircle(
                    color = line.color.value,
                    center = mapper.offset(dataIndex.toFloat(), point),
                    radius = pointStyle.size,
                )
            }
        }
    }
}
