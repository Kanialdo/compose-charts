package pl.krystiankaniowski.composecharts.bar

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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pl.krystiankaniowski.composecharts.ChartsTheme
import pl.krystiankaniowski.composecharts.column.ColumnChartStyle
import pl.krystiankaniowski.composecharts.internal.ChartChoreographer
import pl.krystiankaniowski.composecharts.internal.PointMapper
import pl.krystiankaniowski.composecharts.internal.Scale
import pl.krystiankaniowski.composecharts.legend.LegendEntry
import pl.krystiankaniowski.composecharts.legend.LegendFlow
import pl.krystiankaniowski.composecharts.legend.LegendPosition

data class BarChartData(
    val labels: List<String>,
    val dataSets: List<DataSet>,
) {

    data class DataSet(
        val label: String,
        val color: Color,
        val values: List<Float>,
    )

    init {
        check(dataSets.first().values.size.let { size -> dataSets.all { it.values.size == size } }) {
            "All bars have to contains same amount of entries"
        }
    }

    internal val minValue = dataSets.minOf { it.values.minOf { it } }
    internal val maxValue = dataSets.maxOf { it.values.maxOf { it } }

    internal val size: Int get() = dataSets.first().values.size
}

enum class BarChartStyle {
    GROUPED,
    STACKED,
    PROPORTIONAL,
}

@Composable
fun BarChart(
    modifier: Modifier = Modifier,
    data: BarChartData,
    style: BarChartStyle = BarChartStyle.GROUPED,
    title: (@Composable () -> Unit)? = null,
    yAxis: BarChartYAxis.Drawer = BarChartYAxis.Auto(),
    xAxis: BarChartXAxis.Drawer = BarChartXAxis.Auto(),
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {

    val scale = remember(data) {
        Scale.create(
            min = 0f,
            max = when (style) {
                BarChartStyle.GROUPED -> data.maxValue
                BarChartStyle.STACKED -> FloatArray(data.size) { index -> data.dataSets.map { it.values[index] }.sum() }.max()
                BarChartStyle.PROPORTIONAL -> 1f
            },
        )
    }

    ChartChoreographer(
        modifier = modifier,
        title = title,
        legend = { BarLegend(data) },
        legendPosition = legendPosition,
    ) {
        Canvas(Modifier.fillMaxSize()) {

            val w = 0.25f
            val offset = 1f

            val contentArea = Rect(
                top = 0f, bottom = size.height - xAxis.requiredHeight(),
                left = yAxis.requiredWidth(), right = size.width,
            )
            val yAxisArea = Rect(
                top = contentArea.top, bottom = contentArea.bottom,
                left = 0f, right = contentArea.left,
            )
            val xAxisArea = Rect(
                top = contentArea.bottom, bottom = size.height,
                left = contentArea.left, right = contentArea.right,
            )

            val mapper = PointMapper(
                xSrcMin = scale.min,
                xSrcMax = scale.max,
                xDstMin = contentArea.left,
                xDstMax = contentArea.right,
                ySrcMin = 0f - offset,
                ySrcMax = data.size - 1 + offset,
                yDstMin = contentArea.top,
                yDstMax = contentArea.bottom,
            )

            yAxis.draw(this, contentArea, yAxisArea, mapper, data)
            xAxis.draw(this, contentArea, xAxisArea, mapper, scale)

            when (style) {

                BarChartStyle.GROUPED -> {
                    val barHeight = 2 * w * mapper.yScale / data.dataSets.size
                    data.dataSets.forEachIndexed { series, value ->
                        value.values.forEachIndexed { pos, v ->
                            drawRect(
                                color = value.color,
                                topLeft = Offset(
                                    x = mapper.x(0),
                                    y = mapper.y(pos + w) + series * barHeight,
                                ),
                                size = Size(
                                    width = v * mapper.xScale,
                                    height = barHeight,
                                )
                            )
                        }
                    }
                }

                BarChartStyle.STACKED -> {

                    val series = data.dataSets.size
                    val values = data.size
                    val maxValues = FloatArray(values) { index -> data.dataSets.map { it.values[index] }.sum() }

                    val barHeight = 2 * w * mapper.yScale

                    for (i in (values - 1) downTo 0) {
                        var counter = maxValues[i]
                        for (j in (series - 1) downTo 0) {
                            drawRect(
                                color = data.dataSets[j].color,
                                topLeft = Offset(
                                    x = mapper.x(0),
                                    y = mapper.y(i + w),
                                ),
                                size = Size(
                                    width = counter * mapper.xScale,
                                    height = barHeight,
                                )
                            )
                            counter -= data.dataSets[j].values[i]
                        }
                    }
                }

                BarChartStyle.PROPORTIONAL -> {

                    val series = data.dataSets.size
                    val values = data.size
                    val maxValues = FloatArray(values) { index -> data.dataSets.map { it.values[index] }.sum() }

                    val barHeight = 2 * w * mapper.yScale

                    for (i in (values - 1) downTo 0) {
                        var counter = maxValues[i]
                        for (j in (series - 1) downTo 0) {
                            drawRect(
                                color = data.dataSets[j].color,
                                topLeft = Offset(
                                    x = mapper.x(0),
                                    y = mapper.y(i + w),
                                ),
                                size = Size(
                                    width = (counter / maxValues[i]) * mapper.xScale,
                                    height = barHeight,
                                )
                            )
                            counter -= data.dataSets[j].values[i]
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BarLegend(
    data: BarChartData,
) {
    Box(modifier = Modifier.border(width = 1.dp, color = ChartsTheme.legendColor)) {
        LegendFlow(
            modifier = Modifier.padding(16.dp),
            data = data.dataSets.map { item ->
                LegendEntry(
                    item.label,
                    item.color,
                )
            }
        )
    }
}
