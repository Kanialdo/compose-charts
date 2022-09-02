package pl.krystiankaniowski.composecharts.radar

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable

@Preview
@Composable
fun RadarChartPreview() {
    RadarChart(
        data = RadarChartData(
            labels = listOf(
                "A", "B", "C",
            ),
            entries = emptyList(),
        ),
    )
}