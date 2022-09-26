package pl.krystiankaniowski.composecharts

import androidx.compose.runtime.Composable
import pl.krystiankaniowski.composecharts.views.*
import pl.krystiankaniowski.composecharts.views.circular.*
import pl.krystiankaniowski.composecharts.views.line.LineChartCustomDemo
import pl.krystiankaniowski.composecharts.views.line.LineChartDemo

val menuItems = listOf(
    MenuItem(title = "Column grouped", screen = { ColumnGroupedChartDemo() }),
    MenuItem(title = "Column stacked", screen = { ColumnStackedChartDemo() }),
    MenuItem(title = "Column proportional", screen = { ColumnProportionalChartDemo() }),
    MenuItem(title = "Bar grouped", screen = { BarGroupedChartDemo() }),
    MenuItem(title = "Bar stacked", screen = { BarStackedChartDemo() }),
    MenuItem(title = "Bar proportional", screen = { BarProportionalChartDemo() }),
    MenuItem(title = "Line", screen = { LineChartDemo() }),
    MenuItem(title = "Line custom", screen = { LineChartCustomDemo() }),
    MenuItem(title = "Area overlapping", screen = { AreaOverlappingChartDemo() }),
    MenuItem(title = "Area stacked", screen = { AreaStackedChartDemo() }),
    MenuItem(title = "Area proportional", screen = { AreaProportionalChartDemo() }),
    MenuItem(title = "Point chart", screen = { PointChartDemo() }),
    MenuItem(title = "Point chart custom", screen = { PointChartCustomDemo() }),
    MenuItem(title = "Pie", screen = { PieChartDemo() }),
    MenuItem(title = "Doughnut", screen = { DoughnutChartDemo() }),
    MenuItem(title = "Sunburst", screen = { SunburstChartDemo() }),
    MenuItem(title = "Polar", screen = { PolarChartDemo() }),
    MenuItem(title = "Radar", screen = { RadarChartDemo() }),
    MenuItem(title = "Legend", screen = { LegendDemoView() }),
    MenuItem(title = "Legend Flow", screen = { LegendFlowDemoView() }),
)

data class MenuItem(val title: String, val screen: @Composable () -> Unit)