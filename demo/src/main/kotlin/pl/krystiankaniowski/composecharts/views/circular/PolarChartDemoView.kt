package pl.krystiankaniowski.composecharts.views.circular

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.circular.PolarChart
import pl.krystiankaniowski.composecharts.circular.PolarChartData

@Suppress("MagicNumber")
@Composable
fun PolarChartDemo() {
    PolarChart(
        title = { Text("Polar chart") },
        data = PolarChartData(
            entries = listOf(
                PolarChartData.Entry(label = "A", color = autoColor(0), value = 1f),
                PolarChartData.Entry(label = "B", color = autoColor(1), value = 3f),
                PolarChartData.Entry(label = "C", color = autoColor(2), value = 2f),
                PolarChartData.Entry(label = "D", color = autoColor(3), value = 5f),
                PolarChartData.Entry(label = "E", color = autoColor(4), value = 4f),
            ),
        ),
    )
}