package pl.krystiankaniowski.composecharts

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable

private val data = listOf(
    PieChartData(label = "Series A", value = 1f),
    PieChartData(label = "Series B", value = 2f),
    PieChartData(label = "Series C", value = 3f),
)

@Preview
@Composable
private fun PieChartPreview() {
    PieChart(
        data = data
    )
}