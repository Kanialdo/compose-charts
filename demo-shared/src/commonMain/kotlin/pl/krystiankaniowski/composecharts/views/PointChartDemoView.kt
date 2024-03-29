package pl.krystiankaniowski.composecharts.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.axis.YAxis
import pl.krystiankaniowski.composecharts.point.PointChart
import pl.krystiankaniowski.composecharts.point.PointChartXAxis

@Suppress("MagicNumber")
@Composable
fun PointChartDemo() {
    PointChart(
        data = PointChart.Data(
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
                        Offset(5f, 5f),
                        Offset(4f, 4f),
                        Offset(3f, 3f),
                        Offset(2f, 2f),
                        Offset(1f, 1f),
                    ),
                    color = PointChart.ChartColor.Solid(autoColor(1).value),
                ),
                PointChart.Series(
                    label = "Series C",
                    values = listOf(
                        Offset(5f, 0f),
                        Offset(4f, 0f),
                        Offset(3f, 0f),
                        Offset(2f, 0f),
                        Offset(1f, 0f),
                    ),
                    color = PointChart.ChartColor.Solid(autoColor(2).value),
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
        data = PointChart.Data(
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
                    color = PointChart.ChartColor.Solid(autoColor(1).value),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 10f)),
                ),
                PointChart.Series(
                    label = "Series B",
                    values = listOf(
                        Offset(5f, 5f),
                        Offset(4f, 4f),
                        Offset(3f, 3f),
                        Offset(2f, 2f),
                        Offset(1f, 1f),
                    ),
                    color = PointChart.ChartColor.YGradient(
                        stops = listOf(
                            2f to Color.Green,
                            3f to Color.Yellow,
                            4f to Color.Magenta,
                        ),
                    ),
                    strokeWidth = 2.5f,
                ),
                PointChart.Series(
                    label = "Series C",
                    values = listOf(
                        Offset(5f, 0f),
                        Offset(4f, 0f),
                        Offset(3f, 0f),
                        Offset(2f, 0f),
                        Offset(1f, 0f),
                    ),
                    color = PointChart.ChartColor.Solid(Color.Cyan),
                ),
            ),
        ),
        title = { Text("Point chart custom") },
        xAxis = PointChartXAxis.Fixed(
            points = listOf(
                PointChartXAxis.Label("X1", 1f),
                PointChartXAxis.Label("X2", 2f),
                PointChartXAxis.Label("X3", 3f),
            ),
        ),
        yAxis = YAxis.Fixed(
            values = listOf(
                YAxis.Value(label = "Y1", value = 1f),
                YAxis.Value(label = "Y2", value = 2f),
                YAxis.Value(label = "Y3", value = 3f),
            ),
        ),
    )
}