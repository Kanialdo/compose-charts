package pl.krystiankaniowski.composecharts.axis

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

sealed class YAxis {
    object Auto : YAxis()
    data class Fixed(val values: List<Float>) : YAxis()
    object None : YAxis()
}

fun DrawScope.drawYAxis(xMin: Float, xMax: Float, yMin: Float, yMax: Float, yAxis: YAxis) {
    when (yAxis) {
        YAxis.Auto -> {
            for (i in 0 until 5) {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(x = xMin, y = yMax / 5 * i),
                    end = Offset(x = xMax, y = yMax / 5 * i)
                )
            }
        }
        is YAxis.Fixed -> {
            yAxis.values.forEach {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(x = xMin, y = it / yMax),
                    end = Offset(x = xMax, y = it / yMax)
                )
            }
        }
        YAxis.None -> {
        }
    }
}