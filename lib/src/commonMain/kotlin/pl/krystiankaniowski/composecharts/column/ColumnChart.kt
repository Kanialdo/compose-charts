package pl.krystiankaniowski.composecharts.column

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
import pl.krystiankaniowski.composecharts.internal.ChartChoreographer
import pl.krystiankaniowski.composecharts.internal.PointMapper
import pl.krystiankaniowski.composecharts.internal.Scale
import pl.krystiankaniowski.composecharts.legend.LegendEntry
import pl.krystiankaniowski.composecharts.legend.LegendFlow
import pl.krystiankaniowski.composecharts.legend.LegendPosition

data class ColumnChartData(
    val labels: List<String>,
    val columns: List<Column>,
) {

    data class Column(
        val label: String,
        val values: List<Float>,
        val color: Color,
    )

    init {
        check(columns.first().values.size.let { size -> columns.all { it.values.size == size } }) {
            "All bars have to contains same amount of entries"
        }
        check(labels.size == columns.first().values.size) {
            "Amount of labels should be equal to amount of values in each bar"
        }
    }

    internal val minValue = columns.minOf { it.values.minOf { it } }
    internal val maxValue = columns.maxOf { it.values.maxOf { it } }

    internal val size: Int get() = columns.first().values.size
}

enum class ColumnChartStyle {
    GROUPED,
    STACKED,
    PROPORTIONAL,
}

@Composable
fun ColumnChart(
    modifier: Modifier = Modifier,
    data: ColumnChartData,
    style: ColumnChartStyle = ColumnChartStyle.GROUPED,
    title: (@Composable () -> Unit)? = null,
    xAxis: ColumnChartXAxis.Drawer = ColumnChartXAxis.Auto(),
    yAxis: ColumnChartYAxis.Drawer = ColumnChartYAxis.Auto(),
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {

    val scale = remember(data) {
        Scale.create(
            min = 0f,
            max = when (style) {
                ColumnChartStyle.GROUPED -> data.maxValue
                ColumnChartStyle.STACKED -> FloatArray(data.size) { index -> data.columns.map { it.values[index] }.sum() }.max()
                ColumnChartStyle.PROPORTIONAL -> 1f
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
            val xAxisArea = Rect(
                top = contentArea.bottom, bottom = size.height,
                left = contentArea.left, right = contentArea.right,
            )
            val yAxisArea = Rect(
                top = contentArea.top, bottom = contentArea.bottom,
                left = 0f, right = contentArea.left,
            )

            val mapper = PointMapper(
                xSrcMin = 0f - offset,
                xSrcMax = data.size - 1 + offset,
                xDstMin = contentArea.left,
                xDstMax = contentArea.right,
                ySrcMin = scale.min,
                ySrcMax = scale.max,
                yDstMin = contentArea.top,
                yDstMax = contentArea.bottom,
            )

            xAxis.draw(this, contentArea, xAxisArea, mapper, data)
            yAxis.draw(this, contentArea, yAxisArea, mapper, scale)

            when (style) {

                ColumnChartStyle.GROUPED -> {

                    val barWidth = 2 * w * mapper.xScale / data.columns.size
                    data.columns.forEachIndexed { series, value ->
                        value.values.forEachIndexed { pos, v ->
                            drawRect(
                                color = value.color,
                                topLeft = Offset(
                                    x = mapper.x(pos - w) + series * barWidth,
                                    y = mapper.y(v),
                                ),
                                size = Size(
                                    width = barWidth,
                                    height = v * mapper.yScale,
                                )
                            )
                        }
                    }
                }

                ColumnChartStyle.STACKED -> {

                    val series = data.columns.size
                    val values = data.size
                    val maxValues = FloatArray(values) { index -> data.columns.map { it.values[index] }.sum() }

                    val barWidth = 2 * w * mapper.xScale

                    for (i in (values - 1) downTo 0) {
                        var counter = maxValues[i]
                        for (j in (series - 1) downTo 0) {
                            drawRect(
                                color = data.columns[j].color,
                                topLeft = Offset(
                                    x = mapper.x(i - w),
                                    y = mapper.y(counter),
                                ),
                                size = Size(
                                    width = barWidth,
                                    height = counter * mapper.yScale,
                                )
                            )
                            counter -= data.columns[j].values[i]
                        }
                    }
                }

                ColumnChartStyle.PROPORTIONAL -> {

                    val series = data.columns.size
                    val values = data.size
                    val maxValues = FloatArray(values) { index -> data.columns.map { it.values[index] }.sum() }

                    val barWidth = 2 * w * mapper.xScale

                    for (i in (values - 1) downTo 0) {
                        var counter = maxValues[i]
                        for (j in (series - 1) downTo 0) {
                            drawRect(
                                color = data.columns[j].color,
                                topLeft = Offset(
                                    x = mapper.x(i - w),
                                    y = mapper.y(counter / maxValues[i]),
                                ),
                                size = Size(
                                    width = barWidth,
                                    height = (counter / maxValues[i]) * mapper.yScale,
                                )
                            )
                            counter -= data.columns[j].values[i]
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BarLegend(
    data: ColumnChartData,
) {
    Box(modifier = Modifier.border(width = 1.dp, color = ChartsTheme.legendColor)) {
        LegendFlow(
            modifier = Modifier.padding(16.dp),
            data = data.columns.map { item ->
                LegendEntry(
                    item.label,
                    item.color,
                )
            }
        )
    }
}
