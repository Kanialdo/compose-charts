package pl.krystiankaniowski.composecharts.circular

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import pl.krystiankaniowski.composecharts.data.ChartColor
import pl.krystiankaniowski.composecharts.data.Series
import pl.krystiankaniowski.composecharts.internal.ChartChoreographer
import pl.krystiankaniowski.composecharts.legend.Legend
import pl.krystiankaniowski.composecharts.legend.LegendPosition

object DoughnutChart {

    data class Data(val slices: List<Slice>) {

        constructor(vararg slices: Slice) : this(slices.toList())
    }

    data class Slice(
        override val label: String,
        override val color: ChartColor.Solid,
        val value: Float,
    ) : Series
}

@Composable
fun DoughnutChart(
    modifier: Modifier = Modifier,
    data: DoughnutChart.Data,
    cutOut: Float = 0.5f,
    title: (@Composable () -> Unit)? = null,
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {

    val safeCutOut = cutOut.coerceIn(0.1f, 0.9f)
    val dataSum = remember(data) { data.slices.sumOf { it.value.toDouble() }.toFloat() }

    ChartChoreographer(
        modifier = modifier,
        title = title,
        legend = { Legend(data = data.slices) },
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
                    color = slice.color.value,
                )
                startAngle += sliceAngle
            }
        }
    }
}
