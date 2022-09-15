package pl.krystiankaniowski.composecharts

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ImageComposeScene
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import org.jetbrains.skia.Image
import pl.krystiankaniowski.composecharts.views.*
import pl.krystiankaniowski.composecharts.views.circular.*
import pl.krystiankaniowski.composecharts.views.line.*

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
    Column {
        BoxWithConstraints(Modifier.weight(1f)) {
            val image = ImageComposeScene(
                width = (LocalDensity.current.density * this.maxWidth.value).toInt(),
                height = (LocalDensity.current.density * this.maxHeight.value).toInt(),
                content = {
                    Box(
                        modifier = Modifier.padding(16.dp),
                        content = { chart() },
                    )
                }
            ).render()
            println(image.imageInfo.width)
            println(image.imageInfo.height)
            Image(bitmap = image.toComposeImageBitmap(), null)
        }
        Divider(modifier = Modifier.fillMaxWidth().height(1.dp))
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()).padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            content = { settings() },
        )
    }
}