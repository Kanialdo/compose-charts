package pl.krystiankaniowski.composecharts.line

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import pl.krystiankaniowski.composecharts.ChartsTheme
import pl.krystiankaniowski.composecharts.internal.TextAnchorX
import pl.krystiankaniowski.composecharts.internal.XMapper
import pl.krystiankaniowski.composecharts.internal.drawText

object LineChartXAxis {

    interface Drawer {
        fun requiredHeight(): Float
        fun draw(
            drawScope: DrawScope,
            chartScope: Rect,
            xAxisScope: Rect,
            xMapper: XMapper,
            data: LineChartData
        )
    }

    @Composable
    fun None(): Drawer = object : Drawer {
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

    @Composable
    fun Auto(
        label: (Int) -> String = { (it + 1).toString() },
        textSize: TextUnit = 24.sp,
        color: Color = ChartsTheme.axisColor
    ): Drawer = object : Drawer {

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