package pl.krystiankaniowski.composecharts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp

data class PieChartData(
    val label: String,
    val value: Float,
    val color: Color = Color.Unspecified
)

@Composable
fun PieChart(
    data: List<PieChartData>,
    colors: Colors = AutoColors
) {

    val sum = data.sumOf { it.value.toDouble() }.toFloat()

    Box(modifier = Modifier.padding(16.dp)) {
        Canvas(Modifier) {
            drawIntoCanvas { canvas ->
                var start = 0f
                data.forEachIndexed { index, slice ->
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
    }
}