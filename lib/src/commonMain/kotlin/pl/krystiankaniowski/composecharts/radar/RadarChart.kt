package pl.krystiankaniowski.composecharts.radar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.krystiankaniowski.composecharts.ChartsTheme
import pl.krystiankaniowski.composecharts.internal.ChartChoreographer
import pl.krystiankaniowski.composecharts.internal.TextAnchorX
import pl.krystiankaniowski.composecharts.internal.TextAnchorY
import pl.krystiankaniowski.composecharts.internal.drawText
import pl.krystiankaniowski.composecharts.legend.LegendEntry
import pl.krystiankaniowski.composecharts.legend.LegendFlow
import pl.krystiankaniowski.composecharts.legend.LegendPosition
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

data class RadarChartData(
    val labels: List<String>,
    val entries: List<Entry>
) {

    data class Entry(
        val name: String,
        val color: Color,
        val values: List<Float>
    )
}

data class RadarChartStyle(
    val lineColor: Color = Color.Gray,
    val lineWidth: Dp = 1.dp,
    val labelColor: Color = Color.Gray,
    val valueColor: Color = Color.Gray,
)

@Composable
fun radarChartStyle() = RadarChartStyle(
    lineColor = if (MaterialTheme.colors.isLight) Color.LightGray else Color.DarkGray,
    lineWidth = 1.dp,
    labelColor = Color.Gray,
    valueColor = Color.Gray,
)

@Composable
fun RadarChart(
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit)? = null,
    data: RadarChartData,
    style: RadarChartStyle = radarChartStyle(),
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {
    val maxValue = data.entries.maxOf { it.values.maxOf { it } }

    ChartChoreographer(
        modifier = modifier,
        title = title,
        legend = { RadarLegend(data) },
        legendPosition = legendPosition,
    ) {
        BoxWithConstraints(Modifier.fillMaxSize()) {

            val density = LocalDensity.current.density.dp
            val size = androidx.compose.ui.unit.min(this.maxHeight, this.maxWidth)

            val chartArea = Rect(
                top = 0f, bottom = size.value * density.value,
                left = 0f, right = size.value * density.value,
            )
            val r = chartArea.center.x

            Canvas(
                modifier = Modifier
                    .size(size)
                    .align(Alignment.Center)
            ) {
                data.labels.forEachIndexed { index, it ->
                    val angle = 2 * PI / data.labels.size * index + PI
                    val point = Offset(
                        x = r * sin(angle).toFloat() + chartArea.center.x,
                        y = r * cos(angle).toFloat() + chartArea.center.y
                    )
                    drawLine(
                        color = style.lineColor,
                        start = chartArea.center,
                        end = point,
                        strokeWidth = style.lineWidth.toPx()
                    )
                    drawText(
                        text = it,
                        x = point.x,
                        y = point.y,
                        color = style.labelColor,
                        size = 16.sp.toPx(),
                        anchorX = TextAnchorX.Left,
                        anchorY = TextAnchorY.Center,
                    )
                }

                (0..maxValue.toInt()).forEach { value ->
                    val path = Path()
                    (0..data.labels.size).forEachIndexed { index, it ->
                        val angle = 2 * PI / data.labels.size * index + PI
                        if (index == 0) {
                            val point = Offset(
                                x = r * (value / maxValue) * sin(angle).toFloat() + chartArea.center.x,
                                y = r * (value / maxValue) * cos(angle).toFloat() + chartArea.center.y
                            )
                            path.moveTo(
                                x = point.x,
                                y = point.y
                            )

                            drawText(
                                text = " $value",
                                x = point.x,
                                y = point.y,
                                color = style.valueColor,
                                size = 14.sp.toPx(),
                                anchorX = TextAnchorX.Left,
                                anchorY = TextAnchorY.Center,
                            )

                        } else {
                            path.lineTo(
                                x = r * (value / maxValue) * sin(angle).toFloat() + chartArea.center.x,
                                y = r * (value / maxValue) * cos(angle).toFloat() + chartArea.center.y
                            )
                        }
                    }
                    path.close()
                    drawPath(path = path, color = style.lineColor, style = Stroke(width = style.lineWidth.toPx()))
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
    }
}

@Composable
private fun RadarLegend(data: RadarChartData) {
    Box(modifier = Modifier.border(width = 1.dp, color = ChartsTheme.legendColor)) {
        LegendFlow(
            modifier = Modifier.padding(16.dp),
            data = data.entries.map { entry ->
                LegendEntry(
                    entry.name,
                    entry.color,
                )
            }
        )
    }
}