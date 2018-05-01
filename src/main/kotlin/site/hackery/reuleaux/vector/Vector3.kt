package site.hackery.reuleaux.vector

import site.hackery.reuleaux.rotation.Rotation

@Suppress("NOTHING_TO_INLINE") // Gotta optimize away those INVOKEVIRTUAL instructions, you know?
open class Vector3(x: Double, y: Double, z: Double) {
    // x, y, and z are declared as open in order to facilitate MutableVector3.
    // Furthermore, all functions that return a new Vector3 should be declared open to facilitate MutableVector3,
    // since all MutableVector3 implementations will instead modify itself instead of creating a new Vector3 object.

    open var x: Double = x
        protected set
    open var y: Double = y
        protected set
    open var z: Double = z
        protected set

    private var _length: Double = -1.0
    protected fun calculateLength() { _length = pythagoreanDistance(x, y, z) }

    fun magnitude(): Double {
        if (_length == -1.0)
            calculateLength()

        return _length
    }


    inline operator fun plus(other: Vector3) = add(other)
    inline operator fun minus(other: Vector3) = subtract(other)
    inline operator fun times(other: Vector3) = scale(other)
    inline operator fun times(scalar: Double) = scale(scalar)
    inline operator fun div(other: Vector3) = divide(other)
    inline operator fun div(divisor: Double) = divide(divisor)


    fun add(other: Vector3) =
            add(other.x, other.y, other.z)

    fun subtract(other: Vector3) =
            subtract(other.x, other.y, other.z)

    fun scale(other: Vector3) =
            scale(other.x, other.y, other.z)
    fun scale(scalar: Double) =
            scale(scalar, scalar, scalar)

    fun divide(other: Vector3) =
            divide(other.x, other.y, other.z)
    fun divide(divisor: Double) =
            divide(divisor, divisor, divisor)


    open fun add(x: Double, y: Double, z: Double): Vector3 {
        return Vector3(this.x + x, this.y + y, this.z + z)
    }

    open fun subtract(x: Double, y: Double, z: Double): Vector3 {
        return Vector3(this.x - x, this.y - y, this.z - z)
    }

    open fun scale(x: Double, y: Double, z: Double): Vector3 {
        return Vector3(this.x * x, this.y * y, this.z * z)
    }

    open fun divide(x: Double, y: Double, z: Double): Vector3 {
        return Vector3(this.x / x, this.y / y, this.z / z)
    }

    fun dot(other: Vector3) =
            this.x * other.x + this.y * other.y + this.z * other.z

    fun distanceTo(other: Vector3) =
            pythagoreanDistance(x - other.x, y - other.y, z - other.z)

    fun sqDistanceTo(other: Vector3) =
            sqPythagoreanDistance(x - other.x, y - other.y, z - other.z)


    open fun normalize(): Vector3 {
        val length = magnitude()

        if (length == 0.0 || length == 1.0)
            return Vector3(x, y, z)

        return this / length
    }


    open fun floor(): Vector3 =
            Vector3(Math.floor(x), Math.floor(y), Math.floor(z))
    fun centre(): Vector3 =
            floor().add(0.5, 0.5, 0.5)
    open fun ceil(): Vector3 =
            Vector3(Math.ceil(x), Math.ceil(y), Math.ceil(z))

    fun inverse() = this * -1.0

    open fun rotateYaw(yaw: Double): Vector3 {
        // +---------+---+--------+
        // | cos(a)  | 0 | sin(a) |
        // +---------+---+--------+
        // | 0       | 1 | 0      |
        // +---------+---+--------+
        // | -sin(a) | 0 | cos(a) |
        // +---------+---+--------+

        val yawSin = Math.sin(yaw)
        val yawCos = Math.cos(yaw)

        return Vector3(x * yawCos + z * yawSin, y, x * -yawSin + z * yawCos)
    }

    open fun rotatePitch(pitch: Double): Vector3 {
        // +--------+---------+---+
        // | cos(a) | -sin(a) | 0 |
        // +--------+---------+---+
        // | sin(a) | cos(a)  | 0 |
        // +--------+---------+---+
        // | 0      | 0       | 1 |
        // +--------+---------+---+

        val pitchSin = Math.sin(pitch)
        val pitchCos = Math.cos(pitch)

        return Vector3(x * pitchCos + y * -pitchSin, x * pitchSin + y * pitchCos, z)
    }

    open fun rotateRoll(roll: Double): Vector3 {
        // +---+--------+---------+
        // | 1 | 0      | 0       |
        // +---+--------+---------+
        // | 0 | cos(a) | -sin(a) |
        // +---+--------+---------+
        // | 0 | sin(a) | cos(a)  |
        // +---+--------+---------+

        val rollSin = Math.sin(roll)
        val rollCos = Math.cos(roll)

        return Vector3(x, y * rollCos + z * -rollSin, y * rollSin + z * rollCos)
    }

    fun rotateYawDeg(yaw: Double) = rotateYaw(Math.toRadians(yaw))
    fun rotatePitchDeg(pitch: Double) = rotatePitch(Math.toRadians(pitch))
    fun rotateRollDeg(roll: Double) = rotateRoll(Math.toRadians(roll))

    fun rotate(rotation: Rotation): Vector3 {
        return this.rotateYawDeg(rotation.yaw).rotatePitchDeg(rotation.pitch).rotateRollDeg(rotation.roll)
    }

    override fun equals(other: Any?): Boolean {
        if (other is Vector3) {
            return this.x == other.x && this.y == other.y && this.z == other.z
        }

        return super.equals(other)
    }

    override fun toString(): String {
        return "Vector3[x=$x, y=$y, z=$z]"
    }
}

internal fun pythagoreanDistance(x: Double, y: Double, z: Double): Double {
    return Math.sqrt(x * x + y * y + z * z)
}

internal fun sqPythagoreanDistance(x: Double, y: Double, z: Double): Double {
    return x * x + y * y + z * z
}
