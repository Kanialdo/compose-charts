package pl.krystiankaniowski.composecharts.internal

import kotlin.test.Test
import kotlin.test.assertEquals

internal class NiceScaleKtTest {

    @Test
    fun `Math test`() {
        val initValue = 0.0
        assertEquals(0.0, initValue)
        assertEquals(0.1, initValue + 0.1)
        assertEquals(0.2, initValue + 0.1 + 0.1)
        assertEquals(0.3, initValue + 0.1 + 0.1 + 0.1)
    }

    @Test
    fun `Check small numbers`() {
        val niceScale = niceScale(0f, 1f, maxTicks = 10)

        assertEquals(0.0, niceScale.niceMin)
        assertEquals(1.0, niceScale.niceMax)
        assertEquals(0.1, niceScale.tickSpacing)
        assertEquals(
            expected = listOf(0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0, 1.1),
            actual = niceScale.getHelperLines(),
        )
    }

    @Test
    fun `Check small irregular numbers`() {
        val niceScale = niceScale(0.000001f, 0.999999f, maxTicks = 10)

        assertEquals(0.0, niceScale.niceMin)
        assertEquals(1.0, niceScale.niceMax)
        assertEquals(0.1, niceScale.tickSpacing)
        assertEquals(
            expected = listOf(0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0, 1.1),
            actual = niceScale.getHelperLines(),
        )
    }
}