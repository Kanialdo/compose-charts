package pl.krystiankaniowski.composecharts.bar

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import pl.krystiankaniowski.composecharts.autoColor

private val data = BarChartData(
    labels = listOf(
        "C1", "C2", "C3", "C4", "C5",
    ),
    dataSets = listOf(
        BarChartData.DataSet(label = "Series A", color = autoColor(0), values = listOf(5f, 4f, 3f, 2f, 1f)),
        BarChartData.DataSet(label = "Series B", color = autoColor(1), values = listOf(1f, 1f, 1f, 1f, 1f)),
        BarChartData.DataSet(label = "Series C", color = autoColor(2), values = listOf(0f, 1f, 2f, 1f, 0f)),
    )
)

@Preview
@Composable
fun BarChartGroupedPreview() {
    BarChart(
        data = data,
        style = BarChartStyle.GROUPED,
        title = { Text(" bar chart standard") },
    )
}

@Preview
@Composable
fun BarChartStackedPreview() {
    BarChart(
        data = data,
        style = BarChartStyle.STACKED,
        title = { Text(" bar chart stacked") },
    )
}

@Preview
@Composable
fun BarChartProportionalPreview() {
    BarChart(
        data = data,
        style = BarChartStyle.PROPORTIONAL,
        title = { Text(" bar chart proportion") },
    )
}