package pl.krystiankaniowski.composecharts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp

data class PieChartData(val slices: List<Slice>) {

    constructor(vararg slices: Slice) : this(slices.toList())

    data class Slice(
        val label: String,
        val value: Float,
        val color: Color = Color.Unspecified
    )

    internal val sum = slices.sumOf { it.value.toDouble() }.toFloat()

    internal val size: Int get() = slices.size
}

@Composable
fun PieChart(
    data: PieChartData,
    colors: Colors = AutoColors,
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {

    val sum = data.sum

    Column(modifier = Modifier.padding(16.dp)) {
        if (legendPosition == LegendPosition.Top) {
            PieLegend(data, colors)
        }
        Canvas(modifier = Modifier.weight(1f).fillMaxWidth().padding(16.dp)) {
            drawIntoCanvas { canvas ->
                var start = 0f
                data.slices.forEachIndexed { index, slice ->
                    val end = (slice.value / sum) * 360f
                    canvas.drawArc(
                        rect = Rect(Offset(0f, 0f), Offset(200f, 200f)),
                        startAngle = start,
                        sweepAngle = end,
                        useCenter = true,
                        paint = Paint().apply { color = colors.resolve(index, slice.color) }
                    )
                    start += end
                }
            }
        }
        if (legendPosition == LegendPosition.Bottom) {
            PieLegend(data, colors)
        }
    }
}

@Composable
private fun PieLegend(
    data: PieChartData,
    colors: Colors = AutoColors
) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Box(modifier = Modifier.border(width = 1.dp, color = Color.LightGray)) {
            LegendFlow(
                modifier = Modifier.padding(16.dp),
                data = data.slices.mapIndexed { index, item ->
                    LegendEntry(
                        item.label,
                        colors.resolve(index, item.color)
                    )
                })
        }
    }
}