package pl.krystiankaniowski.composecharts.internal

import kotlin.test.Test
import kotlin.test.assertEquals

internal class YLinesTest {

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