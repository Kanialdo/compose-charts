package pl.krystiankaniowski.composecharts.axis

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import pl.krystiankaniowski.composecharts.internal.*

class XAxisDrawer(val axis: Axis, minValue: Float, maxValue: Float) {

    val _minValue = when (axis.values) {
        is Axis.Values.Auto -> minValue
        is Axis.Values.Fixed -> axis.values.values.min()
        is Axis.Values.Regions -> axis.values.values.minOf { it.first }
    }

    val _maxValue = when (axis.values) {
        is Axis.Values.Auto -> maxValue
        is Axis.Values.Fixed -> axis.values.values.max()
        is Axis.Values.Regions -> axis.values.values.maxOf { it.second }
    }

    fun measureHeight(textMeasurer: TextMeasurer): Float {
        val strokeWidth = axis.style.strokeWidth
        val padding = 8f
        val labelsHeight = textMeasurer.measure(" ", TextStyle(fontSize = axis.style.textSize)).size.height.dp.value
        return strokeWidth + padding + labelsHeight
    }

    fun calculateValues(): List<AxisLabel> {
        return listOf(_minValue, _maxValue).map { AxisLabel(axis.mapper(it), it) }
    }

    fun draw(drawScope: DrawScope, chartMeasurer: ChartMeasurer, values: List<AxisLabel>) {
        draw(
            drawScope = drawScope,
            textMeasurer = chartMeasurer.textMeasurer,
            chartScope = chartMeasurer.contentArea,
            xAxisScope = chartMeasurer.xAxisArea,
            xMapper = chartMeasurer.mapper,
            values = values,
        )
    }

    fun draw(drawScope: DrawScope, textMeasurer: TextMeasurer, chartScope: Rect, xAxisScope: Rect, xMapper: XMapper, values: List<AxisLabel>) {

        // draw axis

        drawScope.drawLine(
            color = axis.style.color,
            start = Offset(xAxisScope.left, xAxisScope.top),
            end = Offset(xAxisScope.right, xAxisScope.top),
        )

        values.forEachIndexed { index, value ->
            val x = xMapper.x(value.value)
            drawScope.drawLine(
                color = axis.style.color,
                start = Offset(x, xAxisScope.top),
                end = Offset(x, xAxisScope.top + 4f),
            )
        }

        // TODO() - skip unnecessary labels
        values.forEachIndexed { index, value ->
            val x = xMapper.x(value.value)
            if (values.size <= 10 || (index % (values.size / 10) == 0)) {
                // labels
                drawScope.drawLine(
                    color = axis.style.color,
                    start = Offset(x, xAxisScope.top),
                    end = Offset(x, xAxisScope.top + 4f),
                )
                drawScope.drawText(
                    textMeasurer = textMeasurer,
                    text = value.label,
                    x = x,
                    y = xAxisScope.top + 8f,
                    anchorX = TextAnchorX.Center,
                    anchorY = TextAnchorY.Bottom,
                    color = axis.style.color,
                    size = axis.style.textSize,
                )
                // gridlines
                if (axis.gridlinesStyle.isEnabled) {
                    axis.gridlinesStyle.let { style ->
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
            }
        }
    }
}
