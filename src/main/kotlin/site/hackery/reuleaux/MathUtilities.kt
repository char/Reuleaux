package site.hackery.reuleaux

/**
 * Determines if an [actual] value is approximately equal to an [expected] value.
 * @return If the [actual] value is equal to the [expected] value within the [margin].
 */
fun approxEquals(expected: Double, actual: Double, margin: Double): Boolean {
    return Math.abs(expected - actual) <= margin
}
