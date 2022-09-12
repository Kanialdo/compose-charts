package pl.krystiankaniowski.composecharts.circular

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import pl.krystiankaniowski.composecharts.autoColor

@Preview
@Composable
private fun PolarChartPreview() {
    PolarChart(
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

@Preview
@Composable
private fun PolarChart2Preview() {
    PolarChart(
        data = PolarChartData(
            entries = listOf(
                PolarChartData.Entry(name = "A", color = autoColor(0), value = 142f),
                PolarChartData.Entry(name = "B", color = autoColor(1), value = 390f),
                PolarChartData.Entry(name = "C", color = autoColor(2), value = 284f),
                PolarChartData.Entry(name = "D", color = autoColor(3), value = 465f),
                PolarChartData.Entry(name = "E", color = autoColor(4), value = 432f),
            ),
        ),
    )
}

@Preview
@Composable
private fun PolarChart3Preview() {
    PolarChart(
        data = PolarChartData(
            entries = listOf(
                PolarChartData.Entry(name = "A", color = autoColor(0), value = 0.1f),
                PolarChartData.Entry(name = "B", color = autoColor(1), value = 0.3f),
                PolarChartData.Entry(name = "C", color = autoColor(2), value = 0.2f),
                PolarChartData.Entry(name = "D", color = autoColor(3), value = 0.5f),
                PolarChartData.Entry(name = "E", color = autoColor(4), value = 0.4f),
            ),
        ),
    )
}