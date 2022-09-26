package pl.krystiankaniowski.composecharts

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChartScreen(
    chart: @Composable () -> Unit,
    settings: @Composable () -> Unit,
) {
    Column {
        Box(
            modifier = Modifier.weight(1f).padding(16.dp),
            content = { chart() },
        )
        Divider(modifier = Modifier.fillMaxWidth().height(1.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(space = 16.dp, alignment = Alignment.CenterHorizontally),
            content = { settings() },
        )
    }
}