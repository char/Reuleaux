package site.hackery.reuleaux.rotation

import site.hackery.reuleaux.vector.sqPythagoreanDistance

@Suppress("NOTHING_TO_INLINE")
class Rotation @JvmOverloads constructor(val yaw: Double, val pitch: Double, val roll: Double = 0.0) {
    fun add(other: Rotation): Rotation {
        return Rotation(yaw + other.yaw, pitch + other.pitch, roll + other.roll)
    }


    fun subtract(other: Rotation): Rotation {
        return Rotation(yaw - other.yaw, pitch - other.pitch, roll - other.roll)
    }

    inline operator fun plus(other: Rotation) = add(other)
    inline operator fun minus(other: Rotation) = subtract(other)

    fun size() = sqPythagoreanDistance(yaw, pitch, roll)

    fun wrapToQuadrants(referenceYaw: Double, referencePitch: Double): Rotation {
        // Since Minecraft uses -180 to 180 for angles, instead of 0 to 360, we have to do this:
        fun wrapAngleToQuadrant(angle: Double, referenceAngle: Double): Double {
            return referenceAngle + 360 * Math.floor((angle - 180) / 360) + 180
        }

        return Rotation(wrapAngleToQuadrant(yaw, referenceYaw), wrapAngleToQuadrant(pitch, referencePitch), roll)
    }

    fun shortestPath(): Rotation {
        fun getShortestAngle(targetAngle: Double): Double {
            val positive = (360 + targetAngle) % 360
            val negative = (-360 + targetAngle) % 360

            return if (Math.abs(negative) < Math.abs(positive)) negative else positive
        }

        return Rotation(getShortestAngle(yaw), getShortestAngle(pitch), getShortestAngle(roll))
    }
}
