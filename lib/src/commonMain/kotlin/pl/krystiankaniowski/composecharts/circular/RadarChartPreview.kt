package pl.krystiankaniowski.composecharts.circular

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.ui.tooling.preview.Preview
import pl.krystiankaniowski.composecharts.data.ChartColor

@Preview
@Composable
private fun RadarChartPreview() {
    RadarChart(
        data = RadarChart.Data(
            labels = listOf("A", "B", "C", "D", "E", "F"),
            entries = listOf(
                RadarChart.Entry(
                    label = "Color Green",
                    color = ChartColor.Solid(Color.Green),
                    values = listOf(3f, 18f, 30f, 1f, 3f, 71f),
                ),
            ),
        ),
    )
}

@Preview
@Composable
private fun RadarChart2Preview() {
    RadarChart(
        data = RadarChart.Data(
            labels = listOf("A", "B", "C", "D", "E", "F"),
            entries = listOf(
                RadarChart.Entry(
                    label = "Color Green",
                    color = ChartColor.Solid(Color.Green),
                    values = listOf(197f, 83f, 312f, 75f, 40f, 15f),
                ),
            ),
        ),
    )
}

@Preview
@Composable
private fun RadarChart3Preview() {
    RadarChart(
        data = RadarChart.Data(
            labels = listOf("A", "B", "C", "D", "E", "F"),
            entries = listOf(
                RadarChart.Entry(
                    label = "Color Green",
                    color = ChartColor.Solid(Color.Green),
                    values = listOf(0.197f, 0.83f, 0.312f, 0.75f, 0.40f, 0.15f),
                ),
            ),
        ),
    )
}