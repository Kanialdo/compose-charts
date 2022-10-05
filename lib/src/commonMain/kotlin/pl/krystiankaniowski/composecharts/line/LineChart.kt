package pl.krystiankaniowski.composecharts.line

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import pl.krystiankaniowski.composecharts.ChartsTheme
import pl.krystiankaniowski.composecharts.axis.XAxis
import pl.krystiankaniowski.composecharts.axis.YAxis
import pl.krystiankaniowski.composecharts.internal.AxisScale
import pl.krystiankaniowski.composecharts.internal.ChartChoreographer
import pl.krystiankaniowski.composecharts.internal.PointMapper
import pl.krystiankaniowski.composecharts.legend.LegendEntry
import pl.krystiankaniowski.composecharts.legend.LegendFlow
import pl.krystiankaniowski.composecharts.legend.LegendPosition

object LineChart {

    data class Data(
        val lines: List<Line>,
        val xLabelFormatter: (Int) -> String = { it.toString() },
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
        val label: String,
        val values: List<Float>,
        val color: Color,
        val lineStyle: Style.LineStyle? = null,
        val pointStyle: Style.PointStyle? = null,
    )

    data class Style(
        val lineStyle: LineStyle = LineStyle(),
        val pointStyle: PointStyle = PointStyle.Filled(),
    ) {

        data class LineStyle(
            val width: Float = 5f,
        )

        sealed class PointStyle {
            object None : PointStyle()
            data class Filled(val size: Float = 5f) : PointStyle()
        }
    }
}

@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    data: LineChart.Data,
    title: (@Composable () -> Unit)? = null,
    style: LineChart.Style = LineChart.Style(),
    xAxis: XAxis.Drawer = XAxis.Default(),
    yAxis: YAxis.Drawer = YAxis.Default(),
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {

    val scale = remember(data) {
        AxisScale.create(
            min = data.minValue,
            max = data.maxValue,
        )
    }

    val xAxisValues = remember(data) {
        buildList(data.size) {
            for (i in 0 until data.size) {
                add(XAxis.Value(label = data.xLabelFormatter(i), value = i.toFloat()))
            }
        }
    }

    val yAxisValues = remember(scale, style) {
        scale.getHelperLines().map {
            YAxis.Value(label = scale.formatValue(it), value = it)
        }
    }

    ChartChoreographer(
        modifier = modifier,
        title = title,
        legend = { LineLegend(data) },
        legendPosition = legendPosition,
    ) {
        Canvas(Modifier.fillMaxSize()) {

            val contentArea = Rect(
                top = 0f, bottom = size.height - xAxis.requiredHeight(this, xAxisValues),
                left = yAxis.requiredWidth(this, yAxisValues), right = size.width,
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
                xSrcMin = -0.5f,
                xSrcMax = data.size - 0.5f,
                xDstMin = contentArea.left,
                xDstMax = contentArea.right,
                ySrcMin = scale.min,
                ySrcMax = scale.max,
                yDstMin = contentArea.top,
                yDstMax = contentArea.bottom,
            )

            xAxis.draw(this, contentArea, xAxisArea, mapper, xAxisValues)
            yAxis.draw(this, contentArea, yAxisArea, mapper, yAxisValues)

            data.lines.forEach { line ->
                drawLine(line, style, mapper)
                drawPoints(line, style, mapper)
            }
        }
    }
}

private fun DrawScope.drawLine(
    line: LineChart.Line,
    style: LineChart.Style,
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
        color = line.color,
        path = path,
        style = Stroke(width = line.lineStyle?.width ?: style.lineStyle.width),
    )
}

private fun DrawScope.drawPoints(
    line: LineChart.Line,
    style: LineChart.Style,
    mapper: PointMapper,
) {
    when (val pointStyle = line.pointStyle ?: style.pointStyle) {
        LineChart.Style.PointStyle.None -> {}
        is LineChart.Style.PointStyle.Filled -> {
            line.values.forEachIndexed { dataIndex, point ->
                drawCircle(
                    color = line.color,
                    center = mapper.offset(dataIndex.toFloat(), point),
                    radius = pointStyle.size,
                )
            }
        }
    }
}

@Composable
private fun LineLegend(
    data: LineChart.Data,
) {
    Box(modifier = Modifier.border(width = 1.dp, color = ChartsTheme.legendColor)) {
        LegendFlow(
            modifier = Modifier.padding(16.dp),
            data = data.lines.map { item ->
                LegendEntry(
                    item.label,
                    item.color,
                )
            },
        )
    }
}