@file:Suppress("MatchingDeclarationName")

package pl.krystiankaniowski.composecharts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import pl.krystiankaniowski.composecharts.views.*
import pl.krystiankaniowski.composecharts.views.circular.*
import pl.krystiankaniowski.composecharts.views.line.LineChart2Demo
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
            MenuEntry.MenuItem(title = "Line 2", screen = { LineChart2Demo() }),
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
        text = {
            Text(
                text = menuItem.title,
                color = if (currentMenuItem == menuItem) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface,
            )
        },
    )
}
