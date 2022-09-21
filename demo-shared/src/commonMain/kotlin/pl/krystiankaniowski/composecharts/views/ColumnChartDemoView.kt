package pl.krystiankaniowski.composecharts.views

import androidx.compose.material.Text
import androidx.compose.runtime.*
import pl.krystiankaniowski.composecharts.ChartScreen
import pl.krystiankaniowski.composecharts.autoColor
import pl.krystiankaniowski.composecharts.column.ColumnChart
import pl.krystiankaniowski.composecharts.column.ColumnChartData
import pl.krystiankaniowski.composecharts.column.ColumnChartStyle
import pl.krystiankaniowski.composecharts.components.*
import pl.krystiankaniowski.composecharts.utils.generateList
import pl.krystiankaniowski.composecharts.utils.randomize
import kotlin.random.Random

@Composable
fun ColumnGroupedChartDemo() {
    ColumnRawChartDemo(
        title = "Column Grouped Chart",
        style = ColumnChartStyle.GROUPED,
    )
}

@Composable
fun ColumnStackedChartDemo() {
    ColumnRawChartDemo(
        title = "Column Stacked Chart",
        style = ColumnChartStyle.STACKED,
    )
}

@Composable
fun ColumnProportionalChartDemo() {
    ColumnRawChartDemo(
        title = "Column Proportional Chart",
        style = ColumnChartStyle.PROPORTIONAL,
    )
}

@Suppress("MagicNumber", "LongMethod")
@Composable
private fun ColumnRawChartDemo(title: String, style: ColumnChartStyle) {

    var data by remember {
        val random = Random(System.currentTimeMillis())
        val size = 5
        mutableStateOf(
            ColumnChartData(
                labels = buildList {
                    for (i in 0 until size) {
                        add("C$i")
                    }
                },
                columns = listOf(
                    createEntry(random, 0, size),
                    createEntry(random, 1, size),
                    createEntry(random, 2, size),
                ),
            )
        )
    }

    ChartScreen(
        chart = {
            ColumnChart(
                data = data,
                title = { Text(title) },
                style = style,
            )
        },
        settings = {
            OptionRandomize { random ->
                data = data.copy(
                    columns = data.columns.map {
                        it.copy(values = it.values.randomize(random))
                    },
                )
            }
            OptionAddDataSet { random ->
                val newId = data.columns.size
                data = data.copy(columns = data.columns + createEntry(random = random, id = newId, size = data.columns.first().values.size))
            }
            OptionRemoveDataSet {
                data = data.copy(columns = data.columns.dropLast(1))
            }
            OptionAddData { random ->
                data = data.copy(
                    labels = data.labels + "C${data.labels.size}",
                    columns = data.columns.map { it.copy(values = it.values + random.nextFloat()) },
                )
            }
            OptionRemoveData {
                data = data.copy(
                    labels = data.labels.dropLast(1),
                    columns = data.columns.map { it.copy(values = it.values.dropLast(1)) },
                )
            }
        }
    )
}

private fun createEntry(random: Random, id: Int, size: Int): ColumnChartData.Column {
    return ColumnChartData.Column(
        label = "Series ${id + 1}",
        color = autoColor(id),
        values = generateList(random, size),
    )
}