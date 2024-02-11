package pl.krystiankaniowski.composecharts.axis

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import pl.krystiankaniowski.composecharts.internal.PointMapper

data class Axis(
    val title: String? = null,
    val values: Values = Values.Dynamic,
    val mapper: (Float) -> String = { it.toString() },
    val style: Style = Style(),
    val gridlinesStyle: Style = Style(),
) {

    val minValue = when (values) {
        is Values.Dynamic -> null
        is Values.Fixed -> values.values.min()
        is Values.Regions -> values.values.minOf { it.first }
    }

    val maxValue = when (values) {
        is Values.Dynamic -> null
        is Values.Fixed -> values.values.max()
        is Values.Regions -> values.values.maxOf { it.second }
    }

    data class Style(
        val isEnabled: Boolean = true,
        val strokeWidth: Float = Stroke.HairlineWidth,
        val cap: StrokeCap = Stroke.DefaultCap,
        val color: Color = Color.Unspecified,
        val alpha: Float = 1.0f,
        val pathEffect: PathEffect? = PathEffect.dashPathEffect(floatArrayOf(10f, 20f)),
        val colorFilter: ColorFilter? = null,
        val blendMode: BlendMode = DrawScope.DefaultBlendMode,
        val textSize: TextUnit = 16.sp,
    )

    sealed interface Values {
        data object Dynamic : Values
        data class Fixed(val values: List<Float>) : Values
        data class Regions(val values: List<Pair<Float, Float>>) : Values
    }
}

fun DrawScope.drawAxisX(pointMapper: PointMapper, axis: Axis) {

    if (axis.style.isEnabled) {
        drawLine(
            color = axis.style.color,
            start = Offset(pointMapper.xDstMin, pointMapper.yDstMin),
            end = Offset(pointMapper.xDstMax, pointMapper.yDstMin),
        )
    }
    if(axis.gridlinesStyle.isEnabled){
        when(axis.values){
            Axis.Values.Dynamic -> TODO()
            is Axis.Values.Fixed -> TODO()
            is Axis.Values.Regions -> TODO()
        }
        drawLine(
            color = axis.style.color,
            start = Offset(pointMapper.xDstMin, pointMapper.yDstMin),
            end = Offset(pointMapper.xDstMax, pointMapper.yDstMin),
        )
    }

}
