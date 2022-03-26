package pl.krystiankaniowski.composecharts.point

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

private val data = PointChartData(
    points = listOf(
        PointChartData.Points(
            label = "Series A",
            values = listOf(
                PointChartData.Point(0f, 5f),
                PointChartData.Point(0f, 4f),
                PointChartData.Point(0f, 3f),
                PointChartData.Point(0f, 2f),
                PointChartData.Point(0f, 1f),
            )
        ),
        PointChartData.Points(
            label = "Series B", values = listOf(
                PointChartData.Point(5f, 4f),
                PointChartData.Point(4f, 3f),
                PointChartData.Point(3f, 2f),
                PointChartData.Point(2f, 1f),
                PointChartData.Point(1f, 0f),
            )
        ),
        PointChartData.Points(
            label = "Series C", values = listOf(
                PointChartData.Point(5f, 1.5f),
                PointChartData.Point(4f, 1.5f),
                PointChartData.Point(3f, 1.5f),
                PointChartData.Point(2f, 1.5f),
                PointChartData.Point(1f, 1.5f),
            )
        ),
    )
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
        xAxis = PointChartXAxis.Linear(
            labels = PointChartXAxis.Labels.Custom(
                labels = listOf(
                    PointChartXAxis.Label.Point(
                        name = "A",
                        value = 1f
                    ),
                    PointChartXAxis.Label.Point(
                        name = "B",
                        value = 2f
                    ),
                    PointChartXAxis.Label.Point(
                        name = "C",
                        value = 3f
                    ),
                )
            )
        )
    )
}

@Preview
@Composable
fun PointChartCustomXAxisRangePreview() {
    PointChart(
        data = data,
        title = { Text("Point chart") },
        xAxis = PointChartXAxis.Linear(
            labels = PointChartXAxis.Labels.Custom(
                labels = listOf(
                    PointChartXAxis.Label.Range(
                        name = "A",
                        from = 0f,
                        to = 2f,
                    ),
                    PointChartXAxis.Label.Range(
                        name = "B",
                        from = 2f,
                        to = 5f,
                    ),
                )
            )
        )
    )
}