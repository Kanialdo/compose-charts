package pl.krystiankaniowski.composecharts.views.circular

import androidx.compose.material.Text
import androidx.compose.runtime.*
import pl.krystiankaniowski.composecharts.ChartScreen
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.circular.PieChart
import pl.krystiankaniowski.composecharts.circular.PieChartData
import pl.krystiankaniowski.composecharts.components.OptionAddData
import pl.krystiankaniowski.composecharts.components.OptionRandomize
import pl.krystiankaniowski.composecharts.components.OptionRemoveData

@Suppress("MagicNumber")
@Composable
fun PieChartDemo() {

    var data by remember {
        mutableStateOf(
            PieChartData(
                slices = listOf(
                    createSlice(0, 0.1f),
                    createSlice(1, 0.2f),
                    createSlice(2, 0.5f),
                ),
            )
        )
    }

    ChartScreen(
        chart = {
            PieChart(
                data = data,
                title = { Text("Pie chart") },
            )
        },
        settings = {
            OptionRandomize { random ->
                data = data.copy(slices = data.slices.map { it.copy(value = random.nextFloat()) })
            }
            OptionAddData { random ->
                val newId = data.slices.size
                data = data.copy(slices = data.slices + createSlice(id = newId, value = random.nextFloat()))
            }
            OptionRemoveData {
                data = data.copy(slices = data.slices.dropLast(1))
            }
        }
    )
}

private fun createSlice(id: Int, value: Float) = PieChartData.Slice(label = "Data ${id + 1}", color = autoColor(id), value = value)