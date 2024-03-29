package pl.krystiankaniowski.composecharts.column

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import pl.krystiankaniowski.composecharts.autoColor

@Suppress("MagicNumber")
private val data = ColumnChart.Data(
    labels = listOf("C1", "C2", "C3", "C4", "C5"),
    columns = listOf(
        ColumnChart.Column(label = "Series A", color = autoColor(0), values = listOf(5f, 4f, 3f, 2f, 1f)),
        ColumnChart.Column(label = "Series B", color = autoColor(1), values = listOf(1f, 1f, 1f, 1f, 1f)),
        ColumnChart.Column(label = "Series C", color = autoColor(2), values = listOf(0f, 1f, 2f, 1f, 0f)),
    ),
)

@Preview
@Composable
fun ColumnChartStandardPreview() {
    ColumnChart(
        data = data,
        style = ColumnChart.Style.GROUPED,
        title = { Text("Column chart grouped") },
    )
}

@Preview
@Composable
fun ColumnChartCombinePreview() {
    ColumnChart(
        data = data,
        style = ColumnChart.Style.STACKED,
        title = { Text("Column chart stacked") },
    )
}

@Preview
@Composable
fun ColumnChartProportionalPreview() {
    ColumnChart(
        data = data,
        style = ColumnChart.Style.PROPORTIONAL,
        title = { Text("Column chart proportional") },
    )
}