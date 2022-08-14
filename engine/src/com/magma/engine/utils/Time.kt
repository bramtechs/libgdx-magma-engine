package com.magma.engine.utils

import com.badlogic.gdx.utils.TimeUtils

object Time {
    val startTime = TimeUtils.millis()
	val timeMillis: Long
        get() = TimeUtils.millis() - startTime
	val time: Float
        get() = timeMillis / 1000f
}