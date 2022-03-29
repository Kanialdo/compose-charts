package pl.krystiankaniowski.composecharts

import androidx.compose.ui.graphics.Color

interface Colors {
    fun getColor(position: Int): Color
}

object AutoColors : Colors {

    private val colors = arrayOf(
        0xFFe6194B,
        0xFF3cb44b,
        0xFFffe119,
        0xFF4363d8,
        0xFFf58231,
        0xFF911eb4,
        0xFF42d4f4,
        0xFFf032e6,
        0xFFbfef45,
        0xFFfabed4,
        0xFF469990,
        0xFFdcbeff,
        0xFF9A6324,
        0xFFfffac8,
        0xFF800000,
        0xFFaaffc3,
        0xFF808000,
        0xFFffd8b1,
        0xFF000075,
        0xFFa9a9a9,
        0xFFffffff,
        0xFF000000,
    )

    override fun getColor(position: Int) = Color(colors[position % colors.size])
}

internal fun Colors.resolve(pos: Int, custom: Color) =
    if (custom == Color.Unspecified) this.getColor(pos) else custom