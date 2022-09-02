package pl.krystiankaniowski.composecharts.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.horizontalbar.HorizontalBarChart
import pl.krystiankaniowski.composecharts.horizontalbar.HorizontalBarChartData
import pl.krystiankaniowski.composecharts.horizontalbar.HorizontalBarChartStyle

@Suppress("MagicNumber")
@Composable
fun HorizontalBarChartDemoGrouped() {
    HorizontalBarChart(
        data = HorizontalBarChartData(
            dataSets = listOf(
                HorizontalBarChartData.DataSet(label = "Series A", color = autoColor(0), values = listOf(5f, 4f, 3f, 2f, 1f)),
                HorizontalBarChartData.DataSet(label = "Series B", color = autoColor(1), values = listOf(1f, 1f, 1f, 1f, 1f)),
                HorizontalBarChartData.DataSet(label = "Series C", color = autoColor(2), values = listOf(0f, 1f, 2f, 1f, 0f)),
            )
        ),
        style = HorizontalBarChartStyle.STANDARD,
        title = { Text("Horizontal bar chart standard") },
    )
}

@Suppress("MagicNumber")
@Composable
fun HorizontalBarChartDemoStacked() {
    HorizontalBarChart(
        data = HorizontalBarChartData(
            dataSets = listOf(
                HorizontalBarChartData.DataSet(label = "Series A", color = autoColor(0), values = listOf(5f, 4f, 3f, 2f, 1f)),
                HorizontalBarChartData.DataSet(label = "Series B", color = autoColor(1), values = listOf(1f, 1f, 1f, 1f, 1f)),
                HorizontalBarChartData.DataSet(label = "Series C", color = autoColor(2), values = listOf(0f, 1f, 2f, 1f, 0f)),
            )
        ),
        style = HorizontalBarChartStyle.STACKED,
        title = { Text("Horizontal bar chart stacked") },
    )
}

@Suppress("MagicNumber")
@Composable
fun HorizontalBarChartDemoProportion() {
    HorizontalBarChart(
        data = HorizontalBarChartData(
            dataSets = listOf(
                HorizontalBarChartData.DataSet(label = "Series A", color = autoColor(0), values = listOf(5f, 4f, 3f, 2f, 1f)),
                HorizontalBarChartData.DataSet(label = "Series B", color = autoColor(1), values = listOf(1f, 1f, 1f, 1f, 1f)),
                HorizontalBarChartData.DataSet(label = "Series C", color = autoColor(2), values = listOf(0f, 1f, 2f, 1f, 0f)),
            )
        ),
        style = HorizontalBarChartStyle.PROPORTION,
        title = { Text("Horizontal bar chart proportion") },
    )
}