package pl.krystiankaniowski.composecharts.bar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pl.krystiankaniowski.composecharts.AutoColors
import pl.krystiankaniowski.composecharts.ChartsTheme
import pl.krystiankaniowski.composecharts.Colors
import pl.krystiankaniowski.composecharts.internal.ChartChoreographer
import pl.krystiankaniowski.composecharts.internal.PointMapper
import pl.krystiankaniowski.composecharts.legend.LegendEntry
import pl.krystiankaniowski.composecharts.legend.LegendFlow
import pl.krystiankaniowski.composecharts.legend.LegendPosition
import pl.krystiankaniowski.composecharts.resolve

data class BarChartData(val bars: List<Bar>) {

    constructor(vararg bars: Bar) : this(bars.toList())

    data class Bar(
        val label: String,
        val values: List<Float>,
        val color: Color = Color.Unspecified
    ) {
        internal val sum: Float get() = values.sum()
    }

    init {
        check(bars.first().values.size.let { size -> bars.all { it.values.size == size } }) {
            "All bars have to contains same amount of entries"
        }
    }

    internal val minValue = bars.minOf { it.values.minOf { it } }
    internal val maxValue = bars.maxOf { it.values.maxOf { it } }

    internal val size: Int get() = bars.first().values.size
}

enum class BarChartStyle {
    STANDARD,
    STACKED,
    PROPORTION
}

@Composable
fun BarChart(
    data: BarChartData,
    style: BarChartStyle = BarChartStyle.STANDARD,
    title: @Composable () -> Unit = {},
    colors: Colors = AutoColors,
    yAxis: BarChartYAxis.Drawer = BarChartYAxis.Auto(),
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {
    ChartChoreographer(
        title = title,
        legend = { BarLegend(data, colors) },
        legendPosition = legendPosition,
    ) {
        Canvas(Modifier.fillMaxSize()) {

            val w = 0.25f
            val offset = 1f

            val contentArea = Rect(
                top = 0f, bottom = size.height - 0,
                left = yAxis.requiredWidth(), right = size.width
            )
            val yAxisArea = Rect(
                top = contentArea.top, bottom = contentArea.bottom,
                left = 0f, right = contentArea.left
            )

            when (style) {

                BarChartStyle.STANDARD -> {

                    val mapper = PointMapper(
                        xSrcMin = 0f - offset,
                        xSrcMax = data.size - 1 + offset,
                        xDstMin = contentArea.left,
                        xDstMax = contentArea.right,
                        ySrcMin = 0f,
                        ySrcMax = data.maxValue,
                        yDstMin = contentArea.top,
                        yDstMax = contentArea.bottom
                    )

                    yAxis.draw(this, contentArea, yAxisArea, mapper, mapper.ySrcMin, mapper.ySrcMax)

                    val barWidth = 2 * w * mapper.xScale / data.bars.size
                    data.bars.forEachIndexed { series, value ->
                        value.values.forEachIndexed { pos, v ->
                            drawRect(
                                color = colors.resolve(series, value.color),
                                topLeft = Offset(
                                    x = mapper.x(pos - w) + series * barWidth,
                                    y = mapper.y(v)
                                ),
                                size = Size(
                                    width = barWidth,
                                    height = v * mapper.yScale
                                )
                            )
                        }
                    }
                }

                BarChartStyle.STACKED -> {

                    val series = data.bars.size
                    val values = data.size
                    val maxValues = FloatArray(values) { index -> data.bars.map { it.values[index] }.sum() }
                    val maxOfValues = maxValues.maxOf { it }

                    val mapper = PointMapper(
                        xSrcMin = 0f - offset,
                        xSrcMax = data.size - 1 + offset,
                        xDstMin = contentArea.left,
                        xDstMax = contentArea.right,
                        ySrcMin = 0f,
                        ySrcMax = maxOfValues,
                        yDstMin = contentArea.top,
                        yDstMax = contentArea.bottom
                    )

                    val barWidth = 2 * w * mapper.xScale

                    yAxis.draw(this, contentArea, yAxisArea, mapper, mapper.ySrcMin, mapper.ySrcMax)

                    for (i in (values - 1) downTo 0) {
                        var counter = maxValues[i]
                        for (j in (series - 1) downTo 0) {
                            drawRect(
                                color = colors.resolve(j, data.bars[j].color),
                                topLeft = Offset(
                                    x = mapper.x(i - w),
                                    y = mapper.y(counter)
                                ),
                                size = Size(
                                    width = barWidth,
                                    height = counter * mapper.yScale
                                )
                            )
                            counter -= data.bars[j].values[i]
                        }
                    }
                }

                BarChartStyle.PROPORTION -> {

                    val series = data.bars.size
                    val values = data.size
                    val maxValues = FloatArray(values) { index -> data.bars.map { it.values[index] }.sum() }

                    val mapper = PointMapper(
                        xSrcMin = 0f - offset,
                        xSrcMax = data.size - 1 + offset,
                        xDstMin = contentArea.left,
                        xDstMax = contentArea.right,
                        ySrcMin = 0f,
                        ySrcMax = 1f,
                        yDstMin = contentArea.top,
                        yDstMax = contentArea.bottom
                    )

                    yAxis.draw(this, contentArea, yAxisArea, mapper, mapper.ySrcMin, mapper.ySrcMax)

                    val barWidth = 2 * w * mapper.xScale

                    for (i in (values - 1) downTo 0) {
                        var counter = maxValues[i]
                        for (j in (series - 1) downTo 0) {
                            drawRect(
                                color = colors.resolve(j, data.bars[j].color),
                                topLeft = Offset(
                                    x = mapper.x(i - w),
                                    y = mapper.y(counter / maxValues[i])
                                ),
                                size = Size(
                                    width = barWidth,
                                    height = (counter / maxValues[i]) * mapper.yScale
                                )
                            )
                            counter -= data.bars[j].values[i]
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
    colors: Colors = AutoColors
) {
    Box(modifier = Modifier.border(width = 1.dp, color = ChartsTheme.legendColor)) {
        LegendFlow(
            modifier = Modifier.padding(16.dp),
            data = data.bars.mapIndexed { index, item ->
                LegendEntry(
                    item.label,
                    colors.resolve(index, item.color)
                )
            }
        )
    }
}
