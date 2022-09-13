package pl.krystiankaniowski.composecharts.views.components

import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import kotlin.random.Random

@Composable
fun OptionRandomize(onClick: (Random) -> Unit) {
    val random = Random(System.currentTimeMillis())
    Button(
        onClick = { onClick(random) },
        // content = { Text("Randomize") },
        content = { Icon(imageVector = Icons.Default.Casino, contentDescription = "Randomize") },
    )
}

@Composable
fun OptionAddData(onClick: (Random) -> Unit) {
    val random = Random(System.currentTimeMillis())
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
        content = { Icon(imageVector = Icons.Default.Remove, contentDescription = "Remove data") },
    )
}

@Composable
fun OptionAddDataSet(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        // content = { Text("Add data set") },
        content = { Icon(imageVector = Icons.Default.PlaylistAdd, contentDescription = "Add data set") },
    )
}

@Composable
fun OptionRemoveDataSet(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        // content = { Text("Remove data set") },
        content = { Icon(imageVector = Icons.Default.PlaylistRemove, contentDescription = "Remove data set") },
    )
}