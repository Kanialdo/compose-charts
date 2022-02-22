package pl.krystiankaniowski.composecharts

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.sp
import pl.krystiankaniowski.composecharts.internal.drawText

fun DrawScope.XAxis(
    labels: Map<Float, String>,
    xMapper: (Float) -> Float,
    drawableArea: Rect
) {
    drawLine(
        color = Color.Black,
        Offset(drawableArea.left, drawableArea.top),
        Offset(drawableArea.right, drawableArea.top)
    )
    labels.forEach { (position, label) ->
        drawText(
            text = label,
            x = xMapper(position),
            y = drawableArea.top + 16.sp.value * 1.5f,
            color = Color.Black,
            size = 16.sp.value
        )
    }
}

data class XAxis(
    val xMin: Float,
    val xMax: Float,
    val textSize: Float,
    val color: Color,
) {
    val requiredHeight: Float get() = 20f
}