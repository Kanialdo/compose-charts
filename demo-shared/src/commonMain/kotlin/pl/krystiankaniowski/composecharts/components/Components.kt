package pl.krystiankaniowski.composecharts.components

import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import kotlinx.datetime.Clock
import pl.krystiankaniowski.composecharts.icons.AddDataSet
import pl.krystiankaniowski.composecharts.icons.Randomize
import pl.krystiankaniowski.composecharts.icons.Remove
import pl.krystiankaniowski.composecharts.icons.RemoveDataSet
import kotlin.random.Random

@Composable
fun OptionRandomize(onClick: (Random) -> Unit) {
    val random = Random(Clock.System.now().toEpochMilliseconds())
    Button(
        onClick = { onClick(random) },
        // content = { Text("Randomize") },
        content = { Icon(imageVector = Icons.Randomize, contentDescription = "Randomize") },
    )
}

@Composable
fun OptionAddData(onClick: (Random) -> Unit) {
    val random = Random(Clock.System.now().toEpochMilliseconds())
    Button(
        onClick = { onClick(random) },
        // content = { Text("Add data") },
        content = { Icon(imageVector = Icons.Default.Add, contentDescription = "Add data") },
    )
}

@Composable
fun OptionRemoveData(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        // content = { Text("Remove data") },
        content = { Icon(imageVector = Icons.Remove, contentDescription = "Remove data") },
    )
}

@Composable
fun OptionAddDataSet(onClick: (Random) -> Unit) {
    val random = Random(Clock.System.now().toEpochMilliseconds())
    Button(
        onClick = { onClick(random) },
        // content = { Text("Add data set") },
        content = { Icon(imageVector = Icons.AddDataSet, contentDescription = "Add data set") },
    )
}

@Composable
fun OptionRemoveDataSet(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        // content = { Text("Remove data set") },
        content = { Icon(imageVector = Icons.RemoveDataSet, contentDescription = "Remove data set") },
    )
}