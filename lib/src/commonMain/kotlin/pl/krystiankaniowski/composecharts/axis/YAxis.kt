package pl.krystiankaniowski.composecharts.axis

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import pl.krystiankaniowski.composecharts.ChartsTheme
import pl.krystiankaniowski.composecharts.internal.*

object YAxis {

    data class Value(
        val label: String,
        val value: Float,
        val helperLine: YAxisLine? = YAxisLine(),
    )

    data class YAxisLine(
        val strokeWidth: Float = Stroke.HairlineWidth,
        val cap: StrokeCap = Stroke.DefaultCap,
        val color: Color = Color.Gray,
        val alpha: Float = 1.0f,
        val pathEffect: PathEffect? = PathEffect.dashPathEffect(floatArrayOf(10f, 20f)),
        val colorFilter: ColorFilter? = null,
        val blendMode: BlendMode = DrawScope.DefaultBlendMode,
    )

    interface Drawer {
        fun requiredWidth(drawScope: DrawScope, textMeasurer: TextMeasurer, values: List<Value>): Float
        fun draw(drawScope: DrawScope, textMeasurer: TextMeasurer, chartScope: Rect, yAxisScope: Rect, yMapper: YMapper, values: List<Value>)
    }

    class None : Drawer {
        override fun requiredWidth(drawScope: DrawScope, textMeasurer: TextMeasurer, values: List<Value>): Float = 0f
        override fun draw(drawScope: DrawScope, textMeasurer: TextMeasurer, chartScope: Rect, yAxisScope: Rect, yMapper: YMapper, values: List<Value>) = Unit
    }

    class Default(
        private val textSize: Float = 20f,
        private val color: Color = ChartsTheme.axisColor,
    ) : Drawer {

        override fun requiredWidth(drawScope: DrawScope, textMeasurer: TextMeasurer, values: List<Value>): Float = values.maxBy {
            it.label.length
        }.let {
            drawScope.measureText(textMeasurer, textSize, it.label)
        }

        override fun draw(drawScope: DrawScope, textMeasurer: TextMeasurer, chartScope: Rect, yAxisScope: Rect, yMapper: YMapper, values: List<Value>) {
            internalDraw(
                textMeasurer = textMeasurer,
                color = color,
                textSize = textSize,
                values = values,
                yMapper = yMapper,
                drawScope = drawScope,
                chartScope = chartScope,
                yAxisScope = yAxisScope,
            )
        }
    }

    class Fixed(
        private val values: List<Value>,
        private val textSize: Float = 20f,
        private val color: Color = ChartsTheme.axisColor,
    ) : Drawer {

        override fun requiredWidth(drawScope: DrawScope, textMeasurer: TextMeasurer, values: List<Value>): Float = this.values.maxBy {
            it.label.length
        }.let {
            drawScope.measureText(textMeasurer, textSize, it.label)
        }

        override fun draw(drawScope: DrawScope, textMeasurer: TextMeasurer, chartScope: Rect, yAxisScope: Rect, yMapper: YMapper, values: List<Value>) {
            internalDraw(
                textMeasurer = textMeasurer,
                color = color,
                textSize = textSize,
                values = this.values,
                yMapper = yMapper,
                drawScope = drawScope,
                chartScope = chartScope,
                yAxisScope = yAxisScope,
            )
        }
    }
}

private fun internalDraw(
    textMeasurer: TextMeasurer,
    color: Color,
    textSize: Float,
    values: List<YAxis.Value>,
    yMapper: YMapper,
    drawScope: DrawScope,
    chartScope: Rect,
    yAxisScope: Rect,
) {
    for (value in values) {

        val y = yMapper.y(value.value)

        value.helperLine?.let { style ->
            drawScope.drawLine(
                color = style.color,
                start = Offset(x = chartScope.left, y = y),
                end = Offset(x = chartScope.right, y = y),
                pathEffect = style.pathEffect,
                strokeWidth = style.strokeWidth,
                cap = style.cap,
                alpha = style.alpha,
                colorFilter = style.colorFilter,
                blendMode = style.blendMode,
            )
        }

        drawScope.drawText(
            textMeasurer = textMeasurer,
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