package pl.krystiankaniowski.composecharts.area

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
import androidx.compose.ui.unit.dp
import pl.krystiankaniowski.composecharts.ChartsTheme
import pl.krystiankaniowski.composecharts.internal.AxisScale
import pl.krystiankaniowski.composecharts.internal.ChartChoreographer
import pl.krystiankaniowski.composecharts.internal.PointMapper
import pl.krystiankaniowski.composecharts.legend.LegendEntry
import pl.krystiankaniowski.composecharts.legend.LegendFlow
import pl.krystiankaniowski.composecharts.legend.LegendPosition

data class AreaChartData(val lines: List<Area>) {

    constructor(vararg lines: Area) : this(lines.toList())

    data class Area(
        val label: String,
        val values: List<Float>,
        val color: Color,
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

enum class AreaChartMode {
    OVERLAPPING,
    STACKED,
    PROPORTIONAL,
}

@Composable
fun AreaChart(
    modifier: Modifier = Modifier,
    data: AreaChartData,
    title: (@Composable () -> Unit)? = null,
    mode: AreaChartMode = AreaChartMode.OVERLAPPING,
    xAxis: AreaChartXAxis.Drawer = AreaChartXAxis.Auto(),
    yAxis: AreaChartYAxis.Drawer = AreaChartYAxis.Auto(),
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {

    val scale = remember(data) {
        AxisScale.create(
            min = 0f,
            max = when (mode) {
                AreaChartMode.OVERLAPPING -> data.maxValue
                AreaChartMode.STACKED -> FloatArray(data.lines.first().values.size) { index -> data.lines.map { it.values[index] }.sum() }.max()
                AreaChartMode.PROPORTIONAL -> 1f
            },
        )
    }

    ChartChoreographer(
        modifier = modifier,
        title = title,
        legend = { AreaLegend(data) },
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

                AreaChartMode.OVERLAPPING -> {
                    data.lines.forEachIndexed { index, line ->
                        drawArea(line.color, line.values, mapper)
                    }
                }

                AreaChartMode.STACKED -> {
                    val size = data.lines.first().values.size
                    val buffor = FloatArray(size) { index -> data.lines.map { it.values[index] }.sum() }.toMutableList()
                    for (i in (data.lines.size - 1) downTo 0) {
                        val line = data.lines[i]
                        drawArea(line.color, buffor, mapper)
                        if (i > 0) {
                            for (j in buffor.indices) {
                                buffor[j] -= line.values[j]
                            }
                        }
                    }
                }

                AreaChartMode.PROPORTIONAL -> {
                    val size = data.lines.first().values.size
                    val total = FloatArray(size) { index -> data.lines.map { it.values[index] }.sum() }.toMutableList()
                    val buffor = total.toMutableList()
                    for (i in (data.lines.size - 1) downTo 0) {
                        val line = data.lines[i]
                        drawProportionalArea(line.color, total, buffor, mapper)
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

private fun DrawScope.drawArea(
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

private fun DrawScope.drawProportionalArea(
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

@Composable
private fun AreaLegend(
    data: AreaChartData,
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