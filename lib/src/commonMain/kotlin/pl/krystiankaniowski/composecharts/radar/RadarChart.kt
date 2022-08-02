package pl.krystiankaniowski.composecharts.radar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

data class RadarChartData(
    val labels: List<String>,
    val entries: List<Entry>
) {

    data class Entry(
        val color: Color,
        val values: List<Float>
    )
}

@Composable
fun RadarChart(
    data: RadarChartData,
) {
    val line: Color = Color.Gray

    val maxValue = data.entries.maxOf { it.values.maxOf { it } }

    val density = LocalDensity.current.density.dp

    val chartArea = Rect(
        top = 0f, bottom = 400f * density.value,
        left = 0f, right = 400f * density.value
    )

    val r = chartArea.center.x

    Canvas(Modifier.size(400.dp)) {
        data.labels.forEachIndexed { index, it ->
            val angle = 2 * PI / data.labels.size * index + PI
            drawLine(
                color = line,
                start = chartArea.center,
                end = Offset(
                    x = r * sin(angle).toFloat() + chartArea.center.x,
                    y = r * cos(angle).toFloat() + chartArea.center.y
                ),
                strokeWidth = 1f
            )
        }

        (0..maxValue.toInt()).forEach { value ->
            val path = Path()
            (0..data.labels.size).forEachIndexed { index, it ->
                val angle = 2 * PI / data.labels.size * index + PI
                if (index == 0) {
                    path.moveTo(
                        x = r * (value / maxValue) * sin(angle).toFloat() + chartArea.center.x,
                        y = r * (value / maxValue) * cos(angle).toFloat() + chartArea.center.y
                    )
                } else {
                    path.lineTo(
                        x = r * (value / maxValue) * sin(angle).toFloat() + chartArea.center.x,
                        y = r * (value / maxValue) * cos(angle).toFloat() + chartArea.center.y
                    )
                }
            }
            path.close()
            drawPath(path = path, color = Color.LightGray, style = Stroke(width = 1f))
        }


        data.entries.forEach { entry ->
            val path = Path()
            entry.values.forEachIndexed { index, it ->
                val angle = 2 * PI / data.labels.size * index + PI
                if (index == 0) {
                    path.moveTo(
                        x = r * (it / maxValue) * sin(angle).toFloat() + chartArea.center.x,
                        y = r * (it / maxValue) * cos(angle).toFloat() + chartArea.center.y
                    )
                } else {
                    path.lineTo(
                        x = r * (it / maxValue) * sin(angle).toFloat() + chartArea.center.x,
                        y = r * (it / maxValue) * cos(angle).toFloat() + chartArea.center.y
                    )
                }
            }
            path.close()
            drawPath(path = path, color = entry.color.copy(alpha = 0.3f), style = Fill)
            drawPath(path = path, color = entry.color, style = Stroke(width = 1f))
        }
    }
}