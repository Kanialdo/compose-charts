package pl.krystiankaniowski.composecharts.horizontalbar

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.bar.HorizontalBarChart
import pl.krystiankaniowski.composecharts.bar.HorizontalBarChartData
import pl.krystiankaniowski.composecharts.bar.HorizontalBarChartStyle

private val data = HorizontalBarChartData(
    labels = listOf(
        "C1", "C2", "C3", "C4", "C5",
    ),
    dataSets = listOf(
        HorizontalBarChartData.DataSet(label = "Series A", color = autoColor(0), values = listOf(5f, 4f, 3f, 2f, 1f)),
        HorizontalBarChartData.DataSet(label = "Series B", color = autoColor(1), values = listOf(1f, 1f, 1f, 1f, 1f)),
        HorizontalBarChartData.DataSet(label = "Series C", color = autoColor(2), values = listOf(0f, 1f, 2f, 1f, 0f)),
    )
)

@Preview
@Composable
fun HorizontalBarChartStandardPreview() {
    HorizontalBarChart(
        data = data,
        style = HorizontalBarChartStyle.GROUPED,
        title = { Text("Horizontal bar chart standard") },
    )
}

@Preview
@Composable
fun HorizontalBarChartCombinePreview() {
    HorizontalBarChart(
        data = data,
        style = HorizontalBarChartStyle.STACKED,
        title = { Text("Horizontal bar chart stacked") },
    )
}

@Preview
@Composable
fun HorizontalBarChartProportionPreview() {
    HorizontalBarChart(
        data = data,
        style = HorizontalBarChartStyle.PROPORTION,
        title = { Text("Horizontal bar chart proportion") },
    )
}