package pl.krystiankaniowski.composecharts.internal

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import pl.krystiankaniowski.composecharts.ChartsTheme

object YAxis {

    data class Value(
        val label: String,
        val value: Float,
    )

    interface Drawer {
        fun requiredWidth(drawScope: DrawScope, values: List<Value>): Float
        fun draw(drawScope: DrawScope, chartScope: Rect, yAxisScope: Rect, yMapper: YMapper, values: List<Value>)
    }

    class None : Drawer {
        override fun requiredWidth(drawScope: DrawScope, values: List<Value>) = 0f
        override fun draw(drawScope: DrawScope, chartScope: Rect, yAxisScope: Rect, yMapper: YMapper, values: List<Value>) = Unit
    }

    class Default(val textSize: Float = 20f) : Drawer {

        override fun requiredWidth(drawScope: DrawScope, values: List<Value>) = values.maxOf { it.label }.let {
            drawScope.measureText(textSize, it)
        }

        override fun draw(drawScope: DrawScope, chartScope: Rect, yAxisScope: Rect, yMapper: YMapper, values: List<Value>) = YAxis(
            drawScope = drawScope, chartScope = chartScope, yAxisScope = yAxisScope, yMapper = yMapper, values = values, textSize = textSize,
        )
    }
}

private fun YAxis(
    drawScope: DrawScope,
    chartScope: Rect,
    yAxisScope: Rect,
    yMapper: YMapper,
    values: List<YAxis.Value>,
    textSize: Float,
    color: Color = ChartsTheme.axisColor,
) {

    for (value in values) {

        val y = yMapper.y(value.value)

        drawScope.drawLine(
            color = color,
            start = Offset(x = chartScope.left, y = y),
            end = Offset(x = chartScope.right, y = y),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 20f)),
        )

        drawScope.drawText(
            text = value.label,
            x = yAxisScope.width - 10f,
            y = y,
            anchorX = TextAnchorX.Right,
            anchorY = TextAnchorY.Center,
            color = color,
            size = textSize,
        )
    }

    drawScope.drawLine(
        color = color,
        start = Offset(yAxisScope.right, yAxisScope.top),
        end = Offset(yAxisScope.right, yAxisScope.bottom),
    )
}