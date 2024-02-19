package pl.krystiankaniowski.composecharts.internal

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.TextUnit

enum class TextAnchorX {
    Left,
    Center,
    Right,
}

enum class TextAnchorY {
    Top,
    Center,
    Bottom,
}

@Deprecated("Use DrawScope.drawText with TextUnit instead")
fun DrawScope.drawText(
    textMeasurer: TextMeasurer,
    text: String,
    x: Float,
    y: Float,
    color: Color,
    size: Float,
    anchorX: TextAnchorX = TextAnchorX.Left,
    anchorY: TextAnchorY = TextAnchorY.Top,
) {
    val layout = textMeasurer.measure(text)
    drawText(
        textMeasurer = textMeasurer,
        text = text,
        style = TextStyle(
            fontSize = size.toSp(),
            color = color,
        ),
        topLeft = Offset(
            x = when (anchorX) {
                TextAnchorX.Left -> x
                TextAnchorX.Center -> x - layout.size.width / 2
                TextAnchorX.Right -> x - layout.size.width
            },
            y = when (anchorY) {
                TextAnchorY.Top -> y - layout.size.height
                TextAnchorY.Center -> y - layout.size.height / 2
                TextAnchorY.Bottom -> y
            },
        ),
    )
}

fun DrawScope.drawText(
    textMeasurer: TextMeasurer,
    text: String,
    x: Float,
    y: Float,
    color: Color,
    size: TextUnit,
    anchorX: TextAnchorX = TextAnchorX.Left,
    anchorY: TextAnchorY = TextAnchorY.Top,
) {
    val layout = textMeasurer.measure(text)
    drawText(
        textMeasurer = textMeasurer,
        text = text,
        style = TextStyle(
            fontSize = size,
            color = color,
        ),
        topLeft = Offset(
            x = when (anchorX) {
                TextAnchorX.Left -> x
                TextAnchorX.Center -> x - layout.size.width / 2
                TextAnchorX.Right -> x - layout.size.width
            },
            y = when (anchorY) {
                TextAnchorY.Top -> y - layout.size.height
                TextAnchorY.Center -> y - layout.size.height / 2
                TextAnchorY.Bottom -> y
            },
        ),
    )
}

fun DrawScope.measureText(textMeasurer: TextMeasurer, size: Float, text: String): Float {
    return textMeasurer.measure(text, style = TextStyle(fontSize = size.toSp())).size.width.toFloat()
}