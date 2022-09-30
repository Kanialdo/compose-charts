package pl.krystiankaniowski.composecharts.line

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import pl.krystiankaniowski.composecharts.area.AreaChart
import pl.krystiankaniowski.composecharts.area.AreaChartXAxis
import pl.krystiankaniowski.composecharts.autoColor

@Suppress("MagicNumber")
private val data = AreaChart.Data(
    areas = listOf(
        AreaChart.Area(label = "Series A", color = autoColor(0), values = listOf(5f, 4f, 3f, 2f, 1f)),
        AreaChart.Area(label = "Series B", color = autoColor(1), values = listOf(1f, 1f, 1f, 1f, 1f)),
        AreaChart.Area(label = "Series C", color = autoColor(2), values = listOf(0f, 1f, 2f, 1f, 0f)),
    ),
)

@Preview
@Composable
fun AreaChartPreview() {
    AreaChart(
        data = data,
        title = { Text("Line chart") },
    )
}

@Preview
@Composable
fun AreaChartNotRegularPreview() {
    AreaChart(
        data = AreaChart.Data(
            areas = listOf(
                AreaChart.Area(
                    label = "Series A",
                    color = autoColor(0),
                    values = listOf(1.2f, 8.35f, 16.6f, 54.987f, 62.99f),
                ),
            ),
        ),
        title = { Text("Line chart") },
    )
}

@Preview
@Composable
fun AreaChartCustomLabelsPreview() {
    AreaChart(
        data = AreaChart.Data(
            areas = listOf(
                AreaChart.Area(
                    label = "Series A",
                    color = autoColor(0),
                    values = listOf(1f, 2f, 3f, 4f, 5f),
                ),
                AreaChart.Area(
                    label = "Series B",
                    color = autoColor(1),
                    values = listOf(2f, 3f, 5f, 4f, 1f),
                ),
            ),
        ),
        title = { Text("Line chart") },
        xAxis = AreaChartXAxis.Auto(label = { listOf("A", "B", "C", "D", "E")[it] }),
    )
}