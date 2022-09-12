package pl.krystiankaniowski.composecharts.views.circular

import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.krystiankaniowski.composecharts.ChartScreen
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.circular.DoughnutChart
import pl.krystiankaniowski.composecharts.circular.DoughnutChartData
import pl.krystiankaniowski.composecharts.views.components.OptionAddData
import pl.krystiankaniowski.composecharts.views.components.OptionRandomize
import pl.krystiankaniowski.composecharts.views.components.OptionRemoveData


@Suppress("MagicNumber")
@Composable
fun DoughnutChartDemo() {

    var cutOut by remember { mutableStateOf(0.5f) }

    var data by remember {
        mutableStateOf(
            DoughnutChartData(
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
            DoughnutChart(
                data = data,
                cutOut = cutOut,
                title = { Text("Doughnut chart") },
            )
        },
        settings = {
            Slider(modifier = Modifier.widthIn(max = 120.dp), value = cutOut, valueRange = 0.1f..0.9f, onValueChange = { cutOut = it })
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

private fun createSlice(id: Int, value: Float) = DoughnutChartData.Slice(label = "Data ${id + 1}", color = autoColor(id), value = value)