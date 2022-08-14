package com.magma.engine.utils.tweens

import com.magma.engine.utils.Time

class Tween {
    private var start = 0f
    private var end = 0f
    private var duration = 0f
    private var startTime = 0f

    constructor()

    //LINEAR: y = start + x / duration * (end - start)
    constructor(start: Float, end: Float, duration: Float) {
        init(start,end,duration)
        // do not remove constructor, tweens can get recycled
    }

    fun init(start: Float, end: Float, duration: Float) {
        this.start = start
        this.end = end
        this.duration = duration
        if (duration < 0) {
            val origEnd = end
            this.end = start
            this.start = origEnd
            this.duration = -duration
        }
        startTime = Time.time
        this.start = start
        this.end = end
        this.duration = duration
    }

    val isBusy: Boolean
        get() = Time.time < startTime + duration
    val isDone: Boolean
        get() = Time.time > startTime + duration
    val time: Float
        get() = Time.time - startTime
    val value: Float
        get() = start + time / duration * (end - start)
}