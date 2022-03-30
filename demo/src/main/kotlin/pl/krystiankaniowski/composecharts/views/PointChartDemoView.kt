package pl.krystiankaniowski.composecharts.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import pl.krystiankaniowski.composecharts.point.PointChart
import pl.krystiankaniowski.composecharts.point.PointChartData
import pl.krystiankaniowski.composecharts.point.PointChartXAxis
import pl.krystiankaniowski.composecharts.point.PointChartYAxis

@Suppress("MagicNumber")
@Composable
fun PointChartDemo() {
    PointChart(
        data = PointChartData(
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
                    label = "Series B",
                    values = listOf(
                        PointChartData.Point(5f, 5f),
                        PointChartData.Point(4f, 4f),
                        PointChartData.Point(3f, 3f),
                        PointChartData.Point(2f, 2f),
                        PointChartData.Point(1f, 1f),
                    )
                ),
                PointChartData.Points(
                    label = "Series C",
                    values = listOf(
                        PointChartData.Point(5f, 0f),
                        PointChartData.Point(4f, 0f),
                        PointChartData.Point(3f, 0f),
                        PointChartData.Point(2f, 0f),
                        PointChartData.Point(1f, 0f),
                    )
                ),
            ),
        ),
        title = { Text("Point chart") },
    )
}

@Suppress("MagicNumber")
@Composable
fun PointChartCustomDemo() {
    PointChart(
        data = PointChartData(
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
                    label = "Series B",
                    values = listOf(
                        PointChartData.Point(5f, 5f),
                        PointChartData.Point(4f, 4f),
                        PointChartData.Point(3f, 3f),
                        PointChartData.Point(2f, 2f),
                        PointChartData.Point(1f, 1f),
                    )
                ),
                PointChartData.Points(
                    label = "Series C",
                    values = listOf(
                        PointChartData.Point(5f, 0f),
                        PointChartData.Point(4f, 0f),
                        PointChartData.Point(3f, 0f),
                        PointChartData.Point(2f, 0f),
                        PointChartData.Point(1f, 0f),
                    )
                ),
            ),
        ),
        title = { Text("Point chart custom") },
        xAxis = PointChartXAxis.Fixed(
            points = listOf(
                PointChartXAxis.Label("X1", 1f),
                PointChartXAxis.Label("X2", 2f),
                PointChartXAxis.Label("X3", 3f),
            )
        ),
        yAxis = PointChartYAxis.Fixed(
            labels = listOf(
                1f to "Y1",
                2f to "Y2",
                3f to "Y3",
            )
        ),
    )
}