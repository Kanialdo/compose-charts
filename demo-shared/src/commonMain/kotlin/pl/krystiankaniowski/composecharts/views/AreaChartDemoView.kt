package pl.krystiankaniowski.composecharts.views

import androidx.compose.material.Text
import androidx.compose.runtime.*
import pl.krystiankaniowski.composecharts.ChartScreen
import pl.krystiankaniowski.composecharts.area.AreaChart
import pl.krystiankaniowski.composecharts.area.AreaChartData
import pl.krystiankaniowski.composecharts.area.AreaChartMode
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.components.*
import pl.krystiankaniowski.composecharts.utils.generateList
import pl.krystiankaniowski.composecharts.utils.randomize
import kotlin.random.Random

@Composable
fun AreaOverlappingChartDemo() {
    AreaRawChartDemo(
        title = "Area Overlapping Chart",
        mode = AreaChartMode.STANDARD,
    )
}

@Composable
fun AreaStackedChartDemo() {
    AreaRawChartDemo(
        title = "Area Stacked Chart",
        mode = AreaChartMode.STACKED,
    )
}

@Composable
fun AreaProportionalChartDemo() {
    AreaRawChartDemo(
        title = "Area Proportional Chart",
        mode = AreaChartMode.PROPORTIONAL,
    )
}

@Suppress("MagicNumber", "LongMethod")
@Composable
private fun AreaRawChartDemo(title: String, mode: AreaChartMode) {

    var data by remember {
        val random = Random(System.currentTimeMillis())
        val size = 5
        mutableStateOf(
            AreaChartData(
                lines = listOf(
                    createEntry(random, 0, size),
                    createEntry(random, 1, size),
                    createEntry(random, 2, size),
                ),
            )
        )
    }

    ChartScreen(
        chart = {
            AreaChart(
                data = data,
                title = { Text(title) },
                mode = mode,
            )
        },
        settings = {
            OptionRandomize { random ->
                data = data.copy(
                    lines = data.lines.map {
                        it.copy(values = it.values.randomize(random))
                    },
                )
            }
            OptionAddDataSet { random ->
                val newId = data.lines.size
                data = data.copy(lines = data.lines + createEntry(random = random, id = newId, size = data.lines.first().values.size))
            }
            OptionRemoveDataSet {
                data = data.copy(lines = data.lines.dropLast(1))
            }
            OptionAddData { random ->
                data = data.copy(lines = data.lines.map { it.copy(values = it.values + random.nextFloat()) })
            }
            OptionRemoveData {
                data = data.copy(lines = data.lines.map { it.copy(values = it.values.dropLast(1)) })
            }
        }
    )
}

private fun createEntry(random: Random, id: Int, size: Int): AreaChartData.Area {
    return AreaChartData.Area(
        label = "Data ${id + 1}",
        color = autoColor(id),
        values = generateList(random, size),
    )
}