package pl.krystiankaniowski.composecharts.legend

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import pl.krystiankaniowski.composecharts.autoColor

private val legendData = listOf(
    LegendEntry(text = "Series A", color = autoColor(0)),
    LegendEntry(text = "Series B", color = autoColor(1)),
    LegendEntry(text = "Series C", color = autoColor(2)),
)

@Preview
@Composable
fun LegendPreview() {
    Legend(
        data = legendData,
    )
}

@Preview
@Composable
fun LegendFlowPreview() {
    LegendFlow(
        data = legendData,
    )
}