package pl.krystiankaniowski.composecharts

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable

private val legendData = listOf(
    LegendEntry(text = "Series A", color = AutoColors.getColor(0)),
    LegendEntry(text = "Series B", color = AutoColors.getColor(1)),
    LegendEntry(text = "Series C", color = AutoColors.getColor(2)),
)

@Preview
@Composable
fun LegendPreview() {
    Legend(
        data = legendData
    )
}

@Preview
@Composable
fun LegendFlowPreview() {
    LegendFlow(
        data = legendData
    )
}