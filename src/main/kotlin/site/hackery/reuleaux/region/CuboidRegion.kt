package site.hackery.reuleaux.region

import site.hackery.reuleaux.vector.Vector3

class CuboidRegion(pos1: Vector3, pos2: Vector3) {
    val min: Vector3 = Vector3(Math.min(pos1.x, pos2.x), Math.min(pos1.y, pos2.y), Math.min(pos1.z, pos2.z))
    val max: Vector3 = Vector3(Math.max(pos1.x, pos2.x), Math.max(pos1.y, pos2.y), Math.max(pos1.z, pos2.z))
    val size = max - min

    fun volume(): Double {
        return size.x * size.y * size.z
    }

    fun surfaceArea(): Double {
        return 2 * (size.x * size.y + size.x * size.z + size.y * size.z)
    }

    fun contains(position: Vector3): Boolean {
        return (position.x >= min.x && position.x <= max.x)
                && (position.y >= min.y && position.y <= max.y)
                && (position.z >= min.z && position.z <= max.z);
    }

    fun getContainedPoints(gridSize: Double = 1.0): List<Vector3> {
        val startX = Math.floor(min.x / gridSize) * gridSize
        val startY = Math.floor(min.y / gridSize) * gridSize
        val startZ = Math.floor(min.z / gridSize) * gridSize

        val endX = Math.floor(max.x / gridSize) * gridSize
        val endY = Math.floor(max.y / gridSize) * gridSize
        val endZ = Math.floor(max.z / gridSize) * gridSize

        val points = mutableListOf<Vector3>()

        var x = startX
        while (x <= endX) {
            var y = startY
            while (y <= endY) {
                var z = startZ
                while (z <= endZ) {
                    points.add(Vector3(x, y, z))

                    z += gridSize
                }

                y += gridSize
            }

            x += gridSize
        }

        return points
    }

    fun offset(offset: Vector3): CuboidRegion {
        return CuboidRegion(min + offset, max + offset)
    }

    fun offset(x: Double, y: Double, z: Double): CuboidRegion {
        return CuboidRegion(min.add(x, y, z), max.add(x, y, z))
    }

    fun expand(expansion: Double): CuboidRegion {
        return CuboidRegion(min.subtract(expansion, expansion, expansion), max.add(expansion, expansion, expansion))
    }

    fun expand(expansion: Vector3): CuboidRegion {
        return CuboidRegion(min - expansion, max + expansion)
    }
}

operator fun CuboidRegion.contains(position: Vector3) = this.contains(position)
operator fun CuboidRegion.iterator() = getContainedPoints().iterator()
