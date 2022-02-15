package pl.krystiankaniowski.composecharts.views

import androidx.compose.runtime.Composable
import pl.krystiankaniowski.composecharts.PieChart
import pl.krystiankaniowski.composecharts.PieChartData

@Composable
fun PieChartDemo() {
    PieChart(
        data = listOf(
            PieChartData(label = "Series A", 1f),
            PieChartData(label = "Series B", 2f),
            PieChartData(label = "Series C", 3f),
        ),
    )
}