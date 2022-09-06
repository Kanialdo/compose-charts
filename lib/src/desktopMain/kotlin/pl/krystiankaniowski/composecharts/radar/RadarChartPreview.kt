package pl.krystiankaniowski.composecharts.radar

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Preview
@Composable
fun RadarChartPreview() {
    RadarChart(
        data = RadarChartData(
            labels = listOf(
                "A", "B", "C", "D", "E", "F",
            ),
            entries = listOf(
                RadarChartData.Entry(
                    name = "Color Green",
                    color = Color.Green,
                    values = listOf(3f, 18f, 30f, 1f, 3f, 71f),
                ),
            ),
        ),
    )
}

@Preview
@Composable
fun RadarChart2Preview() {
    RadarChart(
        data = RadarChartData(
            labels = listOf(
                "A", "B", "C", "D", "E", "F",
            ),
            entries = listOf(
                RadarChartData.Entry(
                    name = "Color Green",
                    color = Color.Green,
                    values = listOf(197f, 83f, 312f, 75f, 40f, 15f),
                ),
            ),
        ),
    )
}

@Preview
@Composable
fun RadarChart3Preview() {
    RadarChart(
        data = RadarChartData(
            labels = listOf(
                "A", "B", "C", "D", "E", "F",
            ),
            entries = listOf(
                RadarChartData.Entry(
                    name = "Color Green",
                    color = Color.Green,
                    values = listOf(0.197f, 0.83f, 0.312f, 0.75f, 0.40f, 0.15f),
                ),
            ),
        ),
    )
}