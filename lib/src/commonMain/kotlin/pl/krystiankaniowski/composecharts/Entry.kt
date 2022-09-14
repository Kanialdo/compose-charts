package pl.krystiankaniowski.composecharts

import androidx.compose.ui.graphics.Color

interface Entry {
    val label: String
    val color: Color
    val value: Float
}