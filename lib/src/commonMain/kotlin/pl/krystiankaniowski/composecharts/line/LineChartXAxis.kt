package pl.krystiankaniowski.composecharts.line

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import pl.krystiankaniowski.composecharts.internal.TextAnchorX
import pl.krystiankaniowski.composecharts.internal.XMapper
import pl.krystiankaniowski.composecharts.internal.drawText

sealed class LineChartXAxis {

    internal abstract fun draw(
        drawScope: DrawScope,
        chartScope: Rect,
        xAxisScope: Rect,
        xMapper: XMapper,
        data: LineChartData
    )

    internal abstract fun requiredHeight(): Float

    object None : LineChartXAxis() {
        override fun requiredHeight() = 0f
        override fun draw(
            drawScope: DrawScope,
            chartScope: Rect,
            xAxisScope: Rect,
            xMapper: XMapper,
            data: LineChartData
        ) {
        }
    }

    data class Linear(
        private val label: (Int) -> String = { (it + 1).toString() },
        private val textSize: TextUnit = 24.sp,
        private val color: Color = Color.Black
    ) : LineChartXAxis() {

        override fun requiredHeight(): Float = textSize.value * 1.5f

        override fun draw(
            drawScope: DrawScope,
            chartScope: Rect,
            xAxisScope: Rect,
            xMapper: XMapper,
            data: LineChartData
        ) {
            drawScope.drawLine(
                color = color,
                Offset(xAxisScope.left, xAxisScope.top),
                Offset(xAxisScope.right, xAxisScope.top)
            )
            for (i in 0 until data.size) {
                val x = xMapper.x(i + 0.5f)
                drawScope.drawLine(
                    color = color,
                    Offset(x, xAxisScope.top),
                    Offset(x, xAxisScope.top + 4f)
                )
                if (data.size <= 10 || (i % (data.size / 10) == 0)) {
                    drawScope.drawText(
                        text = label(i),
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