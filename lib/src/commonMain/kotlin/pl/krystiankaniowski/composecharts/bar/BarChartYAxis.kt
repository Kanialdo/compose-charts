package pl.krystiankaniowski.composecharts.bar

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
import pl.krystiankaniowski.composecharts.internal.YMapper
import pl.krystiankaniowski.composecharts.internal.drawText

object BarChartYAxis {

    interface Drawer {
        fun requiredWidth(): Float
        fun draw(
            drawScope: DrawScope,
            chartScope: Rect,
            yAxisScope: Rect,
            yMapper: YMapper,
            data: BarChartData,
        )
    }

    @Composable
    fun None(): Drawer = object : Drawer {
        override fun requiredWidth() = 0f
        override fun draw(
            drawScope: DrawScope,
            chartScope: Rect,
            yAxisScope: Rect,
            yMapper: YMapper,
            data: BarChartData,
        ) = Unit
    }

    @Composable
    fun Auto(
        textSize: TextUnit = 24.sp,
        color: Color = ChartsTheme.axisColor,
    ): Drawer = object : Drawer {

        override fun requiredWidth() = 80f

        override fun draw(
            drawScope: DrawScope,
            chartScope: Rect,
            yAxisScope: Rect,
            yMapper: YMapper,
            data: BarChartData,
        ) {
            drawScope.drawLine(
                color = color,
                start = Offset(yAxisScope.right, yAxisScope.top),
                end = Offset(yAxisScope.right, yAxisScope.bottom),
            )

            data.labels.forEachIndexed { index, label ->
                val y = yMapper.y(index)
                if (data.labels.size <= 10 || (index % (data.labels.size / 10) == 0)) {
                    drawScope.drawText(
                        text = label + " ",
                        x = yAxisScope.right,
                        y = yAxisScope.top + y,
                        anchorX = TextAnchorX.Right,
                        anchorY = TextAnchorY.Center,
                        color = color,
                        size = textSize.value,
                    )
                }
            }

//            for (threshold in thresholds) {
//                val y = yMapper.y(threshold)
//                drawScope.drawLine(
//                    color = color,
//                    start = Offset(x = chartScope.left, y = y),
//                    end = Offset(x = chartScope.right, y = y),
//                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 20f)),
//                )
//                drawScope.drawText(
//                    text = label(threshold),
//                    x = requiredWidth() - 10f,
//                    y = y,
//                    anchorX = TextAnchorX.Right,
//                    anchorY = TextAnchorY.Center,
//                    color = color,
//                    size = textSize.value,
//                )
//            }
        }
    }
}