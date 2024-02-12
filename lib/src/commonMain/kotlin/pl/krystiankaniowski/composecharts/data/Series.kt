package pl.krystiankaniowski.composecharts.data

import androidx.compose.ui.graphics.Color

interface Series {
    val label: String
    val color: ChartColor
}

sealed interface ChartColor {
    data class Solid(val value: Color) : ChartColor
}