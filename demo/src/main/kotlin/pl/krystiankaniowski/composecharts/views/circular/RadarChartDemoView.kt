package pl.krystiankaniowski.composecharts.views.circular

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import pl.krystiankaniowski.composecharts.circular.RadarChart
import pl.krystiankaniowski.composecharts.circular.RadarChartData

@Suppress("MagicNumber")
@Composable
fun RadarChartDemo() {
    RadarChart(
        title = { Text("Radar chart") },
        data = RadarChartData(
            labels = listOf("A", "B", "C", "D", "E", "F"),
            entries = listOf(
                RadarChartData.Entry(label = "Color Red", color = Color.Red, values = listOf(1f, 2f, 3f, 4f, 5f, 6f)),
                RadarChartData.Entry(label = "Color Green", color = Color.Green, values = listOf(3f, 1f, 3f, 1f, 3f, 1f)),
            )
        ),
    )
}