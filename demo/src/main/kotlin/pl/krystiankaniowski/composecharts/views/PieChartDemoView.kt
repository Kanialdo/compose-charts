package pl.krystiankaniowski.composecharts.views

import androidx.compose.runtime.Composable
import pl.krystiankaniowski.composecharts.PieChart
import pl.krystiankaniowski.composecharts.PieChartData

@Suppress("MagicNumber")
@Composable
fun PieChartDemo() {
    PieChart(
        data = PieChartData(
            slices = listOf(
                PieChartData.Slice(label = "Series A", value = 1f),
                PieChartData.Slice(label = "Series B", value = 2f),
                PieChartData.Slice(label = "Series C", value = 3f),
            ),
        )
    )
}