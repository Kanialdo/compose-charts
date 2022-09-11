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
    ChartChoreographer(
        modifier = modifier,
        title = title,
        legend = { DoughnutLegend(data) },
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
                drawArc(
                    topLeft = topLeft,
                    size = size,
                    startAngle = currentAngle,
                    sweepAngle = sliceAngle,
                    useCenter = false,
                    style = Stroke(width = lineWidth, cap = StrokeCap.Butt),
                    color = slice.color,
                )
                currentAngle += sliceAngle
            }
        }
    }
}

//  .forEachIndexed { index, slice ->
//
//                        val path = Path()
//                        val r = localSize / 2
//                        val angle = 2 * PI * (slice.value / data.sum)
//                        path.moveTo(
//                            x = (r * sin(start) + width / 2).toFloat(),
//                            y = (r * cos(start) + height / 2).toFloat(),
//                        )
//                        path.addArcRad(
//                            oval = Rect(
//                                center = Offset(width / 2, height / 2),
//                                radius = localSize / 2
//                            ),
//                            startAngleRadians = start.toFloat(),
//                            sweepAngleRadians = angle.toFloat(),
//                        )
//                        start += angle.toFloat()
//
//                        path.lineTo(
//                            x = (r * cutOut * sin(start) + width / 2).toFloat(),
//                            y = (r * cutOut * cos(start) + height / 2).toFloat(),
//                        )
//
//                        path.addArcRad(
//                            oval = Rect(
//                                center = Offset(width / 2, height / 2),
//                                radius = (localSize / 2) * cutOut,
//                            ),
//                            startAngleRadians = start.toFloat(),
//                            sweepAngleRadians = -angle.toFloat(),
//                        )
//
//                        path.close()
//                        drawPath(path = path, color = slice.color, style = Fill)
//                    }

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