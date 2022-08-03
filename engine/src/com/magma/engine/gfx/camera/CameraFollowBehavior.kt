package com.magma.engine.gfx.camera

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Actor

class CameraFollowBehavior(private val target: Actor) : CameraBehavior() {
    override fun process(camera: OrthographicCamera, delta: Float) {
            camera.position.x = target.x + target.width * 0.5f * target.scaleX
            camera.position.y = target.y + target.height * 0.5f * target.scaleY
    }
}