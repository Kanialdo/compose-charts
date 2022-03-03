package pl.krystiankaniowski.composecharts.line

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import pl.krystiankaniowski.composecharts.internal.YMapper
import pl.krystiankaniowski.composecharts.internal.drawText

sealed class LineChartYAxis {

    internal abstract fun draw(
        drawScope: DrawScope,
        chartScope: Rect,
        yAxisScope: Rect,
        yMapper: YMapper,
        data: LineChartData
    )

    internal abstract fun requiredWidth(): Float

    object None : LineChartYAxis() {
        override fun requiredWidth() = 0f
        override fun draw(
            drawScope: DrawScope,
            chartScope: Rect,
            yAxisScope: Rect,
            yMapper: YMapper,
            data: LineChartData
        ) {
        }
    }

    data class Linear(
        private val label: (Float) -> String = { it.toString() },
        private val textSize: TextUnit = 24.sp,
        private val color: Color = Color.Black,
    ) : LineChartYAxis() {

        override fun requiredWidth(): Float = 80f

        override fun draw(
            drawScope: DrawScope,
            chartScope: Rect,
            yAxisScope: Rect,
            yMapper: YMapper,
            data: LineChartData
        ) {
            drawScope.drawLine(
                color = color,
                Offset(yAxisScope.right, yAxisScope.top),
                Offset(yAxisScope.right, yAxisScope.bottom)
            )

            for (i in 0..10 step 2) {
                drawScope.drawText(
                    text = label(i.toFloat()),
                    x = 0f,
                    y = yMapper.y(i),
                    color = color,
                    size = textSize.value
                )
            }
        }
    }
}