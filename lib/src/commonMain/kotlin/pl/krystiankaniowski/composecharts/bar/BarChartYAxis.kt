package pl.krystiankaniowski.composecharts.bar

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import pl.krystiankaniowski.composecharts.ChartsTheme
import pl.krystiankaniowski.composecharts.internal.*

object BarChartYAxis {

    interface Drawer {
        fun requiredWidth(): Float
        fun draw(
            drawScope: DrawScope,
            chartScope: Rect,
            yAxisScope: Rect,
            yMapper: YMapper,
            min: Float,
            max: Float,
        )
    }

    @Composable
    fun None(): Drawer = object : Drawer {
        override fun requiredWidth() = 0f
        override fun draw(
            drawScope: DrawScope,
            chartScope: Rect,
            yAxisScope: Rect,
            yMapper: YMapper,
            min: Float,
            max: Float,
        ) {
        }
    }

    @Composable
    fun Auto(
        label: (Float) -> String = { it.toString() },
        textSize: TextUnit = 20.sp,
        color: Color = ChartsTheme.axisColor,
    ): Drawer = object : Drawer {
        override fun requiredWidth() = 80f
        override fun draw(
            drawScope: DrawScope,
            chartScope: Rect,
            yAxisScope: Rect,
            yMapper: YMapper,
            min: Float,
            max: Float,
        ) {
            val thresholds = calculateHelperValues(min, max, desiredCount = 6, withBottomOffset = false, withTopOffset = false)

            for (threshold in thresholds) {
                val y = yMapper.y(threshold)
                drawScope.drawLine(
                    color = color,
                    start = Offset(x = chartScope.left, y = y),
                    end = Offset(x = chartScope.right, y = y),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 20f)),
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