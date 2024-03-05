package pl.krystiankaniowski.composecharts.circular

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import pl.krystiankaniowski.composecharts.autoColor

private val data = PieChart.Data(
    slices = listOf(
        PieChart.Slice(label = "Series A", color = autoColor(0), value = 1f),
        PieChart.Slice(label = "Series B", color = autoColor(1), value = 2f),
        PieChart.Slice(label = "Series C", color = autoColor(2), value = 3f),
    ),
)

@Preview
@Composable
private fun PieChartPreview() {
    PieChart(
        data = data,
        title = { Text("Pie chart") },
    )
}