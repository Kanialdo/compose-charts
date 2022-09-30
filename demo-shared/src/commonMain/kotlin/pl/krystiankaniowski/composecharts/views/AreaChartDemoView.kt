package pl.krystiankaniowski.composecharts.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import pl.krystiankaniowski.composecharts.ChartScreen
import pl.krystiankaniowski.composecharts.area.AreaChart
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.components.OptionAddData
import pl.krystiankaniowski.composecharts.components.OptionAddDataSet
import pl.krystiankaniowski.composecharts.components.OptionRandomize
import pl.krystiankaniowski.composecharts.components.OptionRemoveData
import pl.krystiankaniowski.composecharts.components.OptionRemoveDataSet
import pl.krystiankaniowski.composecharts.utils.generateList
import pl.krystiankaniowski.composecharts.utils.randomize
import kotlin.random.Random

@Composable
fun AreaOverlappingChartDemo() {
    AreaRawChartDemo(
        title = "Area Overlapping Chart",
        mode = AreaChart.Style.OVERLAPPING,
    )
}

@Composable
fun AreaStackedChartDemo() {
    AreaRawChartDemo(
        title = "Area Stacked Chart",
        mode = AreaChart.Style.STACKED,
    )
}

@Composable
fun AreaProportionalChartDemo() {
    AreaRawChartDemo(
        title = "Area Proportional Chart",
        mode = AreaChart.Style.PROPORTIONAL,
    )
}

@Suppress("MagicNumber", "LongMethod")
@Composable
private fun AreaRawChartDemo(title: String, mode: AreaChart.Style) {

    var data by remember {
        val random = Random(System.currentTimeMillis())
        val size = 5
        mutableStateOf(
            AreaChart.Data(
                areas = listOf(
                    createEntry(random, 0, size),
                    createEntry(random, 1, size),
                    createEntry(random, 2, size),
                ),
            ),
        )
    }

    ChartScreen(
        chart = {
            AreaChart(
                data = data,
                title = { Text(title) },
                style = mode,
            )
        },
        settings = {
            OptionRandomize { random ->
                data = data.copy(
                    areas = data.areas.map {
                        it.copy(values = it.values.randomize(random))
                    },
                )
            }
            OptionAddDataSet { random ->
                val newId = data.areas.size
                data = data.copy(areas = data.areas + createEntry(random = random, id = newId, size = data.areas.first().values.size))
            }
            OptionRemoveDataSet {
                data = data.copy(areas = data.areas.dropLast(1))
            }
            OptionAddData { random ->
                data = data.copy(areas = data.areas.map { it.copy(values = it.values + random.nextFloat()) })
            }
            OptionRemoveData {
                data = data.copy(areas = data.areas.map { it.copy(values = it.values.dropLast(1)) })
            }
        },
    )
}

private fun createEntry(random: Random, id: Int, size: Int): AreaChart.Area {
    return AreaChart.Area(
        label = "Data ${id + 1}",
        color = autoColor(id),
        values = generateList(random, size),
    )
}