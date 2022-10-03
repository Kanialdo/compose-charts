package pl.krystiankaniowski.composecharts.internal

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import pl.krystiankaniowski.composecharts.ChartsTheme

object YAxis {

    data class Value(
        val label: String,
        val value: Float,
        val helperLine: YAxisLine? = null,
    )

    data class YAxisLine(
        val width: Float = 1f,
        val color: Color = Color.Gray,
        val alpha: Float = 1.0f,
        val style: DrawStyle = Fill,
        val colorFilter: ColorFilter? = null,
        val blendMode: BlendMode = DrawScope.DefaultBlendMode,
    )

    interface Drawer {
        fun requiredWidth(drawScope: DrawScope, values: List<Value>): Float
        fun draw(drawScope: DrawScope, chartScope: Rect, yAxisScope: Rect, yMapper: YMapper, values: List<Value>)
    }

    class None : Drawer {
        override fun requiredWidth(drawScope: DrawScope, values: List<Value>) = 0f
        override fun draw(drawScope: DrawScope, chartScope: Rect, yAxisScope: Rect, yMapper: YMapper, values: List<Value>) = Unit
    }

    class Default(
        val textSize: Float = 20f,
        val color: Color = ChartsTheme.axisColor,
    ) : Drawer {

        override fun requiredWidth(drawScope: DrawScope, values: List<Value>) = values.maxOf { it.label }.let {
            drawScope.measureText(textSize, it)
        }

        override fun draw(drawScope: DrawScope, chartScope: Rect, yAxisScope: Rect, yMapper: YMapper, values: List<Value>) {
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
    }
}