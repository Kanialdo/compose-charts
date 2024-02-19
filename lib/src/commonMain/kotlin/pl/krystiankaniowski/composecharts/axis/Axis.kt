package pl.krystiankaniowski.composecharts.axis

import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class Axis(
    val title: String? = null,
    val values: Values = Values.Auto,
    val mapper: (Float) -> String = { it.toString() },
    val style: Style = Style(),
    val gridlinesStyle: Style = Style().copy(
        strokeWidth = Stroke.HairlineWidth,
        color = Color.Gray.copy(alpha = 0.5f),
    ),
) {

    data class Style(
        val isEnabled: Boolean = true,
        val strokeWidth: Float = 2f,
        val cap: StrokeCap = Stroke.DefaultCap,
        val color: Color = Color.Gray,
        val alpha: Float = 1.0f,
        val pathEffect: PathEffect? = PathEffect.dashPathEffect(floatArrayOf(10f, 20f)),
        val colorFilter: ColorFilter? = null,
        val blendMode: BlendMode = DrawScope.DefaultBlendMode,
        val textSize: TextUnit = 12.sp,
    )

    sealed interface Values {
        data object Auto : Values
        data class Fixed(val values: List<Float>) : Values
    }
}

data class AxisLabel(
    val label: String,
    val value: Float,
)
