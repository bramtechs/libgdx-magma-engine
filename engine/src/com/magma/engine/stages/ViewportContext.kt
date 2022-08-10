package com.magma.engine.stages

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport

object ViewportContext {
    lateinit var view: Viewport
    lateinit var ui: Viewport

    val width: Float
        get() = view.worldWidth
    val height: Float
        get() = view.worldHeight
    val uiWidth: Float
        get() = ui.worldHeight
    val uiHeight: Float
        get() = ui.worldHeight
    val center: Vector2
        get() = Vector2(width * 0.5f, height * 0.5f)
    val uiCenter: Vector2
        get() = Vector2(uiWidth * 0.5f, uiHeight * 0.5f)
    val camera: Camera
        get() = view.camera
    val uiCamera: Camera
        get() = ui.camera
    val orthoCamera: OrthographicCamera
        get() = view.camera as OrthographicCamera
    val uIOrthoCamera: OrthographicCamera
        get() = ui.camera as OrthographicCamera

    fun resize(width: Int, height: Int) {
        view.update(width, height)
        ui.update(width, height)
    }

    /**
     * Creates two viewports of game and ui where the game consists of tiles on a
     * grid and the ui has a fixed resolution, aka a 'retro' game.
     */
    fun retro(gameWidth: Int, gameHeight: Int, uiWidth: Int, uiHeight: Int) {
        view = FitViewport(gameWidth.toFloat(), gameHeight.toFloat())
        ui = FitViewport(uiWidth.toFloat(), uiHeight.toFloat())
    }

    /**
     * Creates two viewports of the game and ui that match the window size,
     * defintion of a 'modern' game.
     */
    fun modern(camera: Camera) {
        view = ScreenViewport(camera)
        ui = ScreenViewport()
    }

    /**
     * Creates an context storing two viewports of the game and ui.
     */
    fun custom(view: Viewport, uiView: Viewport) {
        this.view = view
        this.ui = uiView
    }
}