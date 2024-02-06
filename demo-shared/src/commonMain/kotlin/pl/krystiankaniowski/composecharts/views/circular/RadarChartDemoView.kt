package pl.krystiankaniowski.composecharts.views.circular

import androidx.compose.material.Text
import androidx.compose.runtime.*
import kotlinx.datetime.Clock
import pl.krystiankaniowski.composecharts.ChartScreen
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.circular.RadarChart
import pl.krystiankaniowski.composecharts.components.*
import kotlin.random.Random

@Suppress("MagicNumber", "LongMethod")
@Composable
fun RadarChartDemo() {

    var data by remember {
        val random = Random(Clock.System.now().toEpochMilliseconds())
        val size = 5
        mutableStateOf(
            RadarChart.Data(
                labels = buildList {
                    for (i in 0 until size) {
                        add("D$i")
                    }
                },
                entries = buildList {
                    for (i in 0 until 3) {
                        add(createEntry(random, i, size))
                    }
                },
            ),
        )
    }

    ChartScreen(
        chart = {
            RadarChart(
                data = data,
                title = { Text("Radar chart") },
            )
        },
        settings = {
            OptionRandomize { random ->
                data = data.copy(
                    entries = data.entries.map {
                        it.copy(
                            values = buildList {
                                for (i in it.values.indices) {
                                    add(random.nextFloat())
                                }
                            },
                        )
                    },
                )
            }
            OptionAddDataSet { random ->
                val newId = data.entries.size
                data = data.copy(
                    entries = data.entries + createEntry(random = random, id = newId, size = data.entries.first().values.size),
                )
            }
            OptionRemoveDataSet {
                data = data.copy(
                    entries = data.entries.dropLast(1),
                )
            }
            OptionAddData { random ->
                data = data.copy(
                    labels = data.labels + "D${data.entries.first().values.size + 1}",
                    entries = data.entries.map { it.copy(values = it.values + random.nextFloat()) },
                )
            }
            OptionRemoveData {
                data = data.copy(
                    labels = data.labels.dropLast(1),
                    entries = data.entries.map { it.copy(values = it.values.dropLast(1)) },
                )
            }
        },
    )
}

private fun createEntry(random: Random, id: Int, size: Int): RadarChart.Entry {
    return RadarChart.Entry(
        label = "Data ${id + 1}",
        color = autoColor(id),
        values = buildList {
            for (i in 0 until size) {
                add(random.nextFloat())
            }
        },
    )
}