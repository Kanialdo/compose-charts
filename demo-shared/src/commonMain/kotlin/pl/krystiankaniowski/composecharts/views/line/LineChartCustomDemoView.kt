package pl.krystiankaniowski.composecharts.views.line

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.line.LineChart

@Suppress("MagicNumber")
@Composable
fun LineChartCustomDemo() {
    LineChart(
        data = LineChart.Data(
            lines = listOf(
                LineChart.Line(
                    label = "Custom color",
                    values = listOf(5f, 4f, 3f, 2f, 1f),
                    color = Color.Black,
                ),
                LineChart.Line(
                    label = "Custom line style",
                    color = autoColor(0),
                    values = listOf(1f, 1f, 1f, 1f, 1f),
                    lineStyle = LineChart.Style.LineStyle(width = 5f),
                ),
                LineChart.Line(
                    label = "Custom point style",
                    color = autoColor(2),
                    values = listOf(0f, 1f, 2f, 1f, 0f),
                    pointStyle = LineChart.Style.PointStyle.Filled(size = 5f),
                ),
            ),
        ),
        style = LineChart.Style(
            lineStyle = LineChart.Style.LineStyle(width = 1f),
            pointStyle = LineChart.Style.PointStyle.None,
        ),
        title = { Text("Line chart custom style") },
    )
}