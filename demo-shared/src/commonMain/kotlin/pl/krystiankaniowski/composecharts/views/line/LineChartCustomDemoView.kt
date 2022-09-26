package pl.krystiankaniowski.composecharts.views.line

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.line.LineChart
import pl.krystiankaniowski.composecharts.line.LineChartData
import pl.krystiankaniowski.composecharts.line.LineChartStyle

@Suppress("MagicNumber")
@Composable
fun LineChartCustomDemo() {
    LineChart(
        data = LineChartData(
            lines = listOf(
                LineChartData.Line(
                    label = "Custom color",
                    values = listOf(5f, 4f, 3f, 2f, 1f),
                    color = Color.Black,
                ),
                LineChartData.Line(
                    label = "Custom line style",
                    color = autoColor(0),
                    values = listOf(1f, 1f, 1f, 1f, 1f),
                    lineStyle = LineChartStyle.LineStyle(width = 5f),
                ),
                LineChartData.Line(
                    label = "Custom point style",
                    color = autoColor(2),
                    values = listOf(0f, 1f, 2f, 1f, 0f),
                    pointStyle = LineChartStyle.PointStyle.Filled(size = 5f),
                ),
            ),
        ),
        style = LineChartStyle(
            lineStyle = LineChartStyle.LineStyle(width = 1f),
            pointStyle = LineChartStyle.PointStyle.None,
        ),
        title = { Text("Line chart custom style") },
    )
}