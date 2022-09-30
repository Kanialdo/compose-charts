package pl.krystiankaniowski.composecharts.circular

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import pl.krystiankaniowski.composecharts.autoColor

private val data = DoughnutChart.Data(
    slices = listOf(
        DoughnutChart.Slice(label = "Series A", color = autoColor(0), value = 1f),
        DoughnutChart.Slice(label = "Series B", color = autoColor(1), value = 2f),
        DoughnutChart.Slice(label = "Series C", color = autoColor(2), value = 3f),
    ),
)

@Preview
@Composable
private fun DoughnutChartPreview() {
    DoughnutChart(
        data = data,
        title = { Text("Doughnut chart") },
    )
}