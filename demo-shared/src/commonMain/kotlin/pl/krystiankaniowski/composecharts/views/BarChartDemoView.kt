package pl.krystiankaniowski.composecharts.views

import androidx.compose.material.Text
import androidx.compose.runtime.*
import kotlinx.datetime.Clock
import pl.krystiankaniowski.composecharts.ChartScreen
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.bar.BarChart
import pl.krystiankaniowski.composecharts.components.*
import pl.krystiankaniowski.composecharts.utils.generateList
import pl.krystiankaniowski.composecharts.utils.randomize
import kotlin.random.Random

@Composable
fun BarGroupedChartDemo() {
    BarRawChartDemo(
        title = "Bar Grouped Chart",
        style = BarChart.Style.GROUPED,
    )
}

@Composable
fun BarStackedChartDemo() {
    BarRawChartDemo(
        title = "Bar Stacked Chart",
        style = BarChart.Style.STACKED,
    )
}

@Composable
fun BarProportionalChartDemo() {
    BarRawChartDemo(
        title = "Bar Proportional Chart",
        style = BarChart.Style.PROPORTIONAL,
    )
}

@Suppress("MagicNumber", "LongMethod")
@Composable
private fun BarRawChartDemo(title: String, style: BarChart.Style) {

    var data by remember {
        val random = Random(Clock.System.now().toEpochMilliseconds())
        val size = 5
        mutableStateOf(
            BarChart.Data(
                labels = buildList {
                    for (i in 0 until size) {
                        add("B$i")
                    }
                },
                bars = listOf(
                    createEntry(random, 0, size),
                    createEntry(random, 1, size),
                    createEntry(random, 2, size),
                ),
            ),
        )
    }

    ChartScreen(
        chart = {
            BarChart(
                data = data,
                title = { Text(title) },
                style = style,
            )
        },
        settings = {
            OptionRandomize { random ->
                data = data.copy(
                    bars = data.bars.map {
                        it.copy(values = it.values.randomize(random))
                    },
                )
            }
            OptionAddDataSet { random ->
                val newId = data.bars.size
                data = data.copy(bars = data.bars + createEntry(random = random, id = newId, size = data.bars.first().values.size))
            }
            OptionRemoveDataSet {
                data = data.copy(bars = data.bars.dropLast(1))
            }
            OptionAddData { random ->
                data = data.copy(
                    labels = data.labels + "B${data.labels.size}",
                    bars = data.bars.map { it.copy(values = it.values + random.nextFloat()) },
                )
            }
            OptionRemoveData {
                data = data.copy(
                    labels = data.labels.dropLast(1),
                    bars = data.bars.map { it.copy(values = it.values.dropLast(1)) },
                )
            }
        },
    )
}

private fun createEntry(random: Random, id: Int, size: Int): BarChart.Bar {
    return BarChart.Bar(
        label = "Series ${id + 1}",
        color = autoColor(id),
        values = generateList(random, size),
    )
}