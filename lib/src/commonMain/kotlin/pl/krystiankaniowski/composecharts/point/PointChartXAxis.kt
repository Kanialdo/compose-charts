package pl.krystiankaniowski.composecharts.point

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import pl.krystiankaniowski.composecharts.internal.TextAnchorX
import pl.krystiankaniowski.composecharts.internal.XMapper
import pl.krystiankaniowski.composecharts.internal.drawText

sealed class PointChartXAxis {

    internal abstract fun draw(
        drawScope: DrawScope,
        chartScope: Rect,
        xAxisScope: Rect,
        xMapper: XMapper,
        data: PointChartData
    )

    internal abstract fun requiredHeight(): Float

    object None : PointChartXAxis() {
        override fun requiredHeight() = 0f
        override fun draw(
            drawScope: DrawScope,
            chartScope: Rect,
            xAxisScope: Rect,
            xMapper: XMapper,
            data: PointChartData
        ) {
        }
    }

    data class Linear(
        private val label: (Float) -> String = { it.toString() },
        private val textSize: TextUnit = 24.sp,
        private val color: Color = Color.Black
    ) : PointChartXAxis() {

        override fun requiredHeight(): Float = textSize.value * 1.5f

        override fun draw(
            drawScope: DrawScope,
            chartScope: Rect,
            xAxisScope: Rect,
            xMapper: XMapper,
            data: PointChartData
        ) {
            drawScope.drawLine(
                color = color,
                Offset(xAxisScope.left, xAxisScope.top),
                Offset(xAxisScope.right, xAxisScope.top)
            )

            val startPos = data.minX
            val count = 5
            val step = (data.maxX - data.minX) / count
            for (i in 0 .. count) {
                val xValue = startPos + i * step
                val x = xMapper.x(xValue)
                drawScope.drawLine(
                    color = color,
                    Offset(x, xAxisScope.top),
                    Offset(x, xAxisScope.top + 4f)
                )
                if (count <= 10 || (i % (count / 10) == 0)) {
                    drawScope.drawText(
                        text = label(xValue),
                        x = x,
                        y = xAxisScope.top + requiredHeight(),
                        anchorX = TextAnchorX.Center,
                        color = color,
                        size = textSize.value
                    )
                }
            }
        }
    }
}