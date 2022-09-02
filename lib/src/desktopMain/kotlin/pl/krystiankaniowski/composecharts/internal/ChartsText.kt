package pl.krystiankaniowski.composecharts.internal

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import org.jetbrains.skia.Font
import org.jetbrains.skia.Paint
import org.jetbrains.skia.TextLine

internal actual fun DrawScope.drawText(
    text: String,
    x: Float,
    y: Float,
    color: Color,
    size: Float,
    anchorX: TextAnchorX,
    anchorY: TextAnchorY,
) {
    val textLine = TextLine.make(text, Font(typeface = null, size = size))
    drawContext.canvas.nativeCanvas.drawTextLine(
        line = textLine,
        x = when (anchorX) {
            TextAnchorX.Left -> x
            TextAnchorX.Center -> x - textLine.width / 2
            TextAnchorX.Right -> x - textLine.width,
        },
        y = when (anchorY) {
            TextAnchorY.Top -> y
            TextAnchorY.Center -> y + size / 2
            TextAnchorY.Bottom -> y + size,
        },
        paint = Paint().apply {
            this.color = color.toArgb()
        },
    )
}