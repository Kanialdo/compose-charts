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
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import pl.krystiankaniowski.composecharts.ChartsTheme
import pl.krystiankaniowski.composecharts.internal.ChartChoreographer
import pl.krystiankaniowski.composecharts.internal.PointMapper
import pl.krystiankaniowski.composecharts.internal.AxisScale
import pl.krystiankaniowski.composecharts.legend.LegendEntry
import pl.krystiankaniowski.composecharts.legend.LegendFlow
import pl.krystiankaniowski.composecharts.legend.LegendPosition

data class LineChartData(val lines: List<Line>) {

    constructor(vararg lines: Line) : this(lines.toList())

    data class Line(
        val label: String,
        val values: List<Float>,
        val color: Color,
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

enum class LineChartMode {
    STANDARD,
    STACKED,
    PROPORTIONAL,
}

@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    data: LineChartData,
    title: (@Composable () -> Unit)? = null,
    style: LineChartStyle = LineChartStyle(),
    mode: LineChartMode = LineChartMode.STANDARD,
    xAxis: LineChartXAxis.Drawer = LineChartXAxis.Auto(),
    yAxis: LineChartYAxis.Drawer = LineChartYAxis.Auto(),
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {

    val scale = remember(data) {
        AxisScale.create(
            min = 0f,
            max = when (mode) {
                LineChartMode.STANDARD -> data.maxValue
                LineChartMode.STACKED -> FloatArray(data.lines.first().values.size) { index -> data.lines.map { it.values[index] }.sum() }.max()
                LineChartMode.PROPORTIONAL -> 1f
            },
        )
    }

    ChartChoreographer(
        modifier = modifier,
        title = title,
        legend = { LineLegend(data) },
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
                xSrcMin = 0f,
                xSrcMax = data.size.toFloat(),
                xDstMin = contentArea.left,
                xDstMax = contentArea.right,
                ySrcMin = scale.min,
                ySrcMax = scale.max,
                yDstMin = contentArea.top,
                yDstMax = contentArea.bottom,
            )

            xAxis.draw(this, contentArea, xAxisArea, mapper, data)
            yAxis.draw(this, contentArea, yAxisArea, mapper, scale)

            when (mode) {

                LineChartMode.STANDARD -> {
                    data.lines.forEachIndexed { index, line ->
                        drawLine(line, style, mapper)
                        drawPoints(line, style, mapper)
                    }
                }

                LineChartMode.STACKED -> {
                    val size = data.lines.first().values.size
                    val buffor = FloatArray(size) { index -> data.lines.map { it.values[index] }.sum() }.toMutableList()
                    for (i in (data.lines.size - 1) downTo 0) {
                        val line = data.lines[i]
                        drawLineArea(line.color, buffor, mapper)
                        if (i > 0) {
                            for (j in buffor.indices) {
                                buffor[j] -= line.values[j]
                            }
                        }
                    }
                }

                LineChartMode.PROPORTIONAL -> {
                    val size = data.lines.first().values.size
                    val total = FloatArray(size) { index -> data.lines.map { it.values[index] }.sum() }.toMutableList()
                    val buffor = total.toMutableList()
                    for (i in (data.lines.size - 1) downTo 0) {
                        val line = data.lines[i]
                        drawProportionalLineArea(line.color, total, buffor, mapper)
                        if (i > 0) {
                            for (j in buffor.indices) {
                                buffor[j] -= line.values[j]
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun DrawScope.drawLine(
    line: LineChartData.Line,
    style: LineChartStyle,
    mapper: PointMapper,
) {
    val path = Path()
    line.values.forEachIndexed { dataIndex, point ->
        if (dataIndex == 0) {
            path.moveTo(
                x = mapper.x(dataIndex + 0.5f),
                y = mapper.y(point),
            )
        } else {
            path.lineTo(
                x = mapper.x(dataIndex + 0.5f),
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

private fun DrawScope.drawLineArea(
    color: Color,
    values: List<Float>,
    mapper: PointMapper,
) {
    val path = Path()
    values.forEachIndexed { dataIndex, point ->
        if (dataIndex == 0) {
            path.moveTo(
                x = mapper.x(dataIndex + 0.5f),
                y = mapper.y(point),
            )
        } else {
            path.lineTo(
                x = mapper.x(dataIndex + 0.5f),
                y = mapper.y(point),
            )
        }
    }
    path.lineTo(
        x = mapper.x(values.size - 0.5f),
        y = mapper.y(0f),
    )
    path.lineTo(
        x = mapper.x(0.5f),
        y = mapper.y(0f),
    )
    path.close()

    drawPath(
        color = color,
        path = path,
        style = Fill,
    )
}


private fun DrawScope.drawProportionalLineArea(
    color: Color,
    total: List<Float>,
    values: List<Float>,
    mapper: PointMapper,
) {
    val path = Path()
    values.forEachIndexed { dataIndex, point ->
        if (dataIndex == 0) {
            path.moveTo(
                x = mapper.x(dataIndex + 0.5f),
                y = mapper.y(point / total[dataIndex]),
            )
        } else {
            path.lineTo(
                x = mapper.x(dataIndex + 0.5f),
                y = mapper.y(point / total[dataIndex]),
            )
        }
    }
    path.lineTo(
        x = mapper.x(values.size - 0.5f),
        y = mapper.y(0f),
    )
    path.lineTo(
        x = mapper.x(0.5f),
        y = mapper.y(0f),
    )
    path.close()

    drawPath(
        color = color,
        path = path,
        style = Fill,
    )
}

private fun DrawScope.drawPoints(
    line: LineChartData.Line,
    style: LineChartStyle,
    mapper: PointMapper,
) {
    when (val pointStyle = line.pointStyle ?: style.pointStyle) {
        LineChartStyle.PointStyle.None -> {}
        is LineChartStyle.PointStyle.Filled -> {
            line.values.forEachIndexed { dataIndex, point ->
                drawCircle(
                    color = line.color,
                    center = mapper.offset(dataIndex + 0.5f, point),
                    radius = pointStyle.size,
                )
            }
        }
    }
}

@Composable
private fun LineLegend(
    data: LineChartData,
) {
    Box(modifier = Modifier.border(width = 1.dp, color = ChartsTheme.legendColor)) {
        LegendFlow(
            modifier = Modifier.padding(16.dp),
            data = data.lines.map { item ->
                LegendEntry(
                    item.label,
                    item.color,
                )
            }
        )
    }
}