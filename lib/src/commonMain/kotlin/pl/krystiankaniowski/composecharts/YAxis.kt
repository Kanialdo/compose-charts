package pl.krystiankaniowski.composecharts

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import pl.krystiankaniowski.composecharts.internal.PointMapper

fun Canvas.drawYAxisHelperLines(
    mapper: PointMapper,
    yHelpers: List<Float>,
    paint: Paint = Paint().apply {
        color = Color.LightGray
    }
) {
    yHelpers.forEach {
        drawLine(
            p1 = Offset(x = mapper.xDstMin, y = mapper.y(it)),
            p2 = Offset(x = mapper.xDstMax, y = mapper.y(it)),
            paint = paint,
        )
    }
}