package pl.krystiankaniowski.composecharts.point

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import pl.krystiankaniowski.composecharts.ChartsTheme
import pl.krystiankaniowski.composecharts.internal.*

object PointChartYAxis {

    interface Drawer {
        fun requiredWidth(): Float
        fun draw(
            drawScope: DrawScope,
            chartScope: Rect,
            yAxisScope: Rect,
            yMapper: YMapper,
            scale: Scale,
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
            scale: Scale,
        ) {
        }
    }

    @Composable
    fun Auto(
        label: (Float) -> String = { it.toString() },
        textSize: TextUnit = 20.sp,
        color: Color = ChartsTheme.axisColor,
    ): Drawer = object : DrawerImpl(
        textSize,
        color,
    ) {
        override fun requiredWidth() = 80f
        override fun draw(
            drawScope: DrawScope,
            chartScope: Rect,
            yAxisScope: Rect,
            yMapper: YMapper,
            scale: Scale,
        ) {
            scale.getHelperLines().forEach {
                drawHelperLine(drawScope = drawScope, chartScope = chartScope, yMapper = yMapper, value = it, label = label(it))
            }
            drawYAxis(drawScope = drawScope, yAxisScope = yAxisScope)
        }
    }

    @Composable
    fun Fixed(
        labels: List<Pair<Float, String>>,
        textSize: TextUnit = 20.sp,
        color: Color = ChartsTheme.axisColor,
    ): Drawer = object : DrawerImpl(
        textSize,
        color,
    ) {
        override fun requiredWidth() = 80f
        override fun draw(
            drawScope: DrawScope,
            chartScope: Rect,
            yAxisScope: Rect,
            yMapper: YMapper,
            scale: Scale,
        ) {
            labels.forEach {
                drawHelperLine(drawScope = drawScope, chartScope = chartScope, yMapper = yMapper, value = it.first, label = it.second)
            }
            drawYAxis(drawScope = drawScope, yAxisScope = yAxisScope)
        }
    }

    internal abstract class DrawerImpl(
        private val textSize: TextUnit = 20.sp,
        private val color: Color = ChartsTheme.axisColor,
    ) : Drawer {
        fun drawYAxis(
            drawScope: DrawScope,
            yAxisScope: Rect,
        ) {
            drawScope.drawLine(
                color = color,
                start = Offset(yAxisScope.right, yAxisScope.top),
                end = Offset(yAxisScope.right, yAxisScope.bottom),
            )
        }

        fun drawHelperLine(
            drawScope: DrawScope,
            chartScope: Rect,
            yMapper: YMapper,
            value: Float,
            label: String,
        ) {
            val y = yMapper.y(value)
            drawScope.drawLine(
                color = color,
                start = Offset(x = chartScope.left, y = y),
                end = Offset(x = chartScope.right, y = y),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 20f)),
            )
            drawScope.drawText(
                text = label,
                x = requiredWidth() - 10f,
                y = y,
                anchorX = TextAnchorX.Right,
                anchorY = TextAnchorY.Center,
                color = color,
                size = textSize.value,
            )
        }
    }
}