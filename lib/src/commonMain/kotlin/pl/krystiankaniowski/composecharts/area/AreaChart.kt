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
import pl.krystiankaniowski.composecharts.axis.XAxis
import pl.krystiankaniowski.composecharts.axis.YAxis
import pl.krystiankaniowski.composecharts.internal.AxisScale
import pl.krystiankaniowski.composecharts.internal.ChartChoreographer
import pl.krystiankaniowski.composecharts.internal.PointMapper
import pl.krystiankaniowski.composecharts.legend.LegendEntry
import pl.krystiankaniowski.composecharts.legend.LegendFlow
import pl.krystiankaniowski.composecharts.legend.LegendPosition

object AreaChart {

    data class Data(
        val areas: List<Area>,
        val xLabelFormatter: (Int) -> String = { it.toString() },
    ) {

        init {
            check(areas.first().values.size.let { size -> areas.all { it.values.size == size } }) {
                "All lines have to contains same amount of entries"
            }
        }

        internal val minValue = areas.minOf { it.values.minOf { it } }
        internal val maxValue = areas.maxOf { it.values.maxOf { it } }

        internal val size: Int get() = areas.first().values.size
    }

    data class Area(
        val label: String,
        val values: List<Float>,
        val color: Color,
    )

    enum class Style {
        OVERLAPPING,
        STACKED,
        PROPORTIONAL,
    }
}

@Suppress("ComplexMethod")
@Composable
fun AreaChart(
    modifier: Modifier = Modifier,
    data: AreaChart.Data,
    title: (@Composable () -> Unit)? = null,
    style: AreaChart.Style = AreaChart.Style.OVERLAPPING,
    xAxis: XAxis.Drawer = XAxis.Default(),
    yAxis: YAxis.Drawer = YAxis.Default(),
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {

    val scale = remember(data) {
        AxisScale.create(
            min = 0f,
            max = when (style) {
                AreaChart.Style.OVERLAPPING -> data.maxValue
                AreaChart.Style.STACKED -> FloatArray(data.areas.first().values.size) { index -> data.areas.map { it.values[index] }.sum() }.max()
                AreaChart.Style.PROPORTIONAL -> 1f
            },
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
            YAxis.Value(
                label =
                when (style) {
                    AreaChart.Style.OVERLAPPING, AreaChart.Style.STACKED -> scale.formatValue(it)
                    AreaChart.Style.PROPORTIONAL -> "${(it * 100).toInt()}%"
                },
                value = it,
            )
        }
    }

    ChartChoreographer(
        modifier = modifier,
        title = title,
        legend = { AreaLegend(data) },
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
                xSrcMin = 0f,
                xSrcMax = data.size.toFloat() - 1,
                xDstMin = contentArea.left,
                xDstMax = contentArea.right,
                ySrcMin = scale.min,
                ySrcMax = scale.max,
                yDstMin = contentArea.top,
                yDstMax = contentArea.bottom,
            )

            xAxis.draw(this, contentArea, xAxisArea, mapper, xAxisValues)
            yAxis.draw(this, contentArea, yAxisArea, mapper, yAxisValues)

            when (style) {

                AreaChart.Style.OVERLAPPING -> {
                    data.areas.forEach { line ->
                        drawArea(line.color, line.values, mapper)
                    }
                }

                AreaChart.Style.STACKED -> {
                    val size = data.areas.first().values.size
                    val buffor = FloatArray(size) { index -> data.areas.map { it.values[index] }.sum() }.toMutableList()
                    for (i in (data.areas.size - 1) downTo 0) {
                        val line = data.areas[i]
                        drawArea(line.color, buffor, mapper)
                        if (i > 0) {
                            for (j in buffor.indices) {
                                buffor[j] -= line.values[j]
                            }
                        }
                    }
                }

                AreaChart.Style.PROPORTIONAL -> {
                    val size = data.areas.first().values.size
                    val total = FloatArray(size) { index -> data.areas.map { it.values[index] }.sum() }.toMutableList()
                    val buffor = total.toMutableList()
                    for (i in (data.areas.size - 1) downTo 0) {
                        val line = data.areas[i]
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
    path.lineTo(
        x = mapper.x(values.size - 1f),
        y = mapper.y(0f),
    )
    path.lineTo(
        x = mapper.x(0f),
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
                x = mapper.x(dataIndex),
                y = mapper.y(point / total[dataIndex]),
            )
        } else {
            path.lineTo(
                x = mapper.x(dataIndex),
                y = mapper.y(point / total[dataIndex]),
            )
        }
    }
    path.lineTo(
        x = mapper.x(values.size - 1f),
        y = mapper.y(0f),
    )
    path.lineTo(
        x = mapper.x(0f),
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
    data: AreaChart.Data,
) {
    Box(modifier = Modifier.border(width = 1.dp, color = ChartsTheme.legendColor)) {
        LegendFlow(
            modifier = Modifier.padding(16.dp),
            data = data.areas.map { item ->
                LegendEntry(
                    item.label,
                    item.color,
                )
            },
        )
    }
}