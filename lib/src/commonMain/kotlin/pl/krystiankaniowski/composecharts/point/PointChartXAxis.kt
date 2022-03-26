package pl.krystiankaniowski.composecharts.point

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import pl.krystiankaniowski.composecharts.internal.TextAnchorX
import pl.krystiankaniowski.composecharts.internal.XMapper
import pl.krystiankaniowski.composecharts.internal.drawText

sealed interface PointChartXAxis {

    fun draw(
        drawScope: DrawScope,
        chartScope: Rect,
        xAxisScope: Rect,
        xMapper: XMapper,
        data: PointChartData
    )

    fun requiredHeight(): Float

    object None : PointChartXAxis {
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

    sealed class Label {

        data class Point(
            val name: String,
            val value: Float,
        ) : Label()

        data class Range(
            val name: String,
            val from: Float,
            val to: Float,
        ) : Label()
    }

    sealed class Labels {

        data class Auto(
            val formatter: (Float) -> String = { it.toString() }
        ) : Labels()

        data class Custom(
            val labels: List<Label>
        ) : Labels()
    }

    data class Linear(
        private val labels: Labels = Labels.Auto(),
        private val textSize: TextUnit = 24.sp,
        private val color: Color = Color.Black
    ) : PointChartXAxis {

        override fun requiredHeight(): Float = textSize.value * 1.5f

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

            when (labels) {
                is Labels.Auto -> {
                    val startPos = data.minX
                    val count = 5
                    val step = (data.maxX - data.minX) / count
                    for (i in 0..count) {
                        val xValue = startPos + i * step
                        val x = xMapper.x(xValue)
                        drawScope.drawLine(
                            color = color,
                            Offset(x, xAxisScope.top),
                            Offset(x, xAxisScope.top + 4f)
                        )
                        if (count <= 10 || (i % (count / 10) == 0)) {
                            drawScope.drawText(
                                text = labels.formatter(xValue),
                                x = x,
                                y = xAxisScope.top + requiredHeight(),
                                anchorX = TextAnchorX.Center,
                                color = color,
                                size = textSize.value
                            )
                        }
                    }
                }
                is Labels.Custom -> {
                    labels.labels.forEach { label ->
                        when (label) {
                            is Label.Point -> {
                                val x = xMapper.x(label.value)
                                drawScope.drawLine(
                                    color = color,
                                    Offset(x, xAxisScope.top),
                                    Offset(x, xAxisScope.top + 4f)
                                )
                                drawScope.drawText(
                                    text = label.name,
                                    x = x,
                                    y = xAxisScope.top + requiredHeight(),
                                    anchorX = TextAnchorX.Center,
                                    color = color,
                                    size = textSize.value
                                )
                            }
                            is Label.Range -> {
                                val x1 = xMapper.x(label.from)
                                val x2 = xMapper.x(label.to)
                                drawScope.drawLine(
                                    color = color,
                                    Offset(x1, xAxisScope.top),
                                    Offset(x1, xAxisScope.top + 4f)
                                )
                                drawScope.drawLine(
                                    color = color,
                                    Offset(x2, xAxisScope.top),
                                    Offset(x2, xAxisScope.top + 4f)
                                )
                                drawScope.drawText(
                                    text = label.name,
                                    x = (x1 + x2) / 2,
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
        }
    }
}