package pl.krystiankaniowski.composecharts.views.components

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import kotlin.random.Random

@Composable
fun OptionRandomize(onClick: (Random) -> Unit) {
    val random = Random(System.currentTimeMillis())
    Button(
        onClick = { onClick(random) },
        content = { Text("Randomize") },
    )
}

@Composable
fun OptionAddData(onClick: (Random) -> Unit) {
    val random = Random(System.currentTimeMillis())
    Button(
        onClick = { onClick(random) },
        content = { Text("Add data") },
    )
}

@Composable
fun OptionRemoveData(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        content = { Text("Remove data") },
    )
}

@Composable
fun OptionAddDataSet(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        content = { Text("Add data set") },
    )
}

@Composable
fun OptionRemoveDataSet(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        content = { Text("Remove data set") },
    )
}