package pl.krystiankaniowski.composecharts.views

import androidx.compose.material.Text
import androidx.compose.runtime.*
import pl.krystiankaniowski.composecharts.ChartScreen
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.bar.BarChart
import pl.krystiankaniowski.composecharts.bar.BarChartData
import pl.krystiankaniowski.composecharts.bar.BarChartStyle
import pl.krystiankaniowski.composecharts.components.*
import pl.krystiankaniowski.composecharts.utils.generateList
import pl.krystiankaniowski.composecharts.utils.randomize
import kotlin.random.Random

@Composable
fun BarGroupedChartDemo() {
    BarRawChartDemo(
        title = "Bar Grouped Chart",
        style = BarChartStyle.GROUPED,
    )
}

@Composable
fun BarStackedChartDemo() {
    BarRawChartDemo(
        title = "Bar Stacked Chart",
        style = BarChartStyle.STACKED,
    )
}

@Composable
fun BarProportionalChartDemo() {
    BarRawChartDemo(
        title = "Bar Proportional Chart",
        style = BarChartStyle.PROPORTIONAL,
    )
}

@Suppress("MagicNumber", "LongMethod")
@Composable
private fun BarRawChartDemo(title: String, style: BarChartStyle) {

    var data by remember {
        val random = Random(System.currentTimeMillis())
        val size = 5
        mutableStateOf(
            BarChartData(
                labels = buildList {
                    for (i in 0 until size) {
                        add("B$i")
                    }
                },
                dataSets = listOf(
                    createEntry(random, 0, size),
                    createEntry(random, 1, size),
                    createEntry(random, 2, size),
                ),
            )
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
                    dataSets = data.dataSets.map {
                        it.copy(values = it.values.randomize(random))
                    },
                )
            }
            OptionAddDataSet { random ->
                val newId = data.dataSets.size
                data = data.copy(dataSets = data.dataSets + createEntry(random = random, id = newId, size = data.dataSets.first().values.size))
            }
            OptionRemoveDataSet {
                data = data.copy(dataSets = data.dataSets.dropLast(1))
            }
            OptionAddData { random ->
                data = data.copy(
                    labels = data.labels + "B${data.labels.size}",
                    dataSets = data.dataSets.map { it.copy(values = it.values + random.nextFloat()) },
                )
            }
            OptionRemoveData {
                data = data.copy(
                    labels = data.labels.dropLast(1),
                    dataSets = data.dataSets.map { it.copy(values = it.values.dropLast(1)) },
                )
            }
        }
    )
}

private fun createEntry(random: Random, id: Int, size: Int): BarChartData.DataSet {
    return BarChartData.DataSet(
        label = "Series ${id + 1}",
        color = autoColor(id),
        values = generateList(random, size),
    )
}