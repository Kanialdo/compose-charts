package pl.krystiankaniowski.composecharts.line

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import pl.krystiankaniowski.composecharts.autoColor

@Suppress("MagicNumber")
private val data = LineChartData(
    lines = listOf(
        LineChartData.Line(label = "Series A", color = autoColor(0), values = listOf(5f, 4f, 3f, 2f, 1f)),
        LineChartData.Line(label = "Series B", color = autoColor(1), values = listOf(1f, 1f, 1f, 1f, 1f)),
        LineChartData.Line(label = "Series C", color = autoColor(2), values = listOf(0f, 1f, 2f, 1f, 0f)),
    ),
)

@Preview
@Composable
fun LineChartPreview() {
    LineChart(
        data = data,
        title = { Text("Line chart") },
    )
}

@Preview
@Composable
fun LineChartNotRegularPreview() {
    LineChart(
        data = LineChartData(
            lines = listOf(
                LineChartData.Line(
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
fun LineChartCustomLabelsPreview() {
    LineChart(
        data = LineChartData(
            lines = listOf(
                LineChartData.Line(
                    label = "Series A",
                    color = autoColor(0),
                    values = listOf(1f, 2f, 3f, 4f, 5f),
                ),
                LineChartData.Line(
                    label = "Series B",
                    color = autoColor(1),
                    values = listOf(2f, 3f, 5f, 4f, 1f),
                ),
            ),
        ),
        title = { Text("Line chart") },
        xAxis = LineChartXAxis.Auto(label = { listOf("A", "B", "C", "D", "E")[it] }),
    )
}

@Preview
@Composable
fun LineChartCustomStylePreview() {
    LineChart(
        data = LineChartData(
            lines = listOf(
                LineChartData.Line(
                    label = "Custom color",
                    values = listOf(5f, 4f, 3f, 2f, 1f),
                    color = Color.Black,
                ),
                LineChartData.Line(
                    label = "Custom line style",
                    values = listOf(1f, 1f, 1f, 1f, 1f),
                    lineStyle = LineChartStyle.LineStyle(width = 5f),
                    color = autoColor(1),
                ),
                LineChartData.Line(
                    label = "Custom point style",
                    values = listOf(0f, 1f, 2f, 1f, 0f),
                    pointStyle = LineChartStyle.PointStyle.Filled(size = 5f),
                    color = autoColor(2),
                ),
            ),
        ),
        style = LineChartStyle(
            lineStyle = LineChartStyle.LineStyle(width = 1f),
            pointStyle = LineChartStyle.PointStyle.None,
        ),
        title = { Text("Line chart") },
    )
}