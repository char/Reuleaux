package site.hackery.reuleaux

import org.junit.Assert
import org.junit.Test
import site.hackery.reuleaux.region.CuboidRegion
import site.hackery.reuleaux.vector.Vector3
import java.util.*

class CuboidRegionTests {
    @Test
    fun unitCube() {
        val unitCubeRegion = CuboidRegion(Vector3(0.0, 0.0, 0.0), Vector3(1.0, 1.0, 1.0))

        // The grid-aligned points of the unit cube should be the eight vertices.
        val points = unitCubeRegion.getContainedPoints()
        Assert.assertEquals(8, points.size)
        Assert.assertArrayEquals(arrayOf(
                Vector3(0.0, 0.0, 0.0),
                Vector3(0.0, 0.0, 1.0),
                Vector3(0.0, 1.0, 0.0),
                Vector3(0.0, 1.0, 1.0),
                
                Vector3(1.0, 0.0, 0.0),
                Vector3(1.0, 0.0, 1.0),
                Vector3(1.0, 1.0, 0.0),
                Vector3(1.0, 1.0, 1.0)
        ), points.toTypedArray())

        Assert.assertTrue(Vector3(0.5, 0.5, 0.5) in unitCubeRegion)
        Assert.assertFalse(Vector3(1.0, 2.0, 1.0) in unitCubeRegion)
    }
}
