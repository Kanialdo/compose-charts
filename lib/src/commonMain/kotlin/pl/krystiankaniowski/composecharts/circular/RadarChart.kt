package pl.krystiankaniowski.composecharts.circular

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.krystiankaniowski.composecharts.data.Series
import pl.krystiankaniowski.composecharts.internal.*
import pl.krystiankaniowski.composecharts.legend.Legend
import pl.krystiankaniowski.composecharts.legend.LegendPosition
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

object RadarChart {

    data class Data(
        val labels: List<String>,
        val entries: List<Entry>,
    )

    data class Entry(
        override val label: String,
        override val color: Color,
        val values: List<Float>,
    ) : Series

    data class RadarChartStyle(
        val lineColor: Color = Color.Gray,
        val lineWidth: Dp = 1.dp,
        val labelColor: Color = Color.Gray,
        val valueColor: Color = Color.Gray,
    )
}

@Composable
fun radarChartStyle() = RadarChart.RadarChartStyle(
    lineColor = if (MaterialTheme.colors.isLight) Color.LightGray else Color.DarkGray,
    lineWidth = 1.dp,
    labelColor = Color.Gray,
    valueColor = Color.Gray,
)

@Composable
fun RadarChart(
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit)? = null,
    data: RadarChart.Data,
    style: RadarChart.RadarChartStyle = radarChartStyle(),
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {

    val scale = remember(data) {
        AxisScale.create(
            min = 0f,
            max = data.entries.maxOf { it.values.maxOf { it } },
        )
    }
    val chartMaxValue = scale.max

    ChartChoreographer(
        modifier = modifier,
        title = title,
        legend = { Legend(data = data.entries) },
        legendPosition = legendPosition,
    ) {
        BoxWithConstraints(Modifier.fillMaxSize()) {

            val density = LocalDensity.current.density.dp
            val size = androidx.compose.ui.unit.min(this.maxHeight, this.maxWidth)

            val chartArea = Rect(
                top = 0f,
                bottom = size.value * density.value,
                left = 0f,
                right = size.value * density.value,
            )
            val r = chartArea.width / 2

            val textMeasurer = rememberTextMeasurer()

            Canvas(
                modifier = Modifier
                    .size(size)
                    .align(Alignment.Center),
            ) {
                data.labels.forEachIndexed { index, value ->
                    val angle = 2 * PI / data.labels.size * index + PI
                    val point = Offset(
                        x = r * sin(angle).toFloat() + chartArea.center.x,
                        y = r * cos(angle).toFloat() + chartArea.center.y,
                    )
                    drawLine(
                        color = style.lineColor,
                        start = chartArea.center,
                        end = point,
                        strokeWidth = style.lineWidth.toPx(),
                    )
                    drawText(
                        textMeasurer = textMeasurer,
                        text = value,
                        x = point.x,
                        y = point.y,
                        color = style.labelColor,
                        size = 16.sp.toPx(),
                        anchorX = TextAnchorX.Left,
                        anchorY = TextAnchorY.Center,
                    )
                }

                scale.getHelperLines().forEach { value ->
                    val path = Path()
                    (0..data.labels.size).forEachIndexed { index, _ ->
                        val angle = 2 * PI / data.labels.size * index + PI
                        if (index == 0) {
                            val point = Offset(
                                x = (r * (value / chartMaxValue) * sin(angle) + chartArea.center.x).toFloat(),
                                y = (r * (value / chartMaxValue) * cos(angle) + chartArea.center.y).toFloat(),
                            )
                            path.moveTo(
                                x = point.x,
                                y = point.y,
                            )

                            drawText(
                                textMeasurer = textMeasurer,
                                text = scale.formatValue(value),
                                x = point.x,
                                y = point.y,
                                color = style.valueColor,
                                size = 14.sp.toPx(),
                                anchorX = TextAnchorX.Left,
                                anchorY = TextAnchorY.Center,
                            )
                        } else {
                            path.lineTo(
                                x = (r * (value / chartMaxValue) * sin(angle) + chartArea.center.x).toFloat(),
                                y = (r * (value / chartMaxValue) * cos(angle) + chartArea.center.y).toFloat(),
                            )
                        }
                    }
                    path.close()
                    drawPath(path = path, color = style.lineColor, style = Stroke(width = style.lineWidth.toPx()))
                }

                data.entries.forEach { entry ->
                    val path = Path()
                    entry.values.forEachIndexed { index, value ->
                        val angle = 2 * PI / data.labels.size * index + PI
                        if (index == 0) {
                            path.moveTo(
                                x = r * (value / chartMaxValue) * sin(angle).toFloat() + chartArea.center.x,
                                y = r * (value / chartMaxValue) * cos(angle).toFloat() + chartArea.center.y,
                            )
                        } else {
                            path.lineTo(
                                x = r * (value / chartMaxValue) * sin(angle).toFloat() + chartArea.center.x,
                                y = r * (value / chartMaxValue) * cos(angle).toFloat() + chartArea.center.y,
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
