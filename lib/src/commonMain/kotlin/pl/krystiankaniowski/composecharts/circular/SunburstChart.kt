package pl.krystiankaniowski.composecharts.circular

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

object SunburstChart {

    data class Data(val slices: List<Slice>) {
        constructor(vararg slices: Slice) : this(slices.toList())
    }

    data class Slice(
        val label: String,
        val color: Color,
        val value: Float,
        val subSlices: List<Slice> = emptyList(),
    )
}

@Composable
fun SunburstChart(
    modifier: Modifier = Modifier,
    data: SunburstChart.Data,
    title: (@Composable () -> Unit)? = null,
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {

    val dataSum = remember(data) { data.slices.sumOf { it.value.toDouble() }.toFloat() }
    val maxLevel = remember(data) { data.deep() }

    ChartChoreographer(
        modifier = modifier,
        title = title,
        legend = { SunburstLegend(data) },
        legendPosition = legendPosition,
    ) {
        Canvas(Modifier.fillMaxSize()) {

            val minSize = minOf(size.width, size.height)
            val areaTopLeft = Offset((size.width - minSize) / 2, (size.height - minSize) / 2)
            val areaSize = Size(minSize, minSize)
            val lineWidth = minSize / (maxLevel + 1) / 2

            var startAngle = -90f

            data.slices.forEach { slice ->
                val sliceAngle = slice.value / dataSum * 360f
                drawComponent(
                    slice = slice,
                    startAngle = startAngle,
                    sweepAngle = sliceAngle,
                    level = 1,
                    maxLevel = maxLevel,
                    lineWidth = lineWidth,
                    areaTopLeft = areaTopLeft,
                    areaSize = areaSize,
                )
                startAngle += sliceAngle
            }
        }
    }
}

private fun SunburstChart.Data.deep(): Int = slices.maxOf { it.deep(1) }
private fun SunburstChart.Slice.deep(current: Int): Int = subSlices.maxOfOrNull { it.deep(current = current + 1) } ?: current

private fun DrawScope.drawComponent(
    slice: SunburstChart.Slice,
    startAngle: Float,
    sweepAngle: Float,
    level: Int,
    maxLevel: Int,
    lineWidth: Float,
    areaTopLeft: Offset,
    areaSize: Size,
) {
    val padding = lineWidth * (maxLevel - level) + lineWidth / 2
    drawArc(
        topLeft = areaTopLeft + Offset(padding, padding),
        size = Size(areaSize.width - 2 * padding, areaSize.width - 2 * padding),
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
            areaTopLeft = areaTopLeft,
            areaSize = areaSize,
        )
        localStartAngle += localSweepAngle
    }
}

@Composable
private fun SunburstLegend(
    data: SunburstChart.Data,
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