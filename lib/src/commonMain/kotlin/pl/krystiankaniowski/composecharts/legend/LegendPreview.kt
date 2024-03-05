package pl.krystiankaniowski.composecharts.legend

import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import pl.krystiankaniowski.composecharts.autoColor

private val legendData = listOf(
    LegendEntry(text = "Series A", color = autoColor(0).value),
    LegendEntry(text = "Series B", color = autoColor(1).value),
    LegendEntry(text = "Series C", color = autoColor(2).value),
)

@Preview
@Composable
fun LegendPreview() {
    CustomLegend(
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