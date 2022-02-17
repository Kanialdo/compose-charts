package pl.krystiankaniowski.composecharts.views

import androidx.compose.runtime.Composable
import pl.krystiankaniowski.composecharts.BarChart
import pl.krystiankaniowski.composecharts.BarChartData
import pl.krystiankaniowski.composecharts.BarChartStyle

@Suppress("MagicNumber")
@Composable
fun BarChartDemoStandard() {
    BarChart(
        data = BarChartData(
            bars = listOf(
                BarChartData.Bar(label = "Series A", values = listOf(5f, 4f, 3f, 2f, 1f)),
                BarChartData.Bar(label = "Series B", values = listOf(1f, 1f, 1f, 1f, 1f)),
                BarChartData.Bar(label = "Series C", values = listOf(0f, 1f, 2f, 1f, 0f)),
            )
        ),
        style = BarChartStyle.STANDARD
    )
}

@Suppress("MagicNumber")
@Composable
fun BarChartDemoStacked() {
    BarChart(
        data = BarChartData(
            bars = listOf(
                BarChartData.Bar(label = "Series A", values = listOf(5f, 4f, 3f, 2f, 1f)),
                BarChartData.Bar(label = "Series B", values = listOf(1f, 1f, 1f, 1f, 1f)),
                BarChartData.Bar(label = "Series C", values = listOf(0f, 1f, 2f, 1f, 0f)),
            )
        ),
        style = BarChartStyle.STACKED
    )
}

@Suppress("MagicNumber")
@Composable
fun BarChartDemoProportion() {
    BarChart(
        data = BarChartData(
            bars = listOf(
                BarChartData.Bar(label = "Series A", values = listOf(5f, 4f, 3f, 2f, 1f)),
                BarChartData.Bar(label = "Series B", values = listOf(1f, 1f, 1f, 1f, 1f)),
                BarChartData.Bar(label = "Series C", values = listOf(0f, 1f, 2f, 1f, 0f)),
            )
        ),
        style = BarChartStyle.PROPORTION
    )
}