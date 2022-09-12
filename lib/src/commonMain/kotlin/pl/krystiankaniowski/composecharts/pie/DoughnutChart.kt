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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import pl.krystiankaniowski.composecharts.ChartsTheme
import pl.krystiankaniowski.composecharts.internal.ChartChoreographer
import pl.krystiankaniowski.composecharts.legend.LegendEntry
import pl.krystiankaniowski.composecharts.legend.LegendFlow
import pl.krystiankaniowski.composecharts.legend.LegendPosition

data class DoughnutChartData(val slices: List<Slice>) {

    constructor(vararg slices: Slice) : this(slices.toList())

    data class Slice(
        val label: String,
        val value: Float,
        val color: Color,
    )
}


@Composable
fun DoughnutChart(
    modifier: Modifier = Modifier,
    data: DoughnutChartData,
    cutOut: Float = 0.5f,
    title: (@Composable () -> Unit)? = null,
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {

    val safeCutOut = cutOut.coerceIn(0.1f, 0.9f)
    val dataSum = remember(data) { data.slices.sumOf { it.value.toDouble() }.toFloat() }

    ChartChoreographer(
        modifier = modifier,
        title = title,
        legend = { DoughnutLegend(data) },
        legendPosition = legendPosition,
    ) {
        Canvas(Modifier.fillMaxSize()) {

            val minSize = minOf(size.width, size.height)
            val virtualLocalSize = minSize * (safeCutOut + 1) / 2
            val topLeft = Offset((size.width - virtualLocalSize) / 2, (size.height - virtualLocalSize) / 2)
            val size = Size(virtualLocalSize, virtualLocalSize)
            val lineWidth = (minSize * (1 - safeCutOut)) / 2

            var startAngle = -90f

            data.slices.forEach { slice ->
                val sliceAngle = slice.value / dataSum * 360f
                drawArc(
                    topLeft = topLeft,
                    size = size,
                    startAngle = startAngle,
                    sweepAngle = sliceAngle,
                    useCenter = false,
                    style = Stroke(width = lineWidth, cap = StrokeCap.Butt),
                    color = slice.color,
                )
                startAngle += sliceAngle
            }
        }
    }
}

@Composable
private fun DoughnutLegend(
    data: DoughnutChartData,
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