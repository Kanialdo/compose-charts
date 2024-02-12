package pl.krystiankaniowski.composecharts.circular

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import pl.krystiankaniowski.composecharts.data.ChartColor
import pl.krystiankaniowski.composecharts.data.Series
import pl.krystiankaniowski.composecharts.internal.ChartChoreographer
import pl.krystiankaniowski.composecharts.legend.Legend
import pl.krystiankaniowski.composecharts.legend.LegendPosition

object PieChart {

    data class Data(val slices: List<Slice>) {

        constructor(vararg slices: Slice) : this(slices.toList())

        internal val sum = slices.sumOf { it.value.toDouble() }.toFloat()

        internal val size: Int get() = slices.size
    }

    data class Slice(
        override val label: String,
        override val color: ChartColor.Solid,
        val value: Float,
    ) : Series
}

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    data: PieChart.Data,
    title: (@Composable () -> Unit)? = null,
    legendPosition: LegendPosition = LegendPosition.Bottom,
) {
    ChartChoreographer(
        modifier = modifier,
        title = title,
        legend = { Legend(data = data.slices) },
        legendPosition = legendPosition,
    ) {
        Canvas(Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            val localSize = minOf(width, height)

            drawIntoCanvas { canvas ->
                var start = -90f
                data.slices.forEach { slice ->
                    val end = (slice.value / data.sum) * 360f
                    canvas.drawArc(
                        rect = Rect(
                            center = Offset(width / 2, height / 2),
                            radius = localSize / 2,
                        ),
                        startAngle = start,
                        sweepAngle = end,
                        useCenter = true,
                        paint = Paint().apply { color = slice.color.value },
                    )
                    start += end
                }
            }
        }
    }
}
