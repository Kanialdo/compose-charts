package pl.krystiankaniowski.composecharts.internal

import android.graphics.Paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas

internal actual fun DrawScope.drawText(
    text: String,
    x: Float,
    y: Float,
    color: Color,
    size: Float
) {
    val paint = Paint().apply {
        this.color = color.value.toInt()
        textSize = size
    }
    drawContext.canvas.nativeCanvas.drawText(
        text,
        x - paint.measureText(text) / 2,
        y,
        paint
    )
}