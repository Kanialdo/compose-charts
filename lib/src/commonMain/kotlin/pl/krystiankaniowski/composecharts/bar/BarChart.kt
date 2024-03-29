package pl.krystiankaniowski.composecharts.bar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.rememberTextMeasurer
import pl.krystiankaniowski.composecharts.axis.XAxis
import pl.krystiankaniowski.composecharts.axis.YAxis
import pl.krystiankaniowski.composecharts.data.ChartColor
import pl.krystiankaniowski.composecharts.data.Series
import pl.krystiankaniowski.composecharts.internal.AxisScale
import pl.krystiankaniowski.composecharts.internal.ChartChoreographer
import pl.krystiankaniowski.composecharts.internal.PointMapper
import pl.krystiankaniowski.composecharts.legend.Legend
import pl.krystiankaniowski.composecharts.legend.LegendPosition

object BarChart {

    data class Data(
        val labels: List<String>,
        val bars: List<Bar>,
    ) {

        init {
            check(bars.first().values.size.let { size -> bars.all { it.values.size == size } }) {
                "All bars have to contains same amount of entries"
            }
        }

        internal val minValue = bars.minOf { it.values.minOf { it } }
        internal val maxValue = bars.maxOf { it.values.maxOf { it } }

        internal val size: Int get() = bars.first().values.size
    }

    data class Bar(
        override val label: String,
        override val color: ChartColor.Solid,
        val values: List<Float>,
    ) : Series

    enum class Style {
        GROUPED,
        STACKED,
        PROPORTIONAL,
    }
}

@Composable
fun BarChart(
    modifier: Modifier = Modifier,
    data: BarChart.Data,
    style: BarChart.Style = BarChart.Style.GROUPED,
    title: (@Composable () -> Unit)? = null,
    yAxis: YAxis.Drawer = YAxis.Default(),
    xAxis: XAxis.Drawer = XAxis.Default(),
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {

    val scale = remember(data) {
        AxisScale.create(
            min = 0f,
            max = when (style) {
                BarChart.Style.GROUPED -> data.maxValue
                BarChart.Style.STACKED -> FloatArray(data.size) { index -> data.bars.map { it.values[index] }.sum() }.max()
                BarChart.Style.PROPORTIONAL -> 1f
            },
        )
    }

    val xAxisValues = remember(scale, style) {
        scale.getHelperLines().map {
            XAxis.Value(
                label = when (style) {
                    BarChart.Style.GROUPED, BarChart.Style.STACKED -> scale.formatValue(it)
                    BarChart.Style.PROPORTIONAL -> "${(it * 100).toInt()}%"
                },
                value = it,
            )
        }
    }

    val yAxisValues = remember(data.labels) {
        data.labels.mapIndexed { index, label ->
            YAxis.Value(
                label = label,
                value = index.toFloat(),
                helperLine = null,
            )
        }
    }

    ChartChoreographer(
        modifier = modifier,
        title = title,
        legend = { Legend(data = data.bars) },
        legendPosition = legendPosition,
    ) {

        val textMeasurer = rememberTextMeasurer()

        Canvas(Modifier.fillMaxSize()) {

            val w = 0.25f
            val offset = 1f

            val contentArea = Rect(
                top = 0f,
                bottom = size.height - xAxis.requiredHeight(this, xAxisValues),
                left = yAxis.requiredWidth(this, textMeasurer, yAxisValues),
                right = size.width,
            )
            val yAxisArea = Rect(
                top = contentArea.top,
                bottom = contentArea.bottom,
                left = 0f,
                right = contentArea.left,
            )
            val xAxisArea = Rect(
                top = contentArea.bottom,
                bottom = size.height,
                left = contentArea.left,
                right = contentArea.right,
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

            yAxis.draw(this, textMeasurer, contentArea, yAxisArea, mapper, yAxisValues)
            xAxis.draw(this, textMeasurer, contentArea, xAxisArea, mapper, xAxisValues)

            when (style) {

                BarChart.Style.GROUPED -> {
                    val barHeight = 2 * w * mapper.yScale / data.bars.size
                    data.bars.forEachIndexed { series, value ->
                        value.values.forEachIndexed { pos, v ->
                            drawRect(
                                color = value.color.value,
                                topLeft = Offset(
                                    x = mapper.x(0),
                                    y = mapper.y(pos + w) + series * barHeight,
                                ),
                                size = Size(
                                    width = v * mapper.xScale,
                                    height = barHeight,
                                ),
                            )
                        }
                    }
                }

                BarChart.Style.STACKED -> {

                    val series = data.bars.size
                    val values = data.size
                    val maxValues = FloatArray(values) { index -> data.bars.map { it.values[index] }.sum() }

                    val barHeight = 2 * w * mapper.yScale

                    for (i in (values - 1) downTo 0) {
                        var counter = maxValues[i]
                        for (j in (series - 1) downTo 0) {
                            drawRect(
                                color = data.bars[j].color.value,
                                topLeft = Offset(
                                    x = mapper.x(0),
                                    y = mapper.y(i + w),
                                ),
                                size = Size(
                                    width = counter * mapper.xScale,
                                    height = barHeight,
                                ),
                            )
                            counter -= data.bars[j].values[i]
                        }
                    }
                }

                BarChart.Style.PROPORTIONAL -> {

                    val series = data.bars.size
                    val values = data.size
                    val maxValues = FloatArray(values) { index -> data.bars.map { it.values[index] }.sum() }

                    val barHeight = 2 * w * mapper.yScale

                    for (i in (values - 1) downTo 0) {
                        var counter = maxValues[i]
                        for (j in (series - 1) downTo 0) {
                            drawRect(
                                color = data.bars[j].color.value,
                                topLeft = Offset(
                                    x = mapper.x(0),
                                    y = mapper.y(i + w),
                                ),
                                size = Size(
                                    width = (counter / maxValues[i]) * mapper.xScale,
                                    height = barHeight,
                                ),
                            )
                            counter -= data.bars[j].values[i]
                        }
                    }
                }
            }
        }
    }
}
