package pl.krystiankaniowski.composecharts.area

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import pl.krystiankaniowski.composecharts.autoColor
import kotlin.random.Random

private val data = AreaChartData(
    lines = listOf(
        AreaChartData.Area(label = "Series A", color = autoColor(0), values = listOf(5f, 4f, 3f, 2f, 1f)),
        AreaChartData.Area(label = "Series B", color = autoColor(1), values = listOf(1f, 1f, 1f, 1f, 1f)),
        AreaChartData.Area(label = "Series C", color = autoColor(2), values = listOf(0f, 1f, 2f, 1f, 0f)),
    )
)

@Preview
@Composable
fun AreaChartPreview() {
    AreaChart(
        data = data,
        title = { Text("Area chart") },
    )
}

@Preview
@Composable
fun AreaChartNotRegularPreview() {
    AreaChart(
        data = AreaChartData(
            lines = listOf(
                AreaChartData.Area(
                    label = "Series A",
                    color = autoColor(0),
                    values = listOf(1.2f, 8.35f, 16.6f, 54.987f, 62.99f),
                ),
            )
        ),
        title = { Text("Area chart") },
    )
}

@Preview
@Composable
fun AreaChartCustomLabelsPreview() {
    AreaChart(
        data = AreaChartData(
            lines = listOf(
                AreaChartData.Area(
                    label = "Series A",
                    color = autoColor(0),
                    values = listOf(1f, 2f, 3f, 4f, 5f),
                ),
                AreaChartData.Area(
                    label = "Series B",
                    color = autoColor(1),
                    values = listOf(2f, 3f, 5f, 4f, 1f),
                ),
            )
        ),
        title = { Text("Area chart") },
        xAxis = AreaChartXAxis.Auto(label = { listOf("A", "B", "C", "D", "E")[it] }),
    )
}


@Preview
@Composable
fun AreaChartCustomStylePreview() {
    AreaChart(
        data = AreaChartData(
            lines = listOf(
                AreaChartData.Area(
                    label = "Custom color",
                    values = listOf(5f, 4f, 3f, 2f, 1f),
                    color = Color.Black,
                ),
                AreaChartData.Area(
                    label = "Custom line style",
                    values = listOf(1f, 1f, 1f, 1f, 1f),
                    lineStyle = AreaChartStyle.AreaStyle(width = 5f),
                    color = autoColor(1),
                ),
                AreaChartData.Area(
                    label = "Custom point style",
                    values = listOf(0f, 1f, 2f, 1f, 0f),
                    pointStyle = AreaChartStyle.PointStyle.Filled(size = 5f),
                    color = autoColor(2),
                ),
            )
        ),
        style = AreaChartStyle(
            lineStyle = AreaChartStyle.AreaStyle(width = 1f),
            pointStyle = AreaChartStyle.PointStyle.None,
        ),
        title = { Text("Area chart") },
    )
}

@Preview
@Composable
fun AreaChartComplexPreview() {
    val random = Random(0)
    val points = List(50) { random.nextFloat() * 5 }
    AreaChart(
        data = AreaChartData(
            lines = listOf(
                AreaChartData.Area(
                    label = "Series A",
                    color = autoColor(0),
                    values = points,
                )
            )
        ),
        title = { Text("Area chart") },
    )
}

@Preview
@Composable
fun AreaStackedChartComplexPreview() {
    val random = Random(0)
    val points1 = List(50) { random.nextFloat() * 5 }
    val points2 = List(50) { random.nextFloat() * 5 }
    AreaChart(
        data = AreaChartData(
            lines = listOf(
                AreaChartData.Area(
                    label = "Series A",
                    color = autoColor(0),
                    values = points1,
                ),
                AreaChartData.Area(
                    label = "Series B",
                    color = autoColor(1),
                    values = points2,
                ),
            )
        ),
        mode = AreaChartMode.STACKED,
        title = { Text("Area stacked chart") },
    )
}

@Preview
@Composable
fun AreaProportionalChartComplexPreview() {
    val random = Random(0)
    val points1 = List(50) { random.nextFloat() * 5 + 1 }
    val points2 = List(50) { random.nextFloat() * 5 + 1 }
    val points3 = List(50) { random.nextFloat() * 5 + 1 }
    AreaChart(
        data = AreaChartData(
            lines = listOf(
                AreaChartData.Area(
                    label = "Series A", color = autoColor(0), values = points1,
                ),
                AreaChartData.Area(
                    label = "Series B", color = autoColor(1), values = points2,
                ),
                AreaChartData.Area(
                    label = "Series C", color = autoColor(2), values = points3,
                ),
            )
        ),
        mode = AreaChartMode.PROPORTIONAL,
        title = { Text("Area proportional chart") },
    )
}