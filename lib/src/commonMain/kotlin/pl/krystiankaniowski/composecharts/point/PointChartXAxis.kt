package pl.krystiankaniowski.composecharts.point

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import pl.krystiankaniowski.composecharts.ChartsTheme
import pl.krystiankaniowski.composecharts.internal.TextAnchorX
import pl.krystiankaniowski.composecharts.internal.XMapper
import pl.krystiankaniowski.composecharts.internal.drawText


object PointChartXAxis {

    interface Drawer {
        fun requiredHeight(): Float
        fun draw(
            drawScope: DrawScope,
            chartScope: Rect,
            xAxisScope: Rect,
            xMapper: XMapper,
            data: PointChartData
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
            data: PointChartData
        ) {
        }
    }

    data class Label(
        val label: String,
        val value: Float,
    )

    data class Range(
        val label: String,
        val from: Float,
        val to: Float,
    )

    abstract class AbstractDrawer(
        private val textSize: TextUnit = 24.sp,
        private val color: Color = ChartsTheme.axisColor
    ) : Drawer {

        override fun requiredHeight(): Float = textSize.value * 1.8f

        fun drawTag(drawScope: DrawScope, xAxisScope: Rect, x: Float) {
            drawScope.drawLine(
                color = color,
                Offset(x, xAxisScope.top),
                Offset(x, xAxisScope.top + 6f)
            )
        }

        fun drawArea(drawScope: DrawScope, xAxisScope: Rect, chartScope: Rect, x1: Float, x2: Float) {
            drawScope.drawRect(
                color = Color(0x11888888),
                topLeft = Offset(x1, chartScope.top),
                size = Size(x2 - x1, chartScope.height + xAxisScope.height),
            )
        }

        fun drawLabel(drawScope: DrawScope, xAxisScope: Rect, x: Float, label: String) {
            drawScope.drawText(
                text = label,
                x = x,
                y = xAxisScope.top + textSize.value + 6f,
                anchorX = TextAnchorX.Center,
                color = color,
                size = textSize.value
            )
        }
    }

    @Composable
    fun Auto(
        formatter: (Float) -> String = { it.toString() },
        textSize: TextUnit = 24.sp,
        color: Color = ChartsTheme.axisColor
    ) = object : AbstractDrawer(textSize) {

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
            for (i in 0..count) {
                val xValue = startPos + i * step
                val x = xMapper.x(xValue)
                drawTag(drawScope, xAxisScope, x)
                if (count <= 10 || (i % (count / 10) == 0)) {
                    drawLabel(drawScope, xAxisScope, x, formatter(xValue))
                }
            }
        }
    }

    @Composable
    fun Fixed(
        points: List<Label>,
        textSize: TextUnit = 24.sp,
        color: Color = ChartsTheme.axisColor
    ) = object : AbstractDrawer(textSize) {

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

            points.forEach { (label, value) ->
                val x = xMapper.x(value)
                drawTag(drawScope, xAxisScope, x)
                drawLabel(drawScope, xAxisScope, x, label)
            }
        }
    }

    @Composable
    fun FixedRanges(
        points: List<Range>,
        textSize: TextUnit = 24.sp,
        color: Color = ChartsTheme.axisColor
    ) = object : AbstractDrawer(textSize) {

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

            points.forEachIndexed { index, label ->
                val x1 = xMapper.x(label.from)
                val x2 = xMapper.x(label.to)
                if (index % 2 == 1) {
                    drawArea(drawScope, xAxisScope, chartScope, x1, x2)
                }
                drawTag(drawScope, xAxisScope, x1)
                drawTag(drawScope, xAxisScope, x2)
                drawLabel(drawScope, xAxisScope, (x1 + x2) / 2, label.label)
            }
        }
    }
}