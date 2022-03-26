package pl.krystiankaniowski.composecharts.point

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import pl.krystiankaniowski.composecharts.internal.TextAnchorX
import pl.krystiankaniowski.composecharts.internal.XMapper
import pl.krystiankaniowski.composecharts.internal.drawText

data class PointChartXAxis(
    private val labels: Labels = Labels.Auto(),
    private val textSize: TextUnit = 24.sp,
    private val color: Color = Color.Black
) {

    fun requiredHeight(): Float = textSize.value * 1.5f

    fun draw(
        drawScope: DrawScope,
        chartScope: Rect,
        xAxisScope: Rect,
        xMapper: XMapper,
        data: PointChartData
    ) {

        fun drawTag(x: Float) {
            drawScope.drawLine(
                color = color,
                Offset(x, xAxisScope.top),
                Offset(x, xAxisScope.top + 6f)
            )
        }

        fun drawArea(x1: Float, x2: Float) {
            drawScope.drawRect(
                color = Color(0xFFEEEEEE),
                topLeft = Offset(x1, chartScope.top),
                size = Size(x2 - x1, chartScope.height + xAxisScope.height),
                blendMode = BlendMode.ColorBurn
            )
        }

        fun drawLabel(x: Float, label: String) {
            drawScope.drawText(
                text = label,
                x = x,
                y = xAxisScope.top + requiredHeight(),
                anchorX = TextAnchorX.Center,
                color = color,
                size = textSize.value
            )
        }

        drawScope.drawLine(
            color = color,
            Offset(xAxisScope.left, xAxisScope.top),
            Offset(xAxisScope.right, xAxisScope.top)
        )

        when (labels) {
            is Labels.Auto -> {
                val startPos = data.minX
                val count = 5
                val step = (data.maxX - data.minX) / count
                for (i in 0..count) {
                    val xValue = startPos + i * step
                    val x = xMapper.x(xValue)
                    drawTag(x)
                    if (count <= 10 || (i % (count / 10) == 0)) {
                        drawLabel(x, labels.formatter(xValue))
                    }
                }
            }
            is Labels.Fixed -> {
                labels.labels.forEach { label ->
                    val x = xMapper.x(label.value)
                    drawTag(x)
                    drawLabel(x, label.label)
                }
            }
            is Labels.FixedRanges -> {
                labels.labels.forEachIndexed { index, label ->
                    val x1 = xMapper.x(label.from)
                    val x2 = xMapper.x(label.to)
                    if (index % 2 == 1) {
                        drawArea(x1, x2)
                    }
                    drawTag(x1)
                    drawTag(x2)
                    drawLabel((x1 + x2) / 2, label.label)
                }
            }
        }
    }

    sealed class Labels {

        data class Auto(
            val formatter: (Float) -> String = { it.toString() }
        ) : Labels()

        data class Fixed(
            val labels: List<Point>
        ) : Labels() {
            data class Point(
                val label: String,
                val value: Float,
            )
        }

        data class FixedRanges(
            val labels: List<Range>
        ) : Labels() {
            data class Range(
                val label: String,
                val from: Float,
                val to: Float,
            )
        }
    }
}