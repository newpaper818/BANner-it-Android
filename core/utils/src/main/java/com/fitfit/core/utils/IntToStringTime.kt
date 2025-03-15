package com.fitfit.core.utils

fun secondToHourMinSec(
    time: Int //sec
): Triple<Int, Int, Int> {
    val hour = time / 3600
    val minute = (time % 3600) / 60
    val second = time % 60

    return Triple(hour, minute, second)
}