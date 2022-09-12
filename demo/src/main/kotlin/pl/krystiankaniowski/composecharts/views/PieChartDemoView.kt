package pl.krystiankaniowski.composecharts.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.pie.PieChart
import pl.krystiankaniowski.composecharts.pie.PieChartData

@Suppress("MagicNumber")
@Composable
fun PieChartDemo() {
    PieChart(
        data = PieChartData(
            slices = listOf(
                PieChartData.Slice(label = "Series A", color = autoColor(0), value = 1f),
                PieChartData.Slice(label = "Series B", color = autoColor(1), value = 2f),
                PieChartData.Slice(label = "Series C", color = autoColor(2), value = 3f),
            ),
        ),
        title = { Text("Pie chart") },
    )
}

@Suppress("MagicNumber")
@Composable
fun DoughnutChartDemo() {
    DoughnutChart(
        data = DoughnutChartData(
            slices = listOf(
                DoughnutChartData.Slice(label = "Series A", color = autoColor(0), value = 1f),
                DoughnutChartData.Slice(label = "Series B", color = autoColor(1), value = 2f),
                DoughnutChartData.Slice(label = "Series C", color = autoColor(2), value = 3f),
            ),
        ),
        title = { Text("Doughnut chart") },
    )
}


@Suppress("MagicNumber")
@Composable
fun SunbrustChartDemo() {
    SunbrustChart(
        data = SunbrustChartData(
            slices = listOf(
                SunbrustChartData.Slice(
                    label = "Series A",
                    color = autoColor(0),
                    value = 3f,
                    subSlices = listOf(
                        SunbrustChartData.Slice(
                            label = "Series A1",
                            color = autoColor(1),
                            value = 1f,
                            subSlices = listOf(
                                SunbrustChartData.Slice(
                                    label = "Series A1.1",
                                    color = autoColor(2),
                                    value = 0.5f,
                                ),
                            ),
                        ),
                        SunbrustChartData.Slice(
                            label = "Series A2",
                            color = autoColor(3),
                            value = 2f,
                            subSlices = listOf(
                                SunbrustChartData.Slice(
                                    label = "Series A1.1",
                                    color = autoColor(4),
                                    value = 0.5f,
                                ),
                                SunbrustChartData.Slice(
                                    label = "Series A1.2",
                                    color = autoColor(5),
                                    value = 0.25f,
                                ),
                            ),
                        ),
                    )
                ),
                SunbrustChartData.Slice(
                    label = "Series B",
                    color = autoColor(6),
                    value = 2f,
                ),
                SunbrustChartData.Slice(
                    label = "Series C",
                    color = autoColor(7),
                    value = 1f,
                ),
            ),
        ),
        title = { Text("Sunbrust chart") },
    )
}