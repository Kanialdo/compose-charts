package pl.krystiankaniowski.composecharts

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

private val data = PieChartData(
    slices = listOf(
        PieChartData.Slice(label = "Series A", value = 1f),
        PieChartData.Slice(label = "Series B", value = 2f),
        PieChartData.Slice(label = "Series C", value = 3f),
    )
)

@Preview
@Composable
private fun PieChartPreview() {
    PieChart(
        data = data,
        title = { Text("Pie chart") },
    )
}