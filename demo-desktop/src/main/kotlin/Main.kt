import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import pl.krystiankaniowski.composecharts.DemoApplication
import pl.krystiankaniowski.composecharts.DemoTheme

fun main() = application {

    val icon = painterResource("icon.png")

    Window(
        onCloseRequest = ::exitApplication,
        icon = icon,
        title = "Compose charts",
        content = { DemoTheme { DemoApplication() } },
    )
}