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
                PolarChartData.Entry(label = "A", color = autoColor(0), value = 1f),
                PolarChartData.Entry(label = "B", color = autoColor(1), value = 3f),
                PolarChartData.Entry(label = "C", color = autoColor(2), value = 2f),
                PolarChartData.Entry(label = "D", color = autoColor(3), value = 5f),
                PolarChartData.Entry(label = "E", color = autoColor(4), value = 4f),
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
                PolarChartData.Entry(label = "A", color = autoColor(0), value = 142f),
                PolarChartData.Entry(label = "B", color = autoColor(1), value = 390f),
                PolarChartData.Entry(label = "C", color = autoColor(2), value = 284f),
                PolarChartData.Entry(label = "D", color = autoColor(3), value = 465f),
                PolarChartData.Entry(label = "E", color = autoColor(4), value = 432f),
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
                PolarChartData.Entry(label = "A", color = autoColor(0), value = 0.1f),
                PolarChartData.Entry(label = "B", color = autoColor(1), value = 0.3f),
                PolarChartData.Entry(label = "C", color = autoColor(2), value = 0.2f),
                PolarChartData.Entry(label = "D", color = autoColor(3), value = 0.5f),
                PolarChartData.Entry(label = "E", color = autoColor(4), value = 0.4f),
            ),
        ),
    )
}