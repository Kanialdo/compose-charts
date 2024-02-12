package pl.krystiankaniowski.composecharts.legend

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.krystiankaniowski.composecharts.ChartsTheme
import pl.krystiankaniowski.composecharts.data.Series

enum class LegendPosition {
    Top,
    Bottom,
    None,
}

@Composable
fun Legend(
    modifier: Modifier = Modifier,
    data: List<Series>,
) {
    Box(modifier = modifier.border(width = 1.dp, color = ChartsTheme.legendColor)) {
        LegendFlow(
            modifier = Modifier.padding(16.dp),
            data = data.map {
                LegendEntry(
                    text = it.label,
                    color = it.color,
                )
            },
        )
    }
}

@Deprecated("Use Series instead")
data class LegendEntry(
    val text: String,
    val color: Color,
)

@Deprecated("Use Legend Series based instead")
@Composable
fun CustomLegend(
    modifier: Modifier = Modifier,
    data: List<LegendEntry>,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        data.forEach {
            LegendEntry(it)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LegendFlow(
    modifier: Modifier = Modifier,
    data: List<LegendEntry>,
) {
    FlowRow(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        data.forEach {
            LegendEntry(it)
        }
    }
}

@Composable
private fun LegendEntry(
    legendEntry: LegendEntry,
) {
    Row {
        Box(modifier = Modifier.size(16.dp).background(legendEntry.color))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = legendEntry.text, fontSize = 12.sp)
    }
}
