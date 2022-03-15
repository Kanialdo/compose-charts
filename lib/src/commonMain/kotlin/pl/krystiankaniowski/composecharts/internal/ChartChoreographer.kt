package pl.krystiankaniowski.composecharts.internal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.krystiankaniowski.composecharts.legend.LegendPosition

@Composable
internal fun ChartChoreographer(
    title: @Composable () -> Unit,
    legend: @Composable () -> Unit,
    legendPosition: LegendPosition,
    chart: @Composable () -> Unit,
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
            content = { title() },
        )
        if (legendPosition == LegendPosition.Top) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
                content = { legend() },
            )
        }
        Box(modifier = Modifier.weight(1f).fillMaxWidth().padding(16.dp)) {
            chart()
        }
        if (legendPosition == LegendPosition.Bottom) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
                content = { legend() },
            )
        }
    }
}