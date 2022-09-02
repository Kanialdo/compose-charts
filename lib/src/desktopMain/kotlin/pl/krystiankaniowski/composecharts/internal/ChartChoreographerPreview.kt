package pl.krystiankaniowski.composecharts.internal

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pl.krystiankaniowski.composecharts.legend.LegendPosition

@Preview
@Composable
private fun ChartChoreographerPreview() {
    ChartChoreographer(
        modifier = Modifier.padding(16.dp),
        title = { Text("Chart title") },
        legend = {
            Box(
                modifier = Modifier.border(1.dp, color = Color.LightGray),
                contentAlignment = Alignment.Center,
                content = { Text("Legend") },
            )
        },
        legendPosition = LegendPosition.Bottom,
        chart = {
            Box(
                modifier = Modifier.border(1.dp, color = Color.Gray).fillMaxSize(),
                contentAlignment = Alignment.Center,
                content = { Text("Chart") },
            )
        }
    )
}

@Preview
@Composable
private fun ChartChoreographerNoTitlePreview() {
    ChartChoreographer(
        title = { },
        legend = {
            Box(
                modifier = Modifier.border(1.dp, color = Color.LightGray),
                contentAlignment = Alignment.Center,
                content = { Text("Legend") },
            )
        },
        legendPosition = LegendPosition.Bottom,
        chart = {
            Box(
                modifier = Modifier.border(1.dp, color = Color.Gray).fillMaxSize(),
                contentAlignment = Alignment.Center,
                content = { Text("Chart") },
            )
        }
    )
}