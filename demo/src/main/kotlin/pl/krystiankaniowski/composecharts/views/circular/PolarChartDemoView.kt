package pl.krystiankaniowski.composecharts.views.circular

import androidx.compose.material.Text
import androidx.compose.runtime.*
import pl.krystiankaniowski.composecharts.ChartScreen
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.circular.PolarChart
import pl.krystiankaniowski.composecharts.circular.PolarChartData
import pl.krystiankaniowski.composecharts.views.components.OptionAddData
import pl.krystiankaniowski.composecharts.views.components.OptionRandomize
import pl.krystiankaniowski.composecharts.views.components.OptionRemoveData

@Suppress("MagicNumber")
@Composable
fun PolarChartDemo() {

    var data by remember {
        mutableStateOf(
            PolarChartData(
                entries = listOf(
                    createEntry(0, 0.1f),
                    createEntry(1, 0.2f),
                    createEntry(2, 0.5f),
                ),
            )
        )
    }

    ChartScreen(
        chart = {
            PolarChart(
                data = data,
                title = { Text("Polar chart") },
            )
        },
        settings = {
            OptionRandomize { random ->
                data = data.copy(entries = data.entries.map { it.copy(value = random.nextFloat()) })
            }
            OptionAddData { random ->
                val newId = data.entries.size
                data = data.copy(entries = data.entries + createEntry(id = newId, value = random.nextFloat()))
            }
            OptionRemoveData {
                data = data.copy(entries = data.entries.dropLast(1))
            }
        }
    )
}

private fun createEntry(id: Int, value: Float) = PolarChartData.Entry(label = "Data ${id + 1}", color = autoColor(id), value = value)