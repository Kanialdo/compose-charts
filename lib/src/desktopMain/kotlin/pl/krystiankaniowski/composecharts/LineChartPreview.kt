package pl.krystiankaniowski.composecharts

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import kotlin.random.Random

private val data = LineChartData(
    lines = listOf(
        LineChartData.Line(label = "Series A", values = listOf(5f, 4f, 3f, 2f, 1f)),
        LineChartData.Line(label = "Series B", values = listOf(1f, 1f, 1f, 1f, 1f)),
        LineChartData.Line(label = "Series C", values = listOf(0f, 1f, 2f, 1f, 0f)),
    )
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
fun LineChartComplexPreview() {
    val random = Random(0)
    val points = List(50) { random.nextFloat() * 5 }
    LineChart(
        data = LineChartData(
            lines = listOf(
                LineChartData.Line(
                    label = "Series A", values = points,
                )
            )
        ),
        title = { Text("Line chart") },
    )
}