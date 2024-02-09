package pl.krystiankaniowski.composecharts.internal

import kotlin.test.Test
import kotlin.test.assertEquals

internal class AxisScaleTest {

    @Test
    fun checkSmallNumbers() {
        val niceScale = AxisScale.create(min = 0f, max = 10f, maxTicks = 10)

        assertEquals(0.0f, niceScale.min)
        assertEquals(10.0f, niceScale.max)
        assertEquals(1.0f, niceScale.tickSpacing)
        assertEquals(
            expected = listOf(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f),
            actual = niceScale.getHelperLines(),
        )
    }

    @Test
    fun checkDecimalNumbers() {
        val niceScale = AxisScale.create(min = 0f, max = 1f, maxTicks = 10)
        val expectedValues = listOf(0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f)

        assertEquals(0.0f, niceScale.min)
        assertEquals(1.0f, niceScale.max)
        assertEquals(0.1f, niceScale.tickSpacing)

        val resultValues = niceScale.getHelperLines()
        for (i in expectedValues.indices) {
            assertEquals(expectedValues[i], resultValues[i], absoluteTolerance = 0.0000000001f)
        }
    }

    @Test
    fun checkBigNumbers() {
        val niceScale = AxisScale.create(min = 0f, max = 1_000_000f, maxTicks = 10)

        assertEquals(0.0f, niceScale.min)
        assertEquals(1_000_000.0f, niceScale.max)
        assertEquals(100_000.0f, niceScale.tickSpacing)
        assertEquals(
            expected = listOf(100_000.0f, 200_000.0f, 300_000.0f, 400_000.0f, 500_000.0f, 600_000.0f, 700_000.0f, 800_000.0f, 900_000.0f, 1_000_000.0f),
            actual = niceScale.getHelperLines(),
        )
    }

    // Test is disabled because of lack of nice formatting on js
    // @Test
    fun checkNiceFormatting() {
        val niceScale = AxisScale.create(min = 0f, max = 0.5f, maxTicks = 10)
        val niceScale2 = AxisScale.create(min = 0f, max = 1f, maxTicks = 10)

        assertEquals(
            listOf("0.05", "0.1", "0.15", "0.2", "0.25", "0.3", "0.35", "0.4", "0.45", "0.5"),
            niceScale.getHelperLines().map { niceScale.formatValue(it) },
        )
        assertEquals(
            listOf("0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1"),
            niceScale2.getHelperLines().map { niceScale.formatValue(it) },
        )
    }
}