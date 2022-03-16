package pl.krystiankaniowski.composecharts.bar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pl.krystiankaniowski.composecharts.AutoColors
import pl.krystiankaniowski.composecharts.Colors
import pl.krystiankaniowski.composecharts.internal.ChartChoreographer
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
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {
    ChartChoreographer(
        title = title,
        legend = { BarLegend(data, colors) },
        legendPosition = legendPosition,
    ) {
        Canvas(Modifier.fillMaxSize()) {

            val width = size.width
            val height = size.height

            val proportion = 2f
            val offset = width / (proportion * data.size + data.size + 1)
            val barWidth = offset * proportion

            when (style) {
                BarChartStyle.STANDARD -> {
                    val maxValue = data.maxValue
                    data.bars.forEachIndexed { series, value ->
                        value.values.forEachIndexed { pos, v ->
                            drawRect(
                                color = colors.resolve(series, value.color),
                                topLeft = Offset(
                                    x = pos * barWidth + (pos + 1) * offset + (barWidth / data.size) * series,
                                    y = ((maxValue - v) / maxValue) * height
                                ),
                                size = Size(
                                    width = barWidth / data.size,
                                    height = (v / maxValue) * height
                                )
                            )
                        }
                    }
                }
                BarChartStyle.STACKED -> {
                    val series = data.bars.size
                    val values = data.size
                    val maxValues =
                        FloatArray(values) { index -> data.bars.map { it.values[index] }.sum() }
                    val maxOfValues = maxValues.maxOf { it }
                    for (i in (values - 1) downTo 0) {
                        var counter = maxValues[i]
                        for (j in (series - 1) downTo 0) {
                            drawRect(
                                color = colors.resolve(j, data.bars[j].color),
                                topLeft = Offset(
                                    x = i * barWidth + (i + 1) * offset,
                                    y = ((maxOfValues - counter) / maxOfValues) * height
                                ),
                                size = Size(
                                    width = barWidth,
                                    height = (counter / maxOfValues) * height
                                )
                            )
                            counter -= data.bars[j].values[i]
                        }
                    }
                }
                BarChartStyle.PROPORTION -> {
                    val series = data.bars.size
                    val values = data.size
                    val maxValues =
                        FloatArray(values) { index -> data.bars.map { it.values[index] }.sum() }
                    for (i in (values - 1) downTo 0) {
                        var counter = maxValues[i]
                        for (j in (series - 1) downTo 0) {
                            drawRect(
                                color = colors.resolve(j, data.bars[j].color),
                                topLeft = Offset(
                                    x = i * barWidth + (i + 1) * offset,
                                    y = ((maxValues[i] - counter) / maxValues[i]) * height
                                ),
                                size = Size(
                                    width = barWidth,
                                    height = (counter / maxValues[i]) * height
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
    Box(modifier = Modifier.border(width = 1.dp, color = Color.LightGray)) {
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
