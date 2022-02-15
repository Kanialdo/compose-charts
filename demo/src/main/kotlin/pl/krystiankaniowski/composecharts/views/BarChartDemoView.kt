package pl.krystiankaniowski.composecharts.views

import androidx.compose.runtime.Composable
import pl.krystiankaniowski.composecharts.BarChart
import pl.krystiankaniowski.composecharts.BarChartData
import pl.krystiankaniowski.composecharts.BarChartStyle

@Composable
fun BarChartDemoStandard() {
    BarChart(
        data = listOf(
            BarChartData(label = "Series A", values = listOf(5f, 4f, 3f, 2f, 1f)),
            BarChartData(label = "Series B", values = listOf(1f, 1f, 1f, 1f, 1f)),
            BarChartData(label = "Series C", values = listOf(0f, 1f, 2f, 1f, 0f)),
        ),
        style = BarChartStyle.STANDARD
    )
}

@Composable
fun BarChartDemoStacked() {
    BarChart(
        data = listOf(
            BarChartData(label = "Series A", values = listOf(5f, 4f, 3f, 2f, 1f)),
            BarChartData(label = "Series B", values = listOf(1f, 1f, 1f, 1f, 1f)),
            BarChartData(label = "Series C", values = listOf(0f, 1f, 2f, 1f, 0f)),
        ),
        style = BarChartStyle.STACKED
    )
}

@Composable
fun BarChartDemoProportion() {
    BarChart(
        data = listOf(
            BarChartData(label = "Series A", values = listOf(5f, 4f, 3f, 2f, 1f)),
            BarChartData(label = "Series B", values = listOf(1f, 1f, 1f, 1f, 1f)),
            BarChartData(label = "Series C", values = listOf(0f, 1f, 2f, 1f, 0f)),
        ),
        style = BarChartStyle.PROPORTION
    )
}