package com.magma.engine.stages

import com.badlogic.gdx.Screen
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Disposable
import com.magma.engine.MagmaGame.Companion.disposeOnExit
import com.magma.engine.debug.modules.StageModuleListener

//TODO: Add stage transitions
object StageSwitcher : Screen, Disposable, StageModuleListener {
    private val listeners: Array<StageSwitchListener> = Array()
    private lateinit var active: GameStage
    init {
        disposeOnExit(this)
    }

    override fun render(delta: Float) {
        active.viewport.apply()
        active.draw()
    }

    override fun dispose() {
        active.dispose()
        listeners.clear()
    }

    override fun resize(width: Int, height: Int) {
        ViewportContext.resize(width, height)
        val ui = active.uiStage
        ui.viewport.update(width, height)
        ui.camera.update(true)
        for (listener in listeners) {
            listener.stageResized(width, height)
        }
    }

    fun open(stage: GameStage) {
        active = stage

        // make the stage a listener if it has the correct interface
        if (stage is StageSwitchListener) {
            listeners.add(stage as StageSwitchListener)
        }

        // notify all the listeners
        for (s in listeners) {
            s.stageOpened(stage)
        }
    }

    fun addListener(listener: StageSwitchListener) {
        listeners.add(listener)
    }

    fun act(delta: Float) {
        active.act(delta)
    }

    override fun pause() {}
    override fun resume() {}
    override fun show() {}
    override fun hide() {}
    override fun unloadStage() {}
}