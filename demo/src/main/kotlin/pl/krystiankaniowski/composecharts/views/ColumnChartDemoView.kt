package pl.krystiankaniowski.composecharts.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.column.ColumnChart
import pl.krystiankaniowski.composecharts.column.ColumnChartData
import pl.krystiankaniowski.composecharts.column.ColumnChartStyle

@Suppress("MagicNumber")
@Composable
fun ColumnChartDemoGrouped() {
    ColumnChart(
        data = ColumnChartData(
            labels = listOf(
                "C1", "C2", "C3", "C4", "C5",
            ),
            columns = listOf(
                ColumnChartData.Column(label = "Series A", color = autoColor(0), values = listOf(5f, 4f, 3f, 2f, 1f)),
                ColumnChartData.Column(label = "Series B", color = autoColor(1), values = listOf(1f, 1f, 1f, 1f, 1f)),
                ColumnChartData.Column(label = "Series C", color = autoColor(2), values = listOf(0f, 1f, 2f, 1f, 0f)),
            )
        ),
        style = ColumnChartStyle.GROUPED,
        title = { Text("Column chart grouped") },
    )
}

@Suppress("MagicNumber")
@Composable
fun ColumnChartDemoStacked() {
    ColumnChart(
        data = ColumnChartData(
            labels = listOf(
                "C1", "C2", "C3", "C4", "C5",
            ),
            columns = listOf(
                ColumnChartData.Column(label = "Series A", color = autoColor(0), values = listOf(5f, 4f, 3f, 2f, 1f)),
                ColumnChartData.Column(label = "Series B", color = autoColor(1), values = listOf(1f, 1f, 1f, 1f, 1f)),
                ColumnChartData.Column(label = "Series C", color = autoColor(2), values = listOf(0f, 1f, 2f, 1f, 0f)),
            )
        ),
        style = ColumnChartStyle.STACKED,
        title = { Text("Column chart stacked") },
    )
}

@Suppress("MagicNumber")
@Composable
fun ColumnChartDemoProportional() {
    ColumnChart(
        data = ColumnChartData(
            labels = listOf(
                "C1", "C2", "C3", "C4", "C5",
            ),
            columns = listOf(
                ColumnChartData.Column(label = "Series A", color = autoColor(0), values = listOf(5f, 4f, 3f, 2f, 1f)),
                ColumnChartData.Column(label = "Series B", color = autoColor(1), values = listOf(1f, 1f, 1f, 1f, 1f)),
                ColumnChartData.Column(label = "Series C", color = autoColor(2), values = listOf(0f, 1f, 2f, 1f, 0f)),
            )
        ),
        style = ColumnChartStyle.PROPORTIONAL,
        title = { Text("Column chart proportional") },
    )
}