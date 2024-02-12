package pl.krystiankaniowski.composecharts.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.data.Series
import pl.krystiankaniowski.composecharts.legend.Legend

private data class LegendData(
    override val label: String,
    override val color: Color,
) : Series

private val legendData = listOf(
    LegendData(label = "Series A", color = autoColor(0)),
    LegendData(label = "Series B", color = autoColor(1)),
    LegendData(label = "Series C", color = autoColor(2)),
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
