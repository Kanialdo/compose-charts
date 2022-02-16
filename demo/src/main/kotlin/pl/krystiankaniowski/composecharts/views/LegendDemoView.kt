package pl.krystiankaniowski.composecharts.views

import androidx.compose.runtime.Composable
import pl.krystiankaniowski.composecharts.AutoColors
import pl.krystiankaniowski.composecharts.Legend
import pl.krystiankaniowski.composecharts.LegendEntry
import pl.krystiankaniowski.composecharts.LegendFlow

private val legendData = listOf(
    LegendEntry(text = "Series A", color = AutoColors.getColor(0)),
    LegendEntry(text = "Series B", color = AutoColors.getColor(1)),
    LegendEntry(text = "Series C", color = AutoColors.getColor(2)),
)

@Suppress("MagicNumber")
@Composable
fun LegendDemoView() {
    Legend(
        data = legendData
    )
}

@Suppress("MagicNumber")
@Composable
fun LegendFlowDemoView() {
    LegendFlow(
        data = legendData
    )
}
