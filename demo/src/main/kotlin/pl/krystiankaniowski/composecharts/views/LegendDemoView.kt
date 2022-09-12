package pl.krystiankaniowski.composecharts.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.legend.Legend
import pl.krystiankaniowski.composecharts.legend.LegendEntry
import pl.krystiankaniowski.composecharts.legend.LegendFlow

private val legendData = listOf(
    LegendEntry(text = "Series A", color = autoColor(0)),
    LegendEntry(text = "Series B", color = autoColor(1)),
    LegendEntry(text = "Series C", color = autoColor(2)),
)

@Suppress("MagicNumber")
@Composable
fun LegendDemoView() {
    Box(modifier = Modifier.padding(16.dp)) {
        Legend(
            data = legendData,
        )
    }
}

@Suppress("MagicNumber")
@Composable
fun LegendFlowDemoView() {
    Box(modifier = Modifier.padding(16.dp)) {
        LegendFlow(
            data = legendData,
        )
    }
}
