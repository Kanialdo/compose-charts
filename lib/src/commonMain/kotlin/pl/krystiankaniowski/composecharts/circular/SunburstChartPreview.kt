package pl.krystiankaniowski.composecharts.circular

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import pl.krystiankaniowski.composecharts.autoColor

@Preview
@Composable
private fun SunburstChartPreview() {
    SunburstChart(
        data = SunburstChart.Data(
            slices = listOf(
                SunburstChart.Slice(
                    label = "Series A",
                    color = autoColor(0),
                    value = 3f,
                    subSlices = listOf(
                        SunburstChart.Slice(
                            label = "Series A1",
                            color = autoColor(1),
                            value = 1f,
                            subSlices = listOf(
                                SunburstChart.Slice(label = "Series A1.1", color = autoColor(2), value = 0.5f),
                            ),
                        ),
                        SunburstChart.Slice(
                            label = "Series A2",
                            color = autoColor(3),
                            value = 2f,
                            subSlices = listOf(
                                SunburstChart.Slice(label = "Series A1.1", color = autoColor(4), value = 0.5f),
                                SunburstChart.Slice(label = "Series A1.2", color = autoColor(5), value = 0.25f),
                            ),
                        ),
                    ),
                ),
                SunburstChart.Slice(label = "Series B", color = autoColor(6), value = 2f),
                SunburstChart.Slice(label = "Series C", color = autoColor(7), value = 1f),
            ),
        ),
        title = { Text("Sunburst chart") },
    )
}