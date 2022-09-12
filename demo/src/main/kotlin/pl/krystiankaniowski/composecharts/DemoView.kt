package pl.krystiankaniowski.composecharts

import androidx.compose.foundation.clickable
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

val items: List<Pair<String, @Composable () -> Unit>> = listOf(
    "Bar chart standard" to { BarChartDemoGrouped() },
    "Bar chart stacked" to { BarChartDemoStacked() },
    "Bar chart proportion" to { BarChartDemoProportion() },
    "Horizontal bar chart standard" to { HorizontalBarChartDemoGrouped() },
    "Horizontal bar chart stacked" to { HorizontalBarChartDemoStacked() },
    "Horizontal bar chart proportion" to { HorizontalBarChartDemoProportion() },
    "Line chart" to { LineChartDemo() },
    "Line stacked chart" to { LineStackedChartDemo() },
    "Line proportional chart" to { LineProportionalChartDemo() },
    "Line chart custom" to { LineChartCustomDemo() },
    "Point chart" to { PointChartDemo() },
    "Point chart custom" to { PointChartCustomDemo() },
    "Pie chart" to { PieChartDemo() },
    "Doughnut chart" to { DoughnutChartDemo() },
    "Sunbrust chart" to { SunbrustChartDemo() },
    "Polar chart" to { PolarChartDemo() },
    "Radar chart" to { RadarChartDemo() },
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
    Row {
        Box(modifier = Modifier.weight(@Suppress("MagicNumber") 3f).padding(16.dp)) {
            chart()
        }
        Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(16.dp)) {
            settings()
        }
    }
}