import pl.krystiankaniowski.composecharts.internal.calculateHelperValues
import pl.krystiankaniowski.composecharts.internal.calculateYHelperLines
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

internal class YLinesTest {

    @Test
    fun testPositive() {
        assertEquals(listOf(2f, 4f, 6f, 8f, 10f), calculateYHelperLines(0f, 10f))
        assertEquals(listOf(20f, 40f, 60f, 80f, 100f), calculateYHelperLines(0f, 100f))
        assertEquals(listOf(2f, 4f, 6f, 8f), calculateYHelperLines(0f, 8f))
    }

    @Ignore
    @Test
    fun testSmallNumbers() {
        assertEquals(listOf(0.2f, 0.4f, 0.6f, 0.8f, 1f), calculateYHelperLines(0f, 0.1f))
    }

    @Ignore
    @Test
    fun testNegative() {
        assertEquals(listOf(-10f, -8f, -6f, -4f, -2f), calculateYHelperLines(-10f, 0f))
        assertEquals(listOf(-0.6f, -0.2f, 0.2f, 0.6f, 1f), calculateYHelperLines(-1f, 1f))
    }

    @Test
    fun testIrregular() {
        assertEquals(listOf(2f, 3f, 4f, 5f, 6f), calculateYHelperLines(1f, 6.2f))
    }

    // -----

    @Test
    fun test1() {
        assertEquals(listOf(-0.2f, 0.0f, 0.2f, 0.4f, 0.6f, 0.8f, 1.0f, 1.2f), calculateHelperValues(0f, 1f, 10))
    }

    @Test
    fun test2() {
        assertEquals(listOf(0f, 25f, 50f, 75f, 100f, 125f, 150f, 175f, 200f, 225f, 250f), calculateHelperValues(15f, 234f, 10))
    }

    @Test
    fun test3() {
        assertEquals(listOf(19.2f, 19.8f, 20.4f, 21.0f, 21.6f, 22.2f), calculateHelperValues(19.8f, 21.9f, 5))
    }

}