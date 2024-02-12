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

object PolarChart {

    data class Data(
        val entries: List<Entry>,
    )

    data class Entry(
        override val label: String,
        override val color: Color,
        val value: Float,
    ) : Series

    data class PolarChartStyle(
        val lineColor: Color = Color.Gray,
        val lineWidth: Dp = 1.dp,
        val labelColor: Color = Color.Gray,
        val valueColor: Color = Color.Gray,
    )
}

@Composable
fun polarChartStyle() = PolarChart.PolarChartStyle(
    lineColor = if (MaterialTheme.colors.isLight) Color.LightGray else Color.DarkGray,
    lineWidth = 1.dp,
    labelColor = Color.Gray,
    valueColor = Color.Gray,
)

@Composable
fun PolarChart(
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit)? = null,
    data: PolarChart.Data,
    style: PolarChart.PolarChartStyle = polarChartStyle(),
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {
    val scale = remember(data) {
        AxisScale.create(
            min = 0f,
            max = data.entries.maxOf { it.value },
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

            val textMeasurer = rememberTextMeasurer()

            Canvas(
                modifier = Modifier
                    .size(size)
                    .align(Alignment.Center),
            ) {
                scale.getHelperLines().forEach { value ->
                    drawCircle(
                        color = style.lineColor,
                        radius = ((chartArea.width / 2) / chartMaxValue * value),
                        center = chartArea.center,
                        style = Stroke(width = style.lineWidth.toPx()),
                    )
                    drawText(
                        textMeasurer = textMeasurer,
                        text = scale.formatValue(value),
                        x = chartArea.center.x,
                        y = (chartArea.center.y - ((chartArea.height / 2) / chartMaxValue) * value),
                        color = style.valueColor,
                        size = 14.sp.toPx(),
                        anchorX = TextAnchorX.Left,
                        anchorY = TextAnchorY.Center,
                    )
                }

                data.entries.forEachIndexed { index, entry ->
                    val factor = entry.value / chartMaxValue
                    drawArc(
                        color = entry.color.copy(alpha = 0.3f),
                        startAngle = 360f / data.entries.size * index - 90f,
                        sweepAngle = 360f / data.entries.size,
                        useCenter = true,
                        topLeft = chartArea.center - Offset(chartArea.width / 2 * factor, chartArea.height / 2 * factor),
                        size = chartArea.size * factor,
                    )
                    drawArc(
                        color = entry.color,
                        startAngle = 360f / data.entries.size * index - 90f,
                        sweepAngle = 360f / data.entries.size,
                        useCenter = true,
                        topLeft = chartArea.center - Offset(chartArea.width / 2 * factor, chartArea.height / 2 * factor),
                        size = chartArea.size * factor,
                        style = Stroke(1.dp.toPx()),
                    )
                }
            }
        }
    }
}
