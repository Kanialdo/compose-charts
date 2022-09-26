package pl.krystiankaniowski.composecharts.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import pl.krystiankaniowski.composecharts.DemoApplication
import pl.krystiankaniowski.composecharts.DemoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DemoTheme {
                DemoApplication()
            }
        }
    }
}