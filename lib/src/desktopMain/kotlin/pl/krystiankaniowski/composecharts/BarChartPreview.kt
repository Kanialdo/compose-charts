package pl.krystiankaniowski.composecharts

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable

private val data = BarChartData(
    bars = listOf(
        BarChartData.Bar(label = "Series A", values = listOf(5f, 4f, 3f, 2f, 1f)),
        BarChartData.Bar(label = "Series B", values = listOf(1f, 1f, 1f, 1f, 1f)),
        BarChartData.Bar(label = "Series C", values = listOf(0f, 1f, 2f, 1f, 0f)),
    )
)

@Preview
@Composable
fun BarChartStandardPreview() {
    BarChart(
        data = data,
        style = BarChartStyle.STANDARD
    )
}

@Preview
@Composable
fun BarChartCombinePreview() {
    BarChart(
        data = data,
        style = BarChartStyle.STACKED
    )
}

@Preview
@Composable
fun BarChartProportionPreview() {
    BarChart(
        data = data,
        style = BarChartStyle.PROPORTION
    )
}