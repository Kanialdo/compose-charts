package pl.krystiankaniowski.composecharts.data

import androidx.compose.ui.graphics.Color

data class DataFrame(
    val labels: List<String>,
    val entries: List<DataSet>,
) {
    val minValue by lazy { entries.minOf { it.values.min() } }
    val maxValue by lazy { entries.maxOf { it.values.max() } }
    val maxStacked by lazy { List(labels.size) { index -> entries.sumOf { it.values[index].toDouble() } }.max() }

    fun getStackedValue(index: Int, number: Int) = entries.map { it.values[index] }.take(number).sum()
}

data class DataFrameOneDimension(
    val entries: List<DataValue>,
) {
    val minValue by lazy { entries.minOf { it.value } }
    val maxValue by lazy { entries.maxOf { it.value } }
}

data class DataSet(
    override val label: String,
    override val color: ChartColor,
    val values: List<Float>,
) : Series

data class DataValue(
    override val label: String,
    override val color: ChartColor,
    val value: Float,
) : Series

// it will work for column, bar, line and area

fun test() {
    DataFrame(
        labels = listOf("A", "B", "C"),
        entries = listOf(
            DataSet(label = "Line 1", values = listOf(1f, 2f, 3f), color = ChartColor.Solid(Color.Red)),
        ),
    )
}

fun test2() {
    DataFrameOneDimension(
        entries = listOf(
            DataValue(label = "Slice 1", value = 1f, color = ChartColor.Solid(Color.Red)),
            DataValue(label = "Slice 2", value = 1f, color = ChartColor.Solid(Color.Gray)),
            DataValue(label = "Slice 3", value = 1f, color = ChartColor.Solid(Color.Blue)),
        ),
    )
}