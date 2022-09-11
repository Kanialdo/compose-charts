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

@Composable
fun SunbrustChart(
    modifier: Modifier = Modifier,
    data: SunbrustChartData,
    cutOut: Float = 0.5f,
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

        Canvas(Modifier.fillMaxSize()) {

            val minSize = minOf(size.width, size.height)
            val virtualLocalSize = minSize * (cutOut + 1) / 2
            val topLeft = Offset((size.width - virtualLocalSize) / 2, (size.height - virtualLocalSize) / 2)
            val size = Size(virtualLocalSize, virtualLocalSize)
            val lineWidth = (minSize * (1 - cutOut)) / 2

            var currentAngle = -90f

            data.slices.forEach { slice ->
                val sliceAngle = slice.value / dataSum * 360f
                drawComponent(
                    slice = slice,
                    startAngle = currentAngle,
                    sweepAngle = sliceAngle,
                    level = 1,
                    maxLevel = 2,
                    lineWidth = 50f,
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
    val m = (size * ((maxLevel - level) / maxLevel.toFloat())).width
    drawArc(
        topLeft = topLeft + Offset(m / 2, m / 2),
        size = size * (level / maxLevel.toFloat()),
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