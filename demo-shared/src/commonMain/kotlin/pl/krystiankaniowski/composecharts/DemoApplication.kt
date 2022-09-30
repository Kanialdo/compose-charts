package pl.krystiankaniowski.composecharts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.NavigationRailDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.min

@Composable
fun DemoApplication() {

    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    BoxWithConstraints {

        val mode = ScreenSize.resolveScreenSize(maxWidth)
        var menuItem by remember { mutableStateOf(menuItems.firstMenuItem()) }

        Row {
            when (mode) {
                ScreenSize.Medium, ScreenSize.Compact -> {}
                ScreenSize.Expanded -> SideMenu(
                    menu = menuItems,
                    selected = menuItem,
                    onSelect = { menuItem = it },
                )
            }
            Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    when (mode) {
                        ScreenSize.Expanded -> {}
                        ScreenSize.Medium, ScreenSize.Compact -> {
                            DemoTopBar(
                                menuItem = menuItem,
                                onToggleDrawerClick = {
                                    if (scaffoldState.drawerState.isOpen) {
                                        coroutineScope.launch { scaffoldState.drawerState.close() }
                                    } else {
                                        coroutineScope.launch { scaffoldState.drawerState.open() }
                                    }
                                },
                            )
                        }
                    }
                },
                drawerShape = DemoDrawerShape(),
                drawerGesturesEnabled = platform == Platform.Android,
                drawerContent = when (mode) {
                    ScreenSize.Expanded -> null
                    ScreenSize.Medium, ScreenSize.Compact -> {
                        {
                            SideMenu(
                                menu = menuItems,
                                selected = menuItem,
                                onSelect = {
                                    menuItem = it
                                    coroutineScope.launch { scaffoldState.drawerState.close() }
                                },
                            )
                        }
                    }
                },
                content = { Box(Modifier.padding(16.dp)) { menuItem.screen() } },
            )
        }
    }
}

@Composable
private fun DemoTopBar(
    menuItem: MenuEntry.MenuItem,
    onToggleDrawerClick: () -> Unit,
) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = onToggleDrawerClick,
                content = { Icon(imageVector = Icons.Default.Menu, contentDescription = null) },
            )
        },
        title = { Text(menuItem.title) },
    )
}

@Composable
private fun SideMenu(menu: List<MenuEntry>, selected: MenuEntry.MenuItem?, onSelect: (MenuEntry.MenuItem) -> Unit) {
    Surface(
        color = MaterialTheme.colors.surface,
        contentColor = contentColorFor(MaterialTheme.colors.surface),
        elevation = NavigationRailDefaults.Elevation,
    ) {
        Column(
            modifier = Modifier
                .width(DemoDrawerMaxWidth.dp)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
        ) {
            menu.forEach { MenuEntry(menuEntry = it, currentMenuItem = selected, onMenuItemClick = onSelect) }
        }
    }
}

private const val DemoDrawerMaxWidth = 250

private class DemoDrawerShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        return Outline.Rectangle(
            Rect(
                offset = Offset.Zero,
                size = Size(
                    width = min(size.width, DemoDrawerMaxWidth * density.density),
                    height = size.height,
                ),
            ),
        )
    }
}

enum class ScreenSize {
    Compact,
    Medium,
    Expanded;

    companion object {
        fun resolveScreenSize(width: Dp) = when {
            width < 600.dp -> Compact
            width < 840.dp -> Medium
            else -> Expanded
        }
    }
}