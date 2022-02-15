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
import pl.krystiankaniowski.composecharts.internal.XMapper
import pl.krystiankaniowski.composecharts.internal.YMapper

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

            val maxValue = data.maxOf { it.values.maxOf { it } }

            val xMapper = XMapper(0f, data.first().values.size.toFloat(), width)
            val yMapper = YMapper(0f, maxValue, height)

            data.forEachIndexed { index, series ->
                val color = colors.resolve(index, series.color)
                val path = Path()
                series.values.forEachIndexed { dataIndex, point ->
                    if (dataIndex == 0) {
                        path.moveTo(
                            x = xMapper.x(dataIndex + 0.5f),
                            y = yMapper.y(point)
                        )
                    } else {
                        path.lineTo(
                            x = xMapper.x(dataIndex + 0.5f),
                            y = yMapper.y(point)
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
                            x = xMapper.x(dataIndex + 0.5f),
                            y = yMapper.y(point),
                        ),
                        radius = 5f
                    )
                }
            }
        }
    }
}