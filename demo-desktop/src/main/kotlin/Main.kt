import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import pl.krystiankaniowski.composecharts.DemoApplication
import pl.krystiankaniowski.composecharts.DemoTheme

fun main() = application {

    val icon = painterResource("icon.png")

    Window(
        state = WindowState(
            width = 1024.dp,
            height = 768.dp,
        ),
        onCloseRequest = ::exitApplication,
        icon = icon,
        title = "Compose charts",
        content = { DemoTheme { DemoApplication() } },
    )
}