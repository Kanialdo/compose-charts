package pl.krystiankaniowski.composecharts.internal

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

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

internal expect fun DrawScope.drawText(
    text: String,
    x: Float,
    y: Float,
    color: Color,
    size: Float,
    anchorX: TextAnchorX = TextAnchorX.Left,
    anchorY: TextAnchorY = TextAnchorY.Top,
)