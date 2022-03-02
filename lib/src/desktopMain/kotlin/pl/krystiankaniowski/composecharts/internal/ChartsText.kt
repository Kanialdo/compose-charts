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
    size: Float
) {
    val textLine = TextLine.Companion.make(text, Font(typeface = null, size = size))
    drawContext.canvas.nativeCanvas.drawTextLine(
        line = textLine,
        x = x - textLine.width / 2,
        y = y,
        paint = Paint().apply {
            this.color = color.toArgb()
        }
    )
}