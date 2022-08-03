package com.magma.engine.gfx

import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.utils.Timer
import com.magma.engine.utils.tweens.Tween

class HoverInEffect(private val instance: ModelInstance, sink: Float, duration: Float, delay: Float) : Action() {
    private val pos: Vector3 = instance.transform.getTranslation(Vector3())
    private var tween: Tween? = null

    init {
        instance.transform.setTranslation(pos.x, pos.y + sink, pos.z)
        Timer.schedule(object : Timer.Task() {
            override fun run() {
                tween = Tween(sink, pos.y, duration)
            }
        }, delay)
    }

    override fun act(delta: Float): Boolean {
        if (tween != null) {
            if (tween!!.isDone) {
                instance.transform.setTranslation(pos.x, pos.y, pos.z)
                return true
            } else {
                val y = tween!!.value
                instance.transform.setTranslation(pos.x, y, pos.z)
            }
        }
        return false
    }
}