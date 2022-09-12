package pl.krystiankaniowski.composecharts.views.circular

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.circular.DoughnutChart
import pl.krystiankaniowski.composecharts.circular.DoughnutChartData

@Suppress("MagicNumber")
@Composable
fun DoughnutChartDemo() {
    DoughnutChart(
        data = DoughnutChartData(
            slices = listOf(
                DoughnutChartData.Slice(label = "Series A", color = autoColor(0), value = 1f),
                DoughnutChartData.Slice(label = "Series B", color = autoColor(1), value = 2f),
                DoughnutChartData.Slice(label = "Series C", color = autoColor(2), value = 3f),
            ),
        ),
        title = { Text("Pie chart") },
    )
}