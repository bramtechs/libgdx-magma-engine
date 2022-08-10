package com.magma.engine.stages

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.magma.engine.collision.CollisionStage
import com.magma.engine.gfx.camera.CameraBehavior
import com.magma.engine.gfx.camera.CameraPanBehavior

// This abstract type of stage is linked to another stage containing the UI
open class GameStage(batch: SpriteBatch) : CollisionStage(batch) {
    val uIStage: Stage

    private var cameraBehavior: CameraBehavior? = null
    private var cameraBehaviorUI: CameraBehavior? = null
    private var initialized = false

    init {
        uIStage = Stage(ViewportContext.ui, batch)
    }

    fun setCameraBehavior(behavior: CameraBehavior) {
        cameraBehavior?.remove()
        cameraBehavior = behavior
        addActor(behavior)
    }

    fun setCameraBehaviorUI(behavior: CameraBehavior?) {
        if (cameraBehaviorUI != null) {
            cameraBehaviorUI!!.remove()
        }
        cameraBehaviorUI = behavior
        uIStage.addActor(cameraBehaviorUI)
    }

    val orthoCamera: OrthographicCamera
        get() {
            check(camera is OrthographicCamera) { "Camera is not orthographic!" + camera.javaClass.simpleName }
            return camera as OrthographicCamera
        }
    val persCamera: PerspectiveCamera
        get() {
            check(camera is PerspectiveCamera) { "Camera is not perspective! It is " + camera.javaClass.simpleName }
            return camera as PerspectiveCamera
        }

    override fun act(delta: Float) {
        cameraBehavior?.process(orthoCamera,delta)
        cameraBehaviorUI?.process(orthoCamera,delta)
        if (Gdx.input.isKeyJustPressed(Input.Keys.F7)) {
            setCameraBehaviorUI(CameraPanBehavior())
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F8)) {
            setCameraBehavior(CameraPanBehavior())
        }
        super.act(delta)
        uIStage.act(delta)
    }

    override fun draw() {
        super.draw()
        uIStage.viewport.apply()
        uIStage.draw()
    }

    override fun dispose() {
        uIStage.dispose()
        super.dispose()
    }
}