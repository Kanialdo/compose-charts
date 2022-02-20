import pl.krystiankaniowski.composecharts.internal.calculateYHelperLines
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ScaleTest {

    @Test
    fun test() {
        assertEquals(emptyList(), calculateYHelperLines(0f, 10f))
    }

    @Test
    fun test2() {
        assertEquals(emptyList(), calculateYHelperLines(0f, 100f))
    }

    @Test
    fun test3() {
        assertEquals(emptyList(), calculateYHelperLines(0f, 8f))
    }

    @Test
    fun test4() {
        assertEquals(emptyList(), calculateYHelperLines(0f, 0.1f))
    }

    @Test
    fun test5() {
        assertEquals(emptyList(), calculateYHelperLines(1f, 6.2f))
    }
}