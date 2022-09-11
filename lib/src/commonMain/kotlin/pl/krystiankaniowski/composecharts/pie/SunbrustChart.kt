package pl.krystiankaniowski.composecharts.pie

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import pl.krystiankaniowski.composecharts.ChartsTheme
import pl.krystiankaniowski.composecharts.internal.ChartChoreographer
import pl.krystiankaniowski.composecharts.legend.LegendEntry
import pl.krystiankaniowski.composecharts.legend.LegendFlow
import pl.krystiankaniowski.composecharts.legend.LegendPosition

data class SunbrustChartData(val slices: List<Slice>) {

    constructor(vararg slices: Slice) : this(slices.toList())

    data class Slice(
        val label: String,
        val value: Float,
        val color: Color,
        val subSlices: List<Slice> = emptyList(),
    )
}

private fun SunbrustChartData.deep(): Int = slices.maxOf { it.deep(1) }
private fun SunbrustChartData.Slice.deep(current: Int): Int = subSlices.maxOfOrNull { it.deep(current = current + 1) } ?: current

@Composable
fun SunbrustChart(
    modifier: Modifier = Modifier,
    data: SunbrustChartData,
    title: (@Composable () -> Unit)? = null,
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {
    ChartChoreographer(
        modifier = modifier,
        title = title,
        legend = { SunbrustLegend(data) },
        legendPosition = legendPosition,
    ) {

        val dataSum = remember(data) { data.slices.sumOf { it.value.toDouble() }.toFloat() }
        val maxLevel = data.deep()

        Canvas(Modifier.fillMaxSize()) {

            val minSize = minOf(size.width, size.height)
            val topLeft = Offset((size.width - minSize) / 2, (size.height - minSize) / 2)
            val size = Size(minSize, minSize)
            val lineWidth = minSize / (maxLevel + 1) / 2

            var currentAngle = -90f

            data.slices.forEach { slice ->
                val sliceAngle = slice.value / dataSum * 360f
                drawComponent(
                    slice = slice,
                    startAngle = currentAngle,
                    sweepAngle = sliceAngle,
                    level = 1,
                    maxLevel = maxLevel,
                    lineWidth = lineWidth,
                    topLeft = topLeft,
                    size = size,
                )
                currentAngle += sliceAngle
            }
        }
    }
}

private fun DrawScope.drawComponent(
    slice: SunbrustChartData.Slice,
    startAngle: Float,
    sweepAngle: Float,
    level: Int,
    maxLevel: Int,
    lineWidth: Float,
    topLeft: Offset,
    size: Size,
) {
    val m = lineWidth * (maxLevel - level) + lineWidth / 2
    drawArc(
        topLeft = topLeft + Offset(m, m),
        size = Size(size.width - 2 * m, size.width - 2 * m),
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(width = lineWidth, cap = StrokeCap.Butt),
        color = slice.color,
    )
    var localStartAngle = startAngle
    slice.subSlices.forEach {
        val localSweepAngle = (it.value / slice.value) * sweepAngle
        drawComponent(
            slice = it,
            startAngle = localStartAngle,
            sweepAngle = localSweepAngle,
            level = level + 1,
            maxLevel = maxLevel,
            lineWidth = lineWidth,
            topLeft = topLeft,
            size = size,
        )
        localStartAngle += localSweepAngle
    }
}

@Composable
private fun SunbrustLegend(
    data: SunbrustChartData,
) {
    Box(modifier = Modifier.border(width = 1.dp, color = ChartsTheme.legendColor)) {
        LegendFlow(
            modifier = Modifier.padding(16.dp),
            data = data.slices.map { item ->
                LegendEntry(
                    item.label,
                    item.color,
                )
            },
        )
    }
}