package pl.krystiankaniowski.composecharts.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import pl.krystiankaniowski.composecharts.AutoColors
import pl.krystiankaniowski.composecharts.point.PointChart
import pl.krystiankaniowski.composecharts.point.PointChartData
import pl.krystiankaniowski.composecharts.point.PointChartXAxis
import pl.krystiankaniowski.composecharts.point.PointChartYAxis

@Suppress("MagicNumber")
@Composable
fun PointChartDemo() {
    PointChart(
        data = PointChartData(
            series = listOf(
                PointChartData.Series(
                    label = "Series A",
                    values = listOf(
                        Offset(0f, 5f),
                        Offset(0f, 4f),
                        Offset(0f, 3f),
                        Offset(0f, 2f),
                        Offset(0f, 1f),
                    ),
                    color = PointChartData.ChartColor.Solid(AutoColors.getColor(0))
                ),
                PointChartData.Series(
                    label = "Series B",
                    values = listOf(
                        Offset(5f, 5f),
                        Offset(4f, 4f),
                        Offset(3f, 3f),
                        Offset(2f, 2f),
                        Offset(1f, 1f),
                    ),
                    color = PointChartData.ChartColor.Solid(AutoColors.getColor(1))
                ),
                PointChartData.Series(
                    label = "Series C",
                    values = listOf(
                        Offset(5f, 0f),
                        Offset(4f, 0f),
                        Offset(3f, 0f),
                        Offset(2f, 0f),
                        Offset(1f, 0f),
                    ),
                    color = PointChartData.ChartColor.Solid(AutoColors.getColor(2))
                ),
            ),
        ),
        title = { Text("Point chart") },
    )
}

@Suppress("MagicNumber", "LongMethod")
@Composable
fun PointChartCustomDemo() {
    PointChart(
        data = PointChartData(
            series = listOf(
                PointChartData.Series(
                    label = "Series A",
                    values = listOf(
                        Offset(0f, 5f),
                        Offset(0f, 4f),
                        Offset(0f, 3f),
                        Offset(0f, 2f),
                        Offset(0f, 1f),
                    ),
                    color = PointChartData.ChartColor.Solid(AutoColors.getColor(1)),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 10f)),
                ),
                PointChartData.Series(
                    label = "Series B",
                    values = listOf(
                        Offset(5f, 5f),
                        Offset(4f, 4f),
                        Offset(3f, 3f),
                        Offset(2f, 2f),
                        Offset(1f, 1f),
                    ),
                    color = PointChartData.ChartColor.YGradient(
                        stops = listOf(
                            2f to Color.Green,
                            3f to Color.Yellow,
                            4f to Color.Magenta,
                        )
                    ),
                    strokeWidth = 2.5f,
                ),
                PointChartData.Series(
                    label = "Series C",
                    values = listOf(
                        Offset(5f, 0f),
                        Offset(4f, 0f),
                        Offset(3f, 0f),
                        Offset(2f, 0f),
                        Offset(1f, 0f),
                    ),
                    color = PointChartData.ChartColor.Solid(Color.Cyan),
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