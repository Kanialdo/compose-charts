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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

data class LineChartData(val lines: List<Line>) {

    constructor(vararg lines: Line) : this(lines.toList())

    data class Line(
        val label: String,
        val values: List<Float>,
        val color: Color = Color.Unspecified
    )

    init {
        check(lines.first().values.size.let { size -> lines.all { it.values.size == size } }) {
            "All lines have to contains same amount of entries"
        }
    }

    internal val minValue = lines.minOf { it.values.minOf { it } }
    internal val maxValue = lines.maxOf { it.values.maxOf { it } }

    internal val size: Int get() = lines.first().values.size
}

@Composable
fun LineChart(
    data: LineChartData,
    colors: Colors = AutoColors,
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {
    Column(modifier = Modifier.padding(16.dp)) {
        if (legendPosition == LegendPosition.Top) {
            LineLegend(data, colors)
        }
        Canvas(modifier = Modifier.weight(1f).fillMaxWidth().padding(16.dp)) {

            val width = size.width
            val height = size.height

            val barWidth = width / data.size
            val maxValue = data.maxValue

            data.lines.forEachIndexed { index, series ->
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
        if (legendPosition == LegendPosition.Bottom) {
            LineLegend(data, colors)
        }
    }
}

@Composable
private fun LineLegend(
    data: LineChartData,
    colors: Colors = AutoColors
) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Box(modifier = Modifier.border(width = 1.dp, color = Color.LightGray)) {
            LegendFlow(
                modifier = Modifier.padding(16.dp),
                data = data.lines.mapIndexed { index, item ->
                    LegendEntry(
                        item.label,
                        colors.resolve(index, item.color)
                    )
                }
            )
        }
    }
}