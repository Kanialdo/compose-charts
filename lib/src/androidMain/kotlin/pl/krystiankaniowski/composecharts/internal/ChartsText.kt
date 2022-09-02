package pl.krystiankaniowski.composecharts.internal

import android.graphics.Paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb

internal actual fun DrawScope.drawText(
    text: String,
    x: Float,
    y: Float,
    color: Color,
    size: Float,
    anchorX: TextAnchorX,
    anchorY: TextAnchorY,
) {
    val paint = Paint().apply {
        this.color = color.toArgb()
        textSize = size
    }
    drawContext.canvas.nativeCanvas.drawText(
        text,
        when (anchorX) {
            TextAnchorX.Left -> x
            TextAnchorX.Center -> x - paint.measureText(text) / 2
            TextAnchorX.Right -> x - paint.measureText(text)
        },
        when (anchorY) {
            TextAnchorY.Top -> y
            TextAnchorY.Center -> y - size / 2
            TextAnchorY.Bottom -> y - size
        },
        paint,
    )
}