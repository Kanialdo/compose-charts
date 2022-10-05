package pl.krystiankaniowski.composecharts.axis

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import pl.krystiankaniowski.composecharts.ChartsTheme
import pl.krystiankaniowski.composecharts.internal.TextAnchorX
import pl.krystiankaniowski.composecharts.internal.TextAnchorY
import pl.krystiankaniowski.composecharts.internal.XMapper
import pl.krystiankaniowski.composecharts.internal.drawText

object XAxis {

    data class Value(
        val label: String,
        val value: Float,
        val helperLine: XAxisLine? = null,
    )

    data class XAxisLine(
        val strokeWidth: Float = Stroke.HairlineWidth,
        val cap: StrokeCap = Stroke.DefaultCap,
        val color: Color = Color.Gray,
        val alpha: Float = 1.0f,
        val pathEffect: PathEffect? = PathEffect.dashPathEffect(floatArrayOf(10f, 20f)),
        val colorFilter: ColorFilter? = null,
        val blendMode: BlendMode = DrawScope.DefaultBlendMode,
    )

    interface Drawer {
        fun requiredHeight(drawScope: DrawScope, values: List<Value>): Float
        fun draw(drawScope: DrawScope, chartScope: Rect, xAxisScope: Rect, xMapper: XMapper, values: List<Value>)
    }

    class None : Drawer {
        override fun requiredHeight(drawScope: DrawScope, values: List<Value>) = 0f
        override fun draw(drawScope: DrawScope, chartScope: Rect, xAxisScope: Rect, xMapper: XMapper, values: List<Value>) = Unit
    }

    class Default(
        private val textSize: Float = 20f,
        private val color: Color = ChartsTheme.axisColor,
    ) : Drawer {

        override fun requiredHeight(drawScope: DrawScope, values: List<Value>) = 80f

        override fun draw(drawScope: DrawScope, chartScope: Rect, xAxisScope: Rect, xMapper: XMapper, values: List<Value>) {
            internalDraw(
                color = color,
                textSize = textSize,
                values = values,
                xMapper = xMapper,
                drawScope = drawScope,
                chartScope = chartScope,
                xAxisScope = xAxisScope,
            )
        }
    }

    class Fixed(
        private val values: List<Value>,
        private val textSize: Float = 20f,
        private val color: Color = ChartsTheme.axisColor,
    ) : Drawer {

        override fun requiredHeight(drawScope: DrawScope, values: List<Value>) = 80f

        override fun draw(drawScope: DrawScope, chartScope: Rect, xAxisScope: Rect, xMapper: XMapper, values: List<Value>) {
            internalDraw(
                color = color,
                textSize = textSize,
                values = this.values,
                xMapper = xMapper,
                drawScope = drawScope,
                chartScope = chartScope,
                xAxisScope = xAxisScope,
            )
        }
    }
}

private fun internalDraw(
    color: Color,
    textSize: Float,
    values: List<XAxis.Value>,
    xMapper: XMapper,
    drawScope: DrawScope,
    chartScope: Rect,
    xAxisScope: Rect,
) {
    drawScope.drawLine(
        color = color,
        start = Offset(xAxisScope.left, xAxisScope.top),
        end = Offset(xAxisScope.right, xAxisScope.top),
    )
    values.forEachIndexed { index, value ->
        val x = xMapper.x(value.value)
        drawScope.drawLine(
            color = color,
            start = Offset(x, xAxisScope.top),
            end = Offset(x, xAxisScope.top + 4f),
        )
        if (values.size <= 10 || (index % (values.size / 10) == 0)) {

            value.helperLine?.let { style ->
                drawScope.drawLine(
                    color = style.color,
                    start = Offset(x = x, y = chartScope.top),
                    end = Offset(x = x, y = chartScope.bottom),
                    pathEffect = style.pathEffect,
                    strokeWidth = style.strokeWidth,
                    cap = style.cap,
                    alpha = style.alpha,
                    colorFilter = style.colorFilter,
                    blendMode = style.blendMode,
                )
            }
        }

        drawScope.drawText(
            text = value.label,
            x = x,
            y = xAxisScope.top + 8f,
            anchorX = TextAnchorX.Center,
            anchorY = TextAnchorY.Bottom,
            color = color,
            size = textSize,
        )
    }
}