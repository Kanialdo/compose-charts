package pl.krystiankaniowski.composecharts.point

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import org.jetbrains.compose.ui.tooling.preview.Preview
import pl.krystiankaniowski.composecharts.autoColor
import kotlin.random.Random

@Suppress("MagicNumber")
private val data = PointChart.Data(
    series = listOf(
        PointChart.Series(
            label = "Series A",
            values = listOf(
                Offset(0f, 5f),
                Offset(0f, 4f),
                Offset(0f, 3f),
                Offset(0f, 2f),
                Offset(0f, 1f),
            ),
            color = PointChart.ChartColor.Solid(autoColor(0).value),
        ),
        PointChart.Series(
            label = "Series B",
            values = listOf(
                Offset(5f, 4f),
                Offset(4f, 3f),
                Offset(3f, 2f),
                Offset(2f, 1f),
                Offset(1f, 0f),
            ),
            color = PointChart.ChartColor.Solid(autoColor(1).value),
        ),
        PointChart.Series(
            label = "Series C",
            values = listOf(
                Offset(5f, 1.5f),
                Offset(4f, 1.5f),
                Offset(3f, 1.5f),
                Offset(2f, 1.5f),
                Offset(1f, 1.5f),
            ),
            color = PointChart.ChartColor.Solid(autoColor(2).value),
        ),
    ),
)

@Preview
@Composable
fun PointChartPreview() {
    PointChart(
        data = data,
        title = { Text("Point chart") },
    )
}

@Preview
@Composable
fun PointChartCustomXAxisPreview() {
    PointChart(
        data = data,
        title = { Text("Point chart") },
        xAxis = PointChartXAxis.Fixed(
            points = listOf(
                PointChartXAxis.Label(label = "A", value = 1f),
                PointChartXAxis.Label(label = "B", value = 2f),
                PointChartXAxis.Label(label = "C", value = 3f),
            ),
        ),
    )
}

@Preview
@Composable
fun PointChartCustomXAxisRangePreview() {
    PointChart(
        data = data,
        title = { Text("Point chart") },
        xAxis = PointChartXAxis.FixedRanges(
            points = listOf(
                PointChartXAxis.Range(label = "A", from = 0f, to = 1f),
                PointChartXAxis.Range(label = "B", from = 1f, to = 2f),
                PointChartXAxis.Range(label = "C", from = 2f, to = 3f),
                PointChartXAxis.Range(label = "D", from = 3f, to = 4f),
                PointChartXAxis.Range(label = "E", from = 4f, to = 5f),
            ),
        ),
    )
}

@Preview
@Composable
fun PointChartComplexPreview() {
    val random = Random(0)
    val points = List(50) { index -> Offset(index / 10f, random.nextFloat() * 5) }
    PointChart(
        data = PointChart.Data(
            series = listOf(
                PointChart.Series(
                    label = "Data",
                    values = points,
                    color = PointChart.ChartColor.Solid(autoColor(1).value),
                ),
            ),
        ),
        title = { Text("Point chart") },
    )
}

@Preview
@Composable
fun PointChartCustomBoundsPreview() {
    val random = Random(0)
    val points = List(50) { index -> Offset(index / 10f, random.nextFloat() * 5) }
    PointChart(
        data = PointChart.Data(
            series = listOf(
                PointChart.Series(
                    label = "Data",
                    values = points,
                    color = PointChart.ChartColor.Solid(autoColor(0).value),
                ),
            ),
            minX = 0f,
            maxX = 10f,
            minY = 0f,
            maxY = 10f,
        ),
        title = { Text("Point chart") },
    )
}