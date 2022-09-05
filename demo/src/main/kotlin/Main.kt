import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import pl.krystiankaniowski.composecharts.DemoView

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose charts",
    ) {
        MaterialTheme(
            colors = if (isSystemInDarkTheme()) darkColors() else lightColors()
        ) {
            DemoView()
        }
    }
}