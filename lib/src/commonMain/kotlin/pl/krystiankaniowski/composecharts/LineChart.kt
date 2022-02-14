package pl.krystiankaniowski.composecharts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

data class LineChartData(
    val label: String,
    val values: List<Float>,
    val color: Color = Color.Unspecified
)

@Composable
fun LineChart(
    data: List<LineChartData>,
    colors: Colors = AutoColors
) {
    Box(modifier = Modifier.padding(16.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {

            val width = size.width
            val height = size.height

            val barWidth = width / data.first().values.size

            val maxValue = data.maxOf { it.values.maxOf { it } }

            data.forEachIndexed { index, series ->
                val color = colors.resolve(index, series.color)
                val path = Path()
                series.values.forEachIndexed { dataIndex, point ->
                    if (dataIndex == 0) {
                        path.moveTo(
                            x = dataIndex * barWidth + barWidth / 2f,
                            y = ((maxValue - point) / maxValue) * height
                        )
                    } else {
                        path.lineTo(
                            x = dataIndex * barWidth + barWidth / 2f,
                            y = ((maxValue - point) / maxValue) * height
                        )
                    }
                }
                drawPath(
                    color = color,
                    path = path,
                    style = Stroke(
                        width = 5f
                    )
                )
                series.values.forEachIndexed { dataIndex, point ->
                    drawCircle(
                        color = color,
                        center = Offset(
                            x = dataIndex * barWidth + barWidth / 2f,
                            y = ((maxValue - point) / maxValue) * height
                        ),
                        radius = 5f
                    )
                }
            }
        }
    }
}