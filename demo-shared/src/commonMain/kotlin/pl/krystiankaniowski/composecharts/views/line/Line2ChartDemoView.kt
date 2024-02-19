package pl.krystiankaniowski.composecharts.views.line

import androidx.compose.material.Text
import androidx.compose.runtime.*
import kotlinx.datetime.Clock
import pl.krystiankaniowski.composecharts.ChartScreen
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.components.*
import pl.krystiankaniowski.composecharts.line.LineChart2
import pl.krystiankaniowski.composecharts.utils.generateList
import pl.krystiankaniowski.composecharts.utils.randomize
import kotlin.random.Random

@Suppress("MagicNumber", "LongMethod")
@Composable
fun LineChart2Demo() {

    var data by remember {
        val random = Random(Clock.System.now().toEpochMilliseconds())
        val size = 5
        mutableStateOf(
            LineChart2.Data(
                lines = listOf(
                    createEntry(random, 0, size),
                    createEntry(random, 1, size),
                    createEntry(random, 2, size),
                ),
            ),
        )
    }

    ChartScreen(
        chart = {
            LineChart2(
                data = data,
                title = { Text("Line chart") },
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
        },
    )
}

private fun createEntry(random: Random, id: Int, size: Int): LineChart2.Line {
    return LineChart2.Line(
        label = "Data ${id + 1}",
        color = autoColor(id),
        values = generateList(random, size),
    )
}