package pl.krystiankaniowski.composecharts.circular

import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import pl.krystiankaniowski.composecharts.autoColor

@Preview
@Composable
private fun PolarChartPreview() {
    PolarChart(
        data = PolarChart.Data(
            entries = listOf(
                PolarChart.Entry(label = "A", color = autoColor(0), value = 1f),
                PolarChart.Entry(label = "B", color = autoColor(1), value = 3f),
                PolarChart.Entry(label = "C", color = autoColor(2), value = 2f),
                PolarChart.Entry(label = "D", color = autoColor(3), value = 5f),
                PolarChart.Entry(label = "E", color = autoColor(4), value = 4f),
            ),
        ),
    )
}

@Preview
@Composable
private fun PolarChart2Preview() {
    PolarChart(
        data = PolarChart.Data(
            entries = listOf(
                PolarChart.Entry(label = "A", color = autoColor(0), value = 142f),
                PolarChart.Entry(label = "B", color = autoColor(1), value = 390f),
                PolarChart.Entry(label = "C", color = autoColor(2), value = 284f),
                PolarChart.Entry(label = "D", color = autoColor(3), value = 465f),
                PolarChart.Entry(label = "E", color = autoColor(4), value = 432f),
            ),
        ),
    )
}

@Preview
@Composable
private fun PolarChart3Preview() {
    PolarChart(
        data = PolarChart.Data(
            entries = listOf(
                PolarChart.Entry(label = "A", color = autoColor(0), value = 0.1f),
                PolarChart.Entry(label = "B", color = autoColor(1), value = 0.3f),
                PolarChart.Entry(label = "C", color = autoColor(2), value = 0.2f),
                PolarChart.Entry(label = "D", color = autoColor(3), value = 0.5f),
                PolarChart.Entry(label = "E", color = autoColor(4), value = 0.4f),
            ),
        ),
    )
}