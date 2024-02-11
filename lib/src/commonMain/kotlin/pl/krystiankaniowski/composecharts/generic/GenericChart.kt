//package pl.krystiankaniowski.composecharts.generic
//
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import pl.krystiankaniowski.composecharts.axis.XAxis
//import pl.krystiankaniowski.composecharts.axis.YAxis
//
//data class ChartData(
//    val items: List<ChartSeries>,
//) {
//    sealed interface ChartSeries {
//        data class Point(val values: List<Float>) : ChartSeries
//        data class Line(val values: List<Float>) : ChartSeries
//        data class Area(val values: List<Float>) : ChartSeries
//        data class Bars(val values: List<Float>) : ChartSeries
//    }
//}
//
//@Composable
//fun GenericChart(
//    modifier: Modifier = Modifier,
//    data: ChartData,
//    xAxisLine: XAxis.XAxisLine,
//    yAxisLine: YAxis.YAxisLine,
//) {
//}
//
//@Composable
//fun test() {
//
//    GenericChart(
//        data = ChartData(
//            items = listOf(
//                ChartData.ChartSeries.Line(
//                    values = listOf(1f, 2f, 3f, 4f, 5f),
//                ),
//                ChartData.ChartSeries.Bars(
//                    values = listOf(1f, 2f, 3f, 4f, 5f),
//                ),
//            ),
//        ),
//        xAxisLine = XAxis.Default(),
//        yAxisLine = YAxis.Default,
//    )
//}
//
////
////
////plot(weatherData) { // Begin plotting
////    x("time") // Set x-axis with time data
////    y("temperature") { // Set y-axis with temperature data
////        // Define scale for temperature (y-axis)
////        scale = continuous(0.0..25.5)
////    }
////
////    bars { // Add a bar layer
////        fillColor("humidity") { // Customizing bar colors based on humidity
////            // Setting the color range
////            scale = continuous(range = Color.YELLOW..Color.RED)
////        }
////        borderLine.width = 0.0 // Define border line width
////    }
////
////    line {
////        width = 3.0 // Set line width
////        color = Color.hex("#6e5596") // Define line color
////        type = LineType.DOTDASH // Specify the line type
////    }
////
////    layout { // Set plot layout
////        title = "Simple plot with kandy-lets-plot" // Add title
////        // Add caption
////        caption = "See `examples` section for more\n complicated and interesting examples!"
////        size = 700 to 450 // Plot dimension settings
////    }
////}