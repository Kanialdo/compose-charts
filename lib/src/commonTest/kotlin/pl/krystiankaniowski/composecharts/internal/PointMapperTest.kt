import pl.krystiankaniowski.composecharts.internal.PointMapper
import kotlin.test.Test
import kotlin.test.assertEquals

internal class PointMapperTest {

    @Test
    fun validateSimpleMapping() {

        val mapper = PointMapper(
            xSrcMin = 0f,
            xSrcMax = 10f,
            xDstMin = 0f,
            xDstMax = 100f,
            ySrcMin = 0f,
            ySrcMax = 10f,
            yDstMin = 0f,
            yDstMax = 100f,
        )

        assertEquals(0f, mapper.x(0f))
        assertEquals(50f, mapper.x(5f))
        assertEquals(100f, mapper.x(10f))

        assertEquals(100f, mapper.y(0f))
        assertEquals(50f, mapper.y(5f))
        assertEquals(0f, mapper.y(10f))
    }

    @Test
    fun validateRangeMapping() {

        val mapper = PointMapper(
            xSrcMin = 20f,
            xSrcMax = 24f,
            xDstMin = 50f,
            xDstMax = 100f,
            ySrcMin = 20f,
            ySrcMax = 24f,
            yDstMin = 50f,
            yDstMax = 100f,
        )

        assertEquals(50f, mapper.x(20f))
        assertEquals(62.5f, mapper.x(21f))
        assertEquals(100f, mapper.x(24f))

        assertEquals(100f, mapper.y(20f))
        assertEquals(87.5f, mapper.y(21f))
        assertEquals(50f, mapper.y(24f))
    }

    @Test
    fun validateYInvertedFlag() {
        val mapperWithYInverted = PointMapper(
            xSrcMin = 0f,
            xSrcMax = 10f,
            xDstMin = 0f,
            xDstMax = 100f,
            ySrcMin = 0f,
            ySrcMax = 10f,
            yDstMin = 0f,
            yDstMax = 100f,
            yInverted = true,
        )

        val mapperWithYNotInverted = mapperWithYInverted.copy(yInverted = false)

        assertEquals(0f, mapperWithYNotInverted.y(0f))
        assertEquals(100f, mapperWithYInverted.y(0f))
    }

    @Test
    fun validateSameBehaviourForDifferentTypes() {
        val mapper = PointMapper(
            xSrcMin = 0f,
            xSrcMax = 10f,
            xDstMin = 0f,
            xDstMax = 100f,
            ySrcMin = 0f,
            ySrcMax = 10f,
            yDstMin = 0f,
            yDstMax = 100f,
        )

        assertEquals(mapper.x(0), mapper.x(0f))
        assertEquals(mapper.x(5), mapper.x(5f))
        assertEquals(mapper.x(10), mapper.x(10f))
    }
}