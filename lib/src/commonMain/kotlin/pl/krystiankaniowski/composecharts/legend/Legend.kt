package pl.krystiankaniowski.composecharts.legend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class LegendPosition {
    Top,
    Bottom,
    None,
}

data class LegendEntry(
    val text: String,
    val color: Color,
)

@Composable
fun Legend(
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
