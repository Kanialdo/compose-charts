package pl.krystiankaniowski.composecharts.bar

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import pl.krystiankaniowski.composecharts.ChartsTheme
import pl.krystiankaniowski.composecharts.internal.*

object BarChartXAxis {

    interface Drawer {
        fun requiredHeight(): Float
        fun draw(
            drawScope: DrawScope,
            chartScope: Rect,
            xAxisScope: Rect,
            xMapper: XMapper,
            scale: AxisScale,
        )
    }

    @Composable
    fun None(): Drawer = object : Drawer {
        override fun requiredHeight(): Float = 0f
        override fun draw(
            drawScope: DrawScope,
            chartScope: Rect,
            xAxisScope: Rect,
            xMapper: XMapper,
            scale: AxisScale,
        ) {
        }
    }

    @Composable
    fun Auto(
        label: (Float) -> String = { it.toString() },
        textSize: TextUnit = 20.sp,
        color: Color = ChartsTheme.axisColor,
    ): Drawer = object : Drawer {
        override fun requiredHeight(): Float = textSize.value * 1.5f
        override fun draw(
            drawScope: DrawScope,
            chartScope: Rect,
            xAxisScope: Rect,
            xMapper: XMapper,
            scale: AxisScale,
        ) {
            drawScope.drawLine(
                color = color,
                start = Offset(xAxisScope.left, xAxisScope.top),
                end = Offset(xAxisScope.right, xAxisScope.top)
            )

            val helperLines = scale.getHelperLines()

            helperLines.forEachIndexed { index, threshold ->
                val x = xMapper.x(threshold)

                if (helperLines.size <= 10 || (index % (helperLines.size / 10) == 0)) {
                    drawScope.drawText(
                        text = label(threshold),
                        x = x,
                        y = xAxisScope.top + requiredHeight(),
                        anchorX = TextAnchorX.Center,
                        anchorY = TextAnchorY.Top,
                        color = color,
                        size = textSize.value,
                    )
                }
            }
        }
    }
}