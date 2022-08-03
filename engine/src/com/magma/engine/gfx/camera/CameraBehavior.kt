package com.magma.engine.gfx.camera

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import java.lang.IllegalArgumentException

abstract class CameraBehavior() : Actor() {

    abstract fun process(camera: OrthographicCamera, delta: Float)

    override fun act(delta: Float) {
        if (stage.camera != null) {
            if (stage.camera !is OrthographicCamera)
                throw IllegalArgumentException("CameraBehaviours only support Orthographic Cameras.")
            process(stage.camera as OrthographicCamera, delta)
        }
        super.act(delta)
    }
}