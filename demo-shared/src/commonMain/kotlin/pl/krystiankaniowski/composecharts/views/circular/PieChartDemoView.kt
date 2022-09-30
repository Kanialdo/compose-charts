package pl.krystiankaniowski.composecharts.views.circular

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import pl.krystiankaniowski.composecharts.ChartScreen
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.circular.PieChart
import pl.krystiankaniowski.composecharts.components.OptionAddData
import pl.krystiankaniowski.composecharts.components.OptionRandomize
import pl.krystiankaniowski.composecharts.components.OptionRemoveData

@Suppress("MagicNumber")
@Composable
fun PieChartDemo() {

    var data by remember {
        mutableStateOf(
            PieChart.Data(
                slices = listOf(
                    createSlice(0, 0.1f),
                    createSlice(1, 0.2f),
                    createSlice(2, 0.5f),
                ),
            ),
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
        },
    )
}

private fun createSlice(id: Int, value: Float) = PieChart.Slice(label = "Data ${id + 1}", color = autoColor(id), value = value)