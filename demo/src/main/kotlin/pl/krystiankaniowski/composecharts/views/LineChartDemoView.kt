package pl.krystiankaniowski.composecharts.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import pl.krystiankaniowski.composecharts.line.LineChart
import pl.krystiankaniowski.composecharts.line.LineChartData
import pl.krystiankaniowski.composecharts.line.LineChartStyle

@Suppress("MagicNumber")
@Composable
fun LineChartDemo() {
    LineChart(
        data = LineChartData(
            lines = listOf(
                LineChartData.Line(label = "Series A", values = listOf(5f, 4f, 3f, 2f, 1f)),
                LineChartData.Line(label = "Series B", values = listOf(1f, 1f, 1f, 1f, 1f)),
                LineChartData.Line(label = "Series C", values = listOf(0f, 1f, 2f, 1f, 0f)),
            )
        ),
        title = { Text("Line chart") },
    )
}

@Suppress("MagicNumber")
@Composable
fun LineChartCustomDemo() {
    LineChart(
        data = LineChartData(
            lines = listOf(
                LineChartData.Line(
                    label = "Custom color",
                    values = listOf(5f, 4f, 3f, 2f, 1f),
                    color = Color.Black
                ),
                LineChartData.Line(
                    label = "Custom line style",
                    values = listOf(1f, 1f, 1f, 1f, 1f),
                    lineStyle = LineChartStyle.LineStyle(width = 5f)
                ),
                LineChartData.Line(
                    label = "Custom point style",
                    values = listOf(0f, 1f, 2f, 1f, 0f),
                    pointStyle = LineChartStyle.PointStyle.Filled(size = 5f)
                ),
            )
        ),
        style = LineChartStyle(
            lineStyle = LineChartStyle.LineStyle(width = 1f),
            pointStyle = LineChartStyle.PointStyle.None
        ),
        title = { Text("Line chart custom style") },
    )
}