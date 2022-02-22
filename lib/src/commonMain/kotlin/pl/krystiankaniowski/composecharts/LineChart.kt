package pl.krystiankaniowski.composecharts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import pl.krystiankaniowski.composecharts.internal.ChartChoreographer
import pl.krystiankaniowski.composecharts.internal.PointMapper
import pl.krystiankaniowski.composecharts.internal.calculateYHelperLines

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
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {

    ChartChoreographer(
        title = title,
        legend = { LineLegend(data, style.colors) },
        legendPosition = legendPosition,
    ) {
        Canvas(Modifier.fillMaxSize()) {

            val mapper = PointMapper(
                xMin = 0f, xMax = data.size.toFloat(), xTarget = size.width,
                yMin = 0f, yMax = data.maxValue, yTarget = size.height
            )

            drawIntoCanvas {
                it.drawYAxisHelperLines(mapper, calculateYHelperLines(0f, data.maxValue))
            }

            data.lines.forEachIndexed { index, series ->
                val color = series.color ?: style.colors.getColor(index)
                val path = Path()
                series.values.forEachIndexed { dataIndex, point ->
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
                    style = Stroke(width = series.lineStyle?.width ?: style.lineStyle.width)
                )
                when (val pointStyle = series.pointStyle ?: style.pointStyle) {
                    LineChartStyle.PointStyle.None -> {}
                    is LineChartStyle.PointStyle.Filled -> {
                        series.values.forEachIndexed { dataIndex, point ->
                            drawCircle(
                                color = color,
                                center = Offset(
                                    x = mapper.x(dataIndex + 0.5f),
                                    y = mapper.y(point),
                                ),
                                radius = pointStyle.size
                            )
                        }
                    }
                }
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