package pl.krystiankaniowski.composecharts.views.circular

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import pl.krystiankaniowski.composecharts.ChartScreen
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.circular.PolarChart
import pl.krystiankaniowski.composecharts.components.OptionAddData
import pl.krystiankaniowski.composecharts.components.OptionRandomize
import pl.krystiankaniowski.composecharts.components.OptionRemoveData

@Suppress("MagicNumber")
@Composable
fun PolarChartDemo() {

    var data by remember {
        mutableStateOf(
            PolarChart.Data(
                entries = listOf(
                    createEntry(0, 0.1f),
                    createEntry(1, 0.2f),
                    createEntry(2, 0.5f),
                ),
            ),
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
        },
    )
}

private fun createEntry(id: Int, value: Float) = PolarChart.Entry(label = "Data ${id + 1}", color = autoColor(id), value = value)