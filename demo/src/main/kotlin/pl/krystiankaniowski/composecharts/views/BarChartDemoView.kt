package pl.krystiankaniowski.composecharts.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.bar.BarChart
import pl.krystiankaniowski.composecharts.bar.BarChartData
import pl.krystiankaniowski.composecharts.bar.BarChartStyle

@Suppress("MagicNumber")
@Composable
fun BarChartDemoGrouped() {
    BarChart(
        data = BarChartData(
            labels = listOf(
                "C1", "C2", "C3", "C4", "C5",
            ),
            bars = listOf(
                BarChartData.Bar(label = "Series A", color = autoColor(0), values = listOf(5f, 4f, 3f, 2f, 1f)),
                BarChartData.Bar(label = "Series B", color = autoColor(1), values = listOf(1f, 1f, 1f, 1f, 1f)),
                BarChartData.Bar(label = "Series C", color = autoColor(2), values = listOf(0f, 1f, 2f, 1f, 0f)),
            )
        ),
        style = BarChartStyle.STANDARD,
        title = { Text("Bar chart standard") },
    )
}

@Suppress("MagicNumber")
@Composable
fun BarChartDemoStacked() {
    BarChart(
        data = BarChartData(
            labels = listOf(
                "C1", "C2", "C3", "C4", "C5",
            ),
            bars = listOf(
                BarChartData.Bar(label = "Series A", color = autoColor(0), values = listOf(5f, 4f, 3f, 2f, 1f)),
                BarChartData.Bar(label = "Series B", color = autoColor(1), values = listOf(1f, 1f, 1f, 1f, 1f)),
                BarChartData.Bar(label = "Series C", color = autoColor(2), values = listOf(0f, 1f, 2f, 1f, 0f)),
            )
        ),
        style = BarChartStyle.STACKED,
        title = { Text("Bar chart stacked") },
    )
}

@Suppress("MagicNumber")
@Composable
fun BarChartDemoProportion() {
    BarChart(
        data = BarChartData(
            labels = listOf(
                "C1", "C2", "C3", "C4", "C5",
            ),
            bars = listOf(
                BarChartData.Bar(label = "Series A", color = autoColor(0), values = listOf(5f, 4f, 3f, 2f, 1f)),
                BarChartData.Bar(label = "Series B", color = autoColor(1), values = listOf(1f, 1f, 1f, 1f, 1f)),
                BarChartData.Bar(label = "Series C", color = autoColor(2), values = listOf(0f, 1f, 2f, 1f, 0f)),
            )
        ),
        style = BarChartStyle.PROPORTION,
        title = { Text("Bar chart proportion") },
    )
}