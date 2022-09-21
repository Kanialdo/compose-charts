package pl.krystiankaniowski.composecharts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pl.krystiankaniowski.composecharts.views.*
import pl.krystiankaniowski.composecharts.views.circular.*
import pl.krystiankaniowski.composecharts.views.line.LineChartCustomDemo
import pl.krystiankaniowski.composecharts.views.line.LineChartDemo

val items: List<Pair<String, @Composable () -> Unit>> = listOf(
    "Column grouped" to { ColumnGroupedChartDemo() },
    "Column stacked" to { ColumnStackedChartDemo() },
    "Column proportional" to { ColumnProportionalChartDemo() },
    "Bar grouped" to { BarGroupedChartDemo() },
    "Bar stacked" to { BarStackedChartDemo() },
    "Bar proportional" to { BarProportionalChartDemo() },
    "Line" to { LineChartDemo() },
    "Line custom" to { LineChartCustomDemo() },
    "Area overlapping" to { AreaOverlappingChartDemo() },
    "Area stacked" to { AreaStackedChartDemo() },
    "Area proportional" to { AreaProportionalChartDemo() },
    "Point chart" to { PointChartDemo() },
    "Point chart custom" to { PointChartCustomDemo() },
    "Pie" to { PieChartDemo() },
    "Doughnut" to { DoughnutChartDemo() },
    "Sunburst" to { SunburstChartDemo() },
    "Polar" to { PolarChartDemo() },
    "Radar" to { RadarChartDemo() },
    "Legend" to { LegendDemoView() },
    "Legend Flow" to { LegendFlowDemoView() },
)

@Composable
fun DemoView() {

    var current by remember { mutableStateOf(0) }

    Surface {
        Row {
            Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {
                items.forEachIndexed { index, (label, _) ->
                    Text(
                        text = label,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { current = index }
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        color = if (index == current) MaterialTheme.colors.primary else Color.Unspecified,
                    )
                }
            }
            Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            Box(modifier = Modifier.weight(@Suppress("MagicNumber") 3f).padding(16.dp)) {
                items[current].second.invoke()
            }
        }
    }
}

@Composable
fun ChartScreen(
    chart: @Composable () -> Unit,
    settings: @Composable () -> Unit,
) {
    Column {
        Box(
            modifier = Modifier.weight(1f).padding(16.dp),
            content = { chart() },
        )
        Divider(modifier = Modifier.fillMaxWidth().height(1.dp))
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()).padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            content = { settings() },
        )
    }
}