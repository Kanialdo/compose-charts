package pl.krystiankaniowski.composecharts.pie

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import pl.krystiankaniowski.composecharts.ChartsTheme
import pl.krystiankaniowski.composecharts.internal.ChartChoreographer
import pl.krystiankaniowski.composecharts.legend.LegendEntry
import pl.krystiankaniowski.composecharts.legend.LegendFlow
import pl.krystiankaniowski.composecharts.legend.LegendPosition

data class PieChartData(val slices: List<Slice>) {

    constructor(vararg slices: Slice) : this(slices.toList())

    data class Slice(
        val label: String,
        val value: Float,
        val color: Color,
    )

    internal val sum = slices.sumOf { it.value.toDouble() }.toFloat()

    internal val size: Int get() = slices.size
}

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    data: PieChartData,
    title: (@Composable () -> Unit)? = null,
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {
    ChartChoreographer(
        modifier = modifier,
        title = title,
        legend = { PieLegend(data) },
        legendPosition = legendPosition,
    ) {
        Canvas(Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            val localSize = minOf(width, height)

            drawIntoCanvas { canvas ->
                var start = -90f
                data.slices.forEachIndexed { index, slice ->
                    val end = (slice.value / data.sum) * 360f
                    canvas.drawArc(
                        rect = Rect(
                            center = Offset(width / 2, height / 2),
                            radius = localSize / 2
                        ),
                        startAngle = start,
                        sweepAngle = end,
                        useCenter = true,
                        paint = Paint().apply { color = slice.color },
                    )
                    start += end
                }
            }
        }
    }
}

@Composable
private fun PieLegend(
    data: PieChartData,
) {
    Box(modifier = Modifier.border(width = 1.dp, color = ChartsTheme.legendColor)) {
        LegendFlow(
            modifier = Modifier.padding(16.dp),
            data = data.slices.map { item ->
                LegendEntry(
                    item.label,
                    item.color,
                )
            }
        )
    }
}