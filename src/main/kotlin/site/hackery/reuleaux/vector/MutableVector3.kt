package site.hackery.reuleaux.vector

class MutableVector3(x: Double, y: Double, z: Double) : Vector3(x, y, z) {
    override var x: Double
        get() = super.x
        public set(value) { super.x = value }

    override var y: Double
        get() = super.y
        public set(value) { super.y = value }

    override var z: Double
        get() = super.z
        public set(value) { super.z = value }

    fun toVector3(): Vector3 {
        return Vector3(x, y, z)
    }

    override fun add(x: Double, y: Double, z: Double): Vector3 {
        this.x += x
        this.y += y
        this.z += z

        return this
    }

    override fun subtract(x: Double, y: Double, z: Double): Vector3 {
        this.x -= x
        this.y -= y
        this.z -= z

        return this
    }

    override fun scale(x: Double, y: Double, z: Double): Vector3 {
        this.x *= x
        this.y *= y
        this.z *= z

        return this
    }

    override fun divide(x: Double, y: Double, z: Double): Vector3 {
        this.x /= x
        this.y /= y
        this.z /= z

        return this
    }

    override fun normalize(): Vector3 {
        val length = this.magnitude()

        if (length == 0.0 || length == 1.0)
            return this

        return this / length
    }

    override fun rotateYaw(yaw: Double): Vector3 {
        val yawSin = Math.sin(yaw)
        val yawCos = Math.cos(yaw)

        this.x = x * yawCos + z * yawSin
        this.z = x * -yawSin + z * yawCos

        return this
    }

    override fun rotatePitch(pitch: Double): Vector3 {
        val pitchSin = Math.sin(pitch)
        val pitchCos = Math.cos(pitch)

        this.x = x * pitchCos + y * -pitchSin
        this.y = x * pitchSin + y * pitchCos

        return this
    }

    override fun rotateRoll(roll: Double): Vector3 {
        val rollSin = Math.sin(roll)
        val rollCos = Math.cos(roll)

        this.y = y * rollCos + z * -rollSin
        this.z = y * rollSin + z * rollCos

        return this
    }

    override fun floor(): Vector3 {
        this.x = Math.floor(x)
        this.y = Math.floor(y)
        this.z = Math.floor(z)

        return this
    }

    override fun ceil(): Vector3 {
        this.x = Math.ceil(x)
        this.y = Math.ceil(y)
        this.z = Math.ceil(z)

        return this
    }
}
