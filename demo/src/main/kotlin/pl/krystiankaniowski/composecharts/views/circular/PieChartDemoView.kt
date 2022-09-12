package pl.krystiankaniowski.composecharts.views.circular

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.circular.PieChart
import pl.krystiankaniowski.composecharts.circular.PieChartData

@Suppress("MagicNumber")
@Composable
fun PieChartDemo() {
    PieChart(
        data = PieChartData(
            slices = listOf(
                PieChartData.Slice(label = "Series A", color = autoColor(0), value = 1f),
                PieChartData.Slice(label = "Series B", color = autoColor(1), value = 2f),
                PieChartData.Slice(label = "Series C", color = autoColor(2), value = 3f),
            ),
        ),
        title = { Text("Pie chart") },
    )
}