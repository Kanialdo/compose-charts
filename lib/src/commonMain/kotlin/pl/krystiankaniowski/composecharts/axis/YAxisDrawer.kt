package pl.krystiankaniowski.composecharts.axis

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import pl.krystiankaniowski.composecharts.internal.*

class YAxisDrawer(val axis: Axis, dataMinValue: Float, dataMxValue: Float) {

    private val minValue = when (axis.values) {
        is Axis.Values.Auto -> dataMinValue
        is Axis.Values.Fixed -> axis.values.values.min()
    }

    private val maxValue = when (axis.values) {
        is Axis.Values.Auto -> dataMxValue
        is Axis.Values.Fixed -> axis.values.values.max()
    }

    fun measureWidth(textMeasurer: TextMeasurer): Float {
        val strokeWidth = axis.style.strokeWidth
        val padding = 8f
        val labelsWidth = textMeasurer.measure("      ", TextStyle(fontSize = axis.style.textSize)).size.width.dp.value
        return strokeWidth + padding + labelsWidth
    }

    fun calculateValues(): List<AxisLabel> = when (axis.values) {
        is Axis.Values.Auto -> {
            AxisScale.create(min = minValue, max = maxValue)
                .getHelperLines()
                .map { AxisLabel(label = axis.mapper(it), value = it) }
        }
        is Axis.Values.Fixed -> {
            axis.values.values.map { AxisLabel(label = axis.mapper(it), value = it) }
        }
    }

    fun draw(drawScope: DrawScope, chartMeasurer: ChartMeasurer, values: List<AxisLabel>) {
        draw(
            drawScope = drawScope,
            textMeasurer = chartMeasurer.textMeasurer,
            chartScope = chartMeasurer.contentArea,
            yAxisScope = chartMeasurer.yAxisArea,
            yMapper = chartMeasurer.mapper,
            values = values,
        )
    }

    fun draw(drawScope: DrawScope, textMeasurer: TextMeasurer, chartScope: Rect, yAxisScope: Rect, yMapper: YMapper, values: List<AxisLabel>) {

        // draw axis

        for (value in values) {

            val y = yMapper.y(value.value)

            if (axis.gridlinesStyle.isEnabled) {
                axis.gridlinesStyle.let { style ->
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
            }

            drawScope.drawText(
                textMeasurer = textMeasurer,
                text = value.label,
                x = yAxisScope.width - 10f,
                y = y,
                anchorX = TextAnchorX.Right,
                anchorY = TextAnchorY.Center,
                color = axis.style.color,
                size = axis.style.textSize,
            )
        }

        drawScope.drawLine(
            color = axis.style.color,
            start = Offset(yAxisScope.right, yAxisScope.top),
            end = Offset(yAxisScope.right, yAxisScope.bottom),
        )
    }
}
