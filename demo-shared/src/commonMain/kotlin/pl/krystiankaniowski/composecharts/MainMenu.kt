@file:Suppress("MatchingDeclarationName")
package pl.krystiankaniowski.composecharts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AreaChart
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.BubbleChart
import androidx.compose.material.icons.filled.Feed
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import pl.krystiankaniowski.composecharts.views.AreaOverlappingChartDemo
import pl.krystiankaniowski.composecharts.views.AreaProportionalChartDemo
import pl.krystiankaniowski.composecharts.views.AreaStackedChartDemo
import pl.krystiankaniowski.composecharts.views.BarGroupedChartDemo
import pl.krystiankaniowski.composecharts.views.BarProportionalChartDemo
import pl.krystiankaniowski.composecharts.views.BarStackedChartDemo
import pl.krystiankaniowski.composecharts.views.ColumnGroupedChartDemo
import pl.krystiankaniowski.composecharts.views.ColumnProportionalChartDemo
import pl.krystiankaniowski.composecharts.views.ColumnStackedChartDemo
import pl.krystiankaniowski.composecharts.views.LegendDemoView
import pl.krystiankaniowski.composecharts.views.LegendFlowDemoView
import pl.krystiankaniowski.composecharts.views.PointChartCustomDemo
import pl.krystiankaniowski.composecharts.views.PointChartDemo
import pl.krystiankaniowski.composecharts.views.circular.DoughnutChartDemo
import pl.krystiankaniowski.composecharts.views.circular.PieChartDemo
import pl.krystiankaniowski.composecharts.views.circular.PolarChartDemo
import pl.krystiankaniowski.composecharts.views.circular.RadarChartDemo
import pl.krystiankaniowski.composecharts.views.circular.SunburstChartDemo
import pl.krystiankaniowski.composecharts.views.line.LineChartCustomDemo
import pl.krystiankaniowski.composecharts.views.line.LineChartDemo

sealed interface MenuEntry {
    data class MenuItem(val title: String, val screen: @Composable () -> Unit) : MenuEntry
    data class Section(val icon: ImageVector, val title: String, val items: List<MenuItem>) : MenuEntry
}

val menuItems = listOf<MenuEntry>(
    MenuEntry.Section(
        icon = Icons.Default.BarChart,
        title = "Column",
        items = listOf(
            MenuEntry.MenuItem(title = "Column grouped", screen = { ColumnGroupedChartDemo() }),
            MenuEntry.MenuItem(title = "Column stacked", screen = { ColumnStackedChartDemo() }),
            MenuEntry.MenuItem(title = "Column proportional", screen = { ColumnProportionalChartDemo() }),
        ),
    ),
    MenuEntry.Section(
        icon = Icons.Default.Sort,
        title = "Bar",
        items = listOf(
            MenuEntry.MenuItem(title = "Bar grouped", screen = { BarGroupedChartDemo() }),
            MenuEntry.MenuItem(title = "Bar stacked", screen = { BarStackedChartDemo() }),
            MenuEntry.MenuItem(title = "Bar proportional", screen = { BarProportionalChartDemo() }),
        ),
    ),
    MenuEntry.Section(
        icon = Icons.Default.ShowChart,
        title = "Line",
        items = listOf(
            MenuEntry.MenuItem(title = "Line", screen = { LineChartDemo() }),
            MenuEntry.MenuItem(title = "Line custom", screen = { LineChartCustomDemo() }),
        ),
    ),
    MenuEntry.Section(
        icon = Icons.Default.BubbleChart,
        title = "Point",
        items = listOf(
            MenuEntry.MenuItem(title = "Point chart", screen = { PointChartDemo() }),
            MenuEntry.MenuItem(title = "Point chart custom", screen = { PointChartCustomDemo() }),
        ),
    ),
    MenuEntry.Section(
        icon = Icons.Default.AreaChart,
        title = "Area",
        items = listOf(
            MenuEntry.MenuItem(title = "Area overlapping", screen = { AreaOverlappingChartDemo() }),
            MenuEntry.MenuItem(title = "Area stacked", screen = { AreaStackedChartDemo() }),
            MenuEntry.MenuItem(title = "Area proportional", screen = { AreaProportionalChartDemo() }),
        ),
    ),
    MenuEntry.Section(
        icon = Icons.Default.PieChart,
        title = "Circular",
        items = listOf(
            MenuEntry.MenuItem(title = "Pie", screen = { PieChartDemo() }),
            MenuEntry.MenuItem(title = "Doughnut", screen = { DoughnutChartDemo() }),
            MenuEntry.MenuItem(title = "Sunburst", screen = { SunburstChartDemo() }),
            MenuEntry.MenuItem(title = "Polar", screen = { PolarChartDemo() }),
            MenuEntry.MenuItem(title = "Radar", screen = { RadarChartDemo() }),
        ),
    ),
    MenuEntry.Section(
        icon = Icons.Default.Feed,
        title = "Legend",
        items = listOf(
            MenuEntry.MenuItem(title = "Legend", screen = { LegendDemoView() }),
            MenuEntry.MenuItem(title = "Legend Flow", screen = { LegendFlowDemoView() }),
        ),
    ),
)

fun List<MenuEntry>.firstMenuItem(): MenuEntry.MenuItem = first().let {
    when (it) {
        is MenuEntry.MenuItem -> it
        is MenuEntry.Section -> it.items.first()
    }
}

@Composable
fun ColumnScope.MenuEntry(menuEntry: MenuEntry, currentMenuItem: MenuEntry.MenuItem?, onMenuItemClick: (MenuEntry.MenuItem) -> Unit) {
    when (menuEntry) {
        is MenuEntry.MenuItem -> MenuItem(menuItem = menuEntry, currentMenuItem = currentMenuItem, onMenuItemClick = onMenuItemClick)
        is MenuEntry.Section -> Section(section = menuEntry, currentMenuItem = currentMenuItem, onMenuItemClick = onMenuItemClick)
    }
}

@Composable
fun ColumnScope.Section(section: MenuEntry.Section, currentMenuItem: MenuEntry.MenuItem?, onMenuItemClick: (MenuEntry.MenuItem) -> Unit) {

    var isExpanded by remember { mutableStateOf(currentMenuItem in section.items) }

    Row(
        modifier = Modifier
            .heightIn(min = 48.dp)
            .clickable { isExpanded = !isExpanded }
            .fillMaxSize()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = section.icon,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = section.title,
            style = typography.subtitle1,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            imageVector = if (isExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
            contentDescription = null,
        )
    }
    if (isExpanded) {
        Column(modifier = Modifier.background(Color.Gray.copy(alpha = 0.05f))) {
            section.items.forEach { MenuItem(menuItem = it, currentMenuItem = currentMenuItem, onMenuItemClick = onMenuItemClick) }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MenuItem(menuItem: MenuEntry.MenuItem, currentMenuItem: MenuEntry.MenuItem?, onMenuItemClick: (MenuEntry.MenuItem) -> Unit) {
    ListItem(
        modifier = Modifier.clickable { onMenuItemClick(menuItem) },
        text = { Text(text = menuItem.title, color = if (currentMenuItem == menuItem) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface) },
    )
}
