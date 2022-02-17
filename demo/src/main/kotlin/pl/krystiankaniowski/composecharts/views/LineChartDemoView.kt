package pl.krystiankaniowski.composecharts.views

import androidx.compose.runtime.Composable
import pl.krystiankaniowski.composecharts.LineChart
import pl.krystiankaniowski.composecharts.LineChartData

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
    )
}