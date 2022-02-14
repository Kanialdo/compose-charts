package pl.krystiankaniowski.composecharts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextPainter
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import kotlin.math.max

enum class BarChartStyle {
    STANDARD,
    COMBINE,
    PROPORTION
}

data class BarChartData(
    val label: String,
    val values: List<Float>,
    val color: Color = Color.Unspecified,
)

@Composable
fun BarChart(
    data: List<BarChartData>,
    colors: Colors = AutoColors,
    style: BarChartStyle = BarChartStyle.STANDARD
) {
    Box(modifier = Modifier.padding(16.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {

            val width = size.width
            val height = size.height

            val proportion = 2f
            val offset =
                width / (proportion * data.first().values.size + data.first().values.size + 1)
            val barWidth = offset * proportion

            when (style) {
                BarChartStyle.STANDARD -> {
                    val maxValue = data.maxOf { it.values.maxOf { it } }
                    data.forEachIndexed { series, value ->
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
                BarChartStyle.COMBINE -> {
                    val series = data.size
                    val values = data.first().values.size
                    val maxValues =
                        FloatArray(values) { index -> data.map { it.values[index] }.sum() }
                    val maxOfValues = maxValues.maxOf { it }
                    for (i in (values - 1) downTo 0) {
                        var counter = maxValues[i]
                        for (j in (series - 1) downTo 0) {
                            drawRect(
                                color = colors.resolve(series - 1 - j, data[j].color),
                                topLeft = Offset(
                                    x = i * barWidth + (i + 1) * offset,
                                    y = ((maxOfValues - counter) / maxOfValues) * height
                                ),
                                size = Size(
                                    width = barWidth,
                                    height = (counter / maxOfValues) * height
                                )
                            )
                            counter -= data[j].values[i]
                        }
                    }
                }
                BarChartStyle.PROPORTION -> {
                    val series = data.size
                    val values = data.first().values.size
                    val maxValues =
                        FloatArray(values) { index -> data.map { it.values[index] }.sum() }
                    for (i in (values - 1) downTo 0) {
                        var counter = maxValues[i]
                        for (j in (series - 1) downTo 0) {
                            drawRect(
                                color = colors.resolve(series - 1 - j, data[j].color),
                                topLeft = Offset(
                                    x = i * barWidth + (i + 1) * offset,
                                    y = ((maxValues[i] - counter) / maxValues[i]) * height
                                ),
                                size = Size(
                                    width = barWidth,
                                    height = (counter / maxValues[i]) * height
                                )
                            )
                            counter -= data[j].values[i]
                        }
                    }
                }
            }
        }
    }
}