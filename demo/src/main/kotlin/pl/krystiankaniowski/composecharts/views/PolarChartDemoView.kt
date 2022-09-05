package pl.krystiankaniowski.composecharts.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.polar.PolarChart
import pl.krystiankaniowski.composecharts.polar.PolarChartData

@Suppress("MagicNumber")
@Composable
fun PolarChartDemo() {
    PolarChart(
        title = { Text("Polar chart") },
        data = PolarChartData(
            entries = listOf(
                PolarChartData.Entry(name = "A", color = autoColor(0), value = 1f),
                PolarChartData.Entry(name = "B", color = autoColor(1), value = 3f),
                PolarChartData.Entry(name = "C", color = autoColor(2), value = 2f),
                PolarChartData.Entry(name = "D", color = autoColor(3), value = 5f),
                PolarChartData.Entry(name = "E", color = autoColor(4), value = 4f),
            ),
        ),
    )
}