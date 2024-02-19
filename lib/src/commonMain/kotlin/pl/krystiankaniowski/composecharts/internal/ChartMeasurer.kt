package pl.krystiankaniowski.composecharts.internal

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextMeasurer
import pl.krystiankaniowski.composecharts.axis.XAxisDrawer
import pl.krystiankaniowski.composecharts.axis.YAxisDrawer

class ChartMeasurer(
    drawScope: DrawScope,
    val textMeasurer: TextMeasurer,
    xAxisDrawer: XAxisDrawer,
    yAxisDrawer: YAxisDrawer,
    minX: Float,
    maxX: Float,
    minY: Float,
    maxY: Float,
) {

    val contentArea = Rect(
        top = 0f,
        bottom = drawScope.size.height - xAxisDrawer.measureHeight(textMeasurer),
        left = yAxisDrawer.measureWidth(textMeasurer),
        right = drawScope.size.width,
    )
    val xAxisArea = Rect(
        top = contentArea.bottom,
        bottom = drawScope.size.height,
        left = contentArea.left,
        right = contentArea.right,
    )
    val yAxisArea = Rect(
        top = contentArea.top,
        bottom = contentArea.bottom,
        left = 0f,
        right = contentArea.left,
    )

    val mapper = PointMapper(
        xSrcMin = minX,
        xSrcMax = maxX,
        xDstMin = contentArea.left,
        xDstMax = contentArea.right,
        ySrcMin = minY,
        ySrcMax = maxY,
        yDstMin = contentArea.top,
        yDstMax = contentArea.bottom,
    )
}