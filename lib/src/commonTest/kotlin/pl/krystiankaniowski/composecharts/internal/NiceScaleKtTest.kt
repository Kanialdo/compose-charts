package pl.krystiankaniowski.composecharts.internal

import kotlin.test.Test
import kotlin.test.assertEquals

internal class NiceScaleKtTest {

    @Test
    fun `Check small numbers`() {
        val niceScale = niceScale(0f, 10f, maxTicks = 10)

        assertEquals(0.0, niceScale.niceMin)
        assertEquals(10.0, niceScale.niceMax)
        assertEquals(1.0, niceScale.tickSpacing)
        assertEquals(
            expected = listOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0),
            actual = niceScale.getHelperLines(),
        )
    }

    @Test
    fun `Check decimal numbers`() {
        val niceScale = niceScale(0f, 1f, maxTicks = 10)
        val expectedValues = listOf(0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)

        assertEquals(0.0, niceScale.niceMin)
        assertEquals(1.0, niceScale.niceMax)
        assertEquals(0.1, niceScale.tickSpacing)

        val resultValues = niceScale.getHelperLines()
        for (i in expectedValues.indices) {
            assertEquals(expectedValues[i], resultValues[i], absoluteTolerance = 0.0000000001)
        }
    }

    @Test
    fun `Check big numbers`() {
        val niceScale = niceScale(0f, 1_000_000f, maxTicks = 10)

        assertEquals(0.0, niceScale.niceMin)
        assertEquals(1_000_000.0, niceScale.niceMax)
        assertEquals(100_000.0, niceScale.tickSpacing)
        assertEquals(
            expected = listOf(100_000.0, 200_000.0, 300_000.0, 400_000.0, 500_000.0, 600_000.0, 700_000.0, 800_000.0, 900_000.0, 1_000_000.0),
            actual = niceScale.getHelperLines(),
        )
    }

    @Test
    fun `Check nice scale with extended min and max values`() {
        val niceScale = niceScale(0f, 10f, maxTicks = 10)

        assertEquals(0.0, niceScale.niceMin)
        assertEquals(10.0, niceScale.niceMax)
        assertEquals(1.0, niceScale.tickSpacing)
        assertEquals(
            expected = listOf(-1.0, 0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0),
            actual = niceScale.getHelperLines(beforeMinValues = 2, afterMaxValues = 2),
        )
    }
}