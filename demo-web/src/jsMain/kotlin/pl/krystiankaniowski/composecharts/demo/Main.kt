package pl.krystiankaniowski.composecharts.demo

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import org.jetbrains.skiko.wasm.onWasmReady
import pl.krystiankaniowski.composecharts.DemoApplication
import pl.krystiankaniowski.composecharts.DemoTheme

fun main() {

    onWasmReady {
        @OptIn(ExperimentalComposeUiApi::class)
        CanvasBasedWindow(
            title = "Compose charts Demo",
            canvasElementId = "ComposeTarget",
            content = { DemoTheme { DemoApplication() } },
        )
    }
}
