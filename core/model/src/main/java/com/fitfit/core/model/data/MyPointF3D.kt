package com.fitfit.core.model.data

import kotlin.math.sqrt

data class MyPointF3D(
    val x: Float,
    val y: Float,
    val z: Float,
){
    operator fun plus(other: MyPointF3D): MyPointF3D {
        return MyPointF3D(x + other.x, y + other.y, z + other.z)
    }

    operator fun minus(other: MyPointF3D): MyPointF3D {
        return MyPointF3D(x - other.x, y - other.y, z - other.z)
    }

    operator fun times(scalar: Float): MyPointF3D {
        return MyPointF3D(x * scalar, y * scalar, z * scalar)
    }

    operator fun div(scalar: Float): MyPointF3D {
        return MyPointF3D(x / scalar, y / scalar, z / scalar)
    }

    fun distanceTo(other: MyPointF3D): Float {
        return sqrt(
            (x - other.x) * (x - other.x)
                + (y - other.y) * (y - other.y)
                + (z - other.z) * (z - other.z)
        )
    }
}