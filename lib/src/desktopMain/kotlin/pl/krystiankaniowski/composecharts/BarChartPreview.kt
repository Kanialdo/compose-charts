package pl.krystiankaniowski.composecharts

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val data = listOf(
    BarChartData("Series A", Color.Companion.Red, listOf(5f, 4f, 3f, 2f, 1f)),
    BarChartData("Series B", Color.Companion.Blue, listOf(1f, 1f, 1f, 1f, 1f)),
    BarChartData("Series C", Color.Companion.Green, listOf(0f, 1f, 2f, 1f, 0f)),
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
        style = BarChartStyle.COMBINE
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