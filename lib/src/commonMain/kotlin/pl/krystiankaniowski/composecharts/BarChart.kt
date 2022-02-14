package pl.krystiankaniowski.composecharts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.TextLayoutInput
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextPainter
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import kotlin.math.max

enum class BarChartStyle {
    STANDARD,
    COMBINE,
    PROPORTION
}

data class BarChartData(val label: String, val color: Color, val data: List<Float>)

@Composable
fun BarChart(data: List<BarChartData>, style: BarChartStyle = BarChartStyle.STANDARD) {
    Box(modifier = Modifier.padding(16.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {

            val width = size.width
            val height = size.height

            val proportion = 2f
            val offset =
                width / (proportion * data.first().data.size + data.first().data.size + 1)
            val barWidth = offset * proportion

            when (style) {
                BarChartStyle.STANDARD -> {
                    val maxValue = data.maxOf { it.data.maxOf { it } }
                    data.forEachIndexed { series, value ->
                        value.data.reversed().forEachIndexed { pos, v ->
                            drawRect(
                                color = value.color,
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
                    val values = data.first().data.size
                    val maxValues =
                        FloatArray(values) { index -> data.map { it.data[index] }.sum() }
                    val maxOfValues = maxValues.maxOf { it }
                    for (i in (values - 1) downTo 0) {
                        var counter = maxValues[i]
                        for (j in (series - 1) downTo 0) {
                            drawRect(
                                color = data[j].color,
                                topLeft = Offset(
                                    x = i * barWidth + (i + 1) * offset,
                                    y = ((maxOfValues - counter) / maxOfValues) * height
                                ),
                                size = Size(
                                    width = barWidth,
                                    height = (counter / maxOfValues) * height
                                )
                            )
                            counter -= data[j].data[i]
                        }
                    }
                }
                BarChartStyle.PROPORTION -> {
                    val series = data.size
                    val values = data.first().data.size
                    val maxValues =
                        FloatArray(values) { index -> data.map { it.data[index] }.sum() }
                    for (i in (values - 1) downTo 0) {
                        var counter = maxValues[i]
                        for (j in (series - 1) downTo 0) {
                            drawRect(
                                color = data[j].color,
                                topLeft = Offset(
                                    x = i * barWidth + (i + 1) * offset,
                                    y = ((maxValues[i] - counter) / maxValues[i]) * height
                                ),
                                size = Size(
                                    width = barWidth,
                                    height = (counter / maxValues[i]) * height
                                )
                            )
                            counter -= data[j].data[i]
                        }
                    }
                }
            }
        }
    }
}