package pl.krystiankaniowski.composecharts.views.circular

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.circular.SunburstChart
import pl.krystiankaniowski.composecharts.circular.SunburstChartData

@Suppress("MagicNumber")
@Composable
fun SunburstChartDemo() {
    SunburstChart(
        data = SunburstChartData(
            slices = listOf(
                SunburstChartData.Slice(
                    label = "Series A",
                    color = autoColor(0),
                    value = 3f,
                    subSlices = listOf(
                        SunburstChartData.Slice(
                            label = "Series A1",
                            color = autoColor(1),
                            value = 1f,
                            subSlices = listOf(
                                SunburstChartData.Slice(label = "Series A1.1", color = autoColor(2), value = 0.5f),
                            ),
                        ),
                        SunburstChartData.Slice(
                            label = "Series A2",
                            color = autoColor(3),
                            value = 2f,
                            subSlices = listOf(
                                SunburstChartData.Slice(label = "Series A1.1", color = autoColor(4), value = 0.5f),
                                SunburstChartData.Slice(label = "Series A1.2", color = autoColor(5), value = 0.25f),
                            ),
                        ),
                    ),
                ),
                SunburstChartData.Slice(label = "Series B", color = autoColor(6), value = 2f),
                SunburstChartData.Slice(label = "Series C", color = autoColor(7), value = 1f),
            ),
        ),
        title = { Text("Sunburst chart") },
    )
}