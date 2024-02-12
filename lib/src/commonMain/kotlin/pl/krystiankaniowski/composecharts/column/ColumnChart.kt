package pl.krystiankaniowski.composecharts.column

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.rememberTextMeasurer
import pl.krystiankaniowski.composecharts.axis.XAxis
import pl.krystiankaniowski.composecharts.axis.YAxis
import pl.krystiankaniowski.composecharts.data.Series
import pl.krystiankaniowski.composecharts.internal.AxisScale
import pl.krystiankaniowski.composecharts.internal.ChartChoreographer
import pl.krystiankaniowski.composecharts.internal.PointMapper
import pl.krystiankaniowski.composecharts.legend.Legend
import pl.krystiankaniowski.composecharts.legend.LegendPosition

object ColumnChart {

    data class Data(
        val labels: List<String>,
        val columns: List<Column>,
    ) {
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

    data class Column(
        override val label: String,
        val values: List<Float>,
        override val color: Color,
    ) : Series

    enum class Style {
        GROUPED,
        STACKED,
        PROPORTIONAL,
    }
}

@Composable
fun ColumnChart(
    modifier: Modifier = Modifier,
    data: ColumnChart.Data,
    style: ColumnChart.Style = ColumnChart.Style.GROUPED,
    title: (@Composable () -> Unit)? = null,
    xAxis: XAxis.Drawer = XAxis.Default(),
    yAxis: YAxis.Drawer = YAxis.Default(),
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {

    val scale = remember(data) {
        AxisScale.create(
            min = 0f,
            max = when (style) {
                ColumnChart.Style.GROUPED -> data.maxValue
                ColumnChart.Style.STACKED -> FloatArray(data.size) { index -> data.columns.map { it.values[index] }.sum() }.max()
                ColumnChart.Style.PROPORTIONAL -> 1f
            },
        )
    }

    val xAxisValues = remember(data) {
        data.labels.mapIndexed { index, label ->
            XAxis.Value(
                label = label,
                value = index.toFloat(),
                helperLine = null,
            )
        }
    }

    val yAxisValues = remember(scale, style) {
        scale.getHelperLines().map {
            YAxis.Value(
                label =
                when (style) {
                    ColumnChart.Style.GROUPED, ColumnChart.Style.STACKED -> scale.formatValue(it)
                    ColumnChart.Style.PROPORTIONAL -> "${(it * 100).toInt()}%"
                },
                value = it,
            )
        }
    }

    ChartChoreographer(
        modifier = modifier,
        title = title,
        legend = { Legend(data = data.columns) },
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
            val xAxisArea = Rect(
                top = contentArea.bottom,
                bottom = size.height,
                left = contentArea.left,
                right = contentArea.right,
            )
            val yAxisArea = Rect(
                top = contentArea.top,
                bottom = contentArea.bottom,
                left = 0f,
                right = contentArea.left,
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

            xAxis.draw(this, textMeasurer, contentArea, xAxisArea, mapper, xAxisValues)
            yAxis.draw(drawScope = this, textMeasurer = textMeasurer, chartScope = contentArea, yAxisScope = yAxisArea, yMapper = mapper, values = yAxisValues)

            when (style) {

                ColumnChart.Style.GROUPED -> {

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
                                ),
                            )
                        }
                    }
                }

                ColumnChart.Style.STACKED -> {

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
                                ),
                            )
                            counter -= data.columns[j].values[i]
                        }
                    }
                }

                ColumnChart.Style.PROPORTIONAL -> {

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
                                ),
                            )
                            counter -= data.columns[j].values[i]
                        }
                    }
                }
            }
        }
    }
}
