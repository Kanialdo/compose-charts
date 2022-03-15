package pl.krystiankaniowski.composecharts.bar

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import pl.krystiankaniowski.composecharts.internal.*

sealed class BarChartYAxis {

    internal abstract fun draw(
        drawScope: DrawScope,
        chartScope: Rect,
        yAxisScope: Rect,
        yMapper: YMapper,
        data: BarChartData
    )

    internal abstract fun requiredWidth(): Float

    object None : BarChartYAxis() {
        override fun requiredWidth() = 0f
        override fun draw(
            drawScope: DrawScope,
            chartScope: Rect,
            yAxisScope: Rect,
            yMapper: YMapper,
            data: BarChartData
        ) {
        }
    }

    data class Names(
        private val label: (Float) -> String = { it.toString() },
        private val textSize: TextUnit = 20.sp,
        private val color: Color = Color.LightGray,
    ) : BarChartYAxis() {

        override fun requiredWidth(): Float = 80f

        override fun draw(
            drawScope: DrawScope,
            chartScope: Rect,
            yAxisScope: Rect,
            yMapper: YMapper,
            data: BarChartData
        ) {

            val thresholds = calculateYHelperLines(data.minValue, data.maxValue)

            for (threshold in thresholds) {
                val y = yMapper.y(threshold)
                drawScope.drawLine(
                    color = color,
                    start = Offset(x = chartScope.left, y = y),
                    end = Offset(x = chartScope.right, y = y),
                )
                drawScope.drawText(
                    text = label(threshold),
                    x = requiredWidth() - 10f,
                    y = y,
                    anchorX = TextAnchorX.Right,
                    anchorY = TextAnchorY.Center,
                    color = color,
                    size = textSize.value
                )
            }

            drawScope.drawLine(
                color = color,
                start = Offset(yAxisScope.right, yAxisScope.top),
                end = Offset(yAxisScope.right, yAxisScope.bottom)
            )
        }
    }
}