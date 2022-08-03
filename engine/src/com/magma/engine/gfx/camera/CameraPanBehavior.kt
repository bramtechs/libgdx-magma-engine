package com.magma.engine.gfx.camera

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera

class CameraPanBehavior(var speed: Float = 100f) : CameraBehavior() {

    override fun process(camera: OrthographicCamera, delta: Float) {
        val s = speed * delta * if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) 5f else 1f
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.translate(-s, 0f, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.translate(s, 0f, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.translate(0f, s, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.translate(0f, -s, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_UP)) {
            camera.zoom -= delta
        }
        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_DOWN)) {
            camera.zoom += delta
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.HOME)) {
            camera.zoom = 1f
        }
    }
}