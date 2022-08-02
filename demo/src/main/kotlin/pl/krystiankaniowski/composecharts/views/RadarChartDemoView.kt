package pl.krystiankaniowski.composecharts.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import pl.krystiankaniowski.composecharts.radar.RadarChart
import pl.krystiankaniowski.composecharts.radar.RadarChartData

@Suppress("MagicNumber")
@Composable
fun RadarChartDemo() {
    RadarChart(
        data = RadarChartData(
            labels = listOf(
                "A", "B", "C", "D", "E", "F"
            ),
            entries = listOf(
                RadarChartData.Entry(
                    color = Color.Red,
                    values = listOf(1f, 2f, 3f, 4f, 5f, 6f)
                ),
                RadarChartData.Entry(
                    color = Color.Green,
                    values = listOf(3f, 1f, 3f, 1f, 3f, 1f)
                ),
            )
        ),
    )
}