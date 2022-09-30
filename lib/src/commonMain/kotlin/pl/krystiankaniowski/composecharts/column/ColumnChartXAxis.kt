package pl.krystiankaniowski.composecharts.column

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import pl.krystiankaniowski.composecharts.ChartsTheme
import pl.krystiankaniowski.composecharts.internal.TextAnchorX
import pl.krystiankaniowski.composecharts.internal.TextAnchorY
import pl.krystiankaniowski.composecharts.internal.XMapper
import pl.krystiankaniowski.composecharts.internal.drawText

object ColumnChartXAxis {

    interface Drawer {
        fun requiredHeight(): Float
        fun draw(
            drawScope: DrawScope,
            chartScope: Rect,
            xAxisScope: Rect,
            xMapper: XMapper,
            data: ColumnChart.Data,
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
            data: ColumnChart.Data,
        ) = Unit
    }

    @Composable
    fun Auto(
        textSize: TextUnit = 24.sp,
        color: Color = ChartsTheme.axisColor,
    ): Drawer = object : Drawer {

        override fun requiredHeight(): Float = textSize.value * 1.5f

        override fun draw(
            drawScope: DrawScope,
            chartScope: Rect,
            xAxisScope: Rect,
            xMapper: XMapper,
            data: ColumnChart.Data,
        ) {
            drawScope.drawLine(
                color = color,
                start = Offset(xAxisScope.left, xAxisScope.top),
                end = Offset(xAxisScope.right, xAxisScope.top),
            )

            data.labels.forEachIndexed { index, label ->
                val x = xMapper.x(index)
                if (data.labels.size <= 10 || (index % (data.labels.size / 10) == 0)) {
                    drawScope.drawText(
                        text = label,
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