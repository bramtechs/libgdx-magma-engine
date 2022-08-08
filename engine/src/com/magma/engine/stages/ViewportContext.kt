package com.magma.engine.stages

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport

class ViewportContext (
    private var view: Viewport,
    private var ui: Viewport
    ){

    init {
        ins = this
    }

    companion object {
        private lateinit var ins: ViewportContext

        val width: Float
            get() = ins.view.worldWidth
        val height: Float
            get() = ins.view.worldHeight
        val uiWidth: Float
            get() = ins.ui.worldHeight
        val uiHeight: Float
            get() = ins.ui.worldHeight
        val center: Vector2
            get() = Vector2(width * 0.5f, height * 0.5f)
        val uiCenter: Vector2
            get() = Vector2(uiWidth * 0.5f, uiHeight * 0.5f)
        val camera: Camera
            get() = ins.view.camera
        val uiCamera: Camera
            get() = ins.ui.camera
        val orthoCamera: OrthographicCamera
            get() = ins.view.camera as OrthographicCamera
        val uIOrthoCamera: OrthographicCamera
            get() = ins.ui.camera as OrthographicCamera

        init {
            createRetro(40,30,640,480)
        }

        fun resize(width: Int, height: Int) {
            ins.view.update(width, height)
            ins.ui.update(width, height)
        }

        /**
         * Creates two viewports of game and ui where the game consists of tiles on a
         * grid and the ui has a fixed resolution, aka a 'retro' game.
         */
        fun createRetro(gameWidth: Int, gameHeight: Int, uiWidth: Int, uiHeight: Int): ViewportContext {
            return ViewportContext(FitViewport(gameWidth.toFloat(), gameHeight.toFloat()),FitViewport(uiWidth.toFloat(), uiHeight.toFloat()))
        }

        /**
         * Creates two viewports of the game and ui that match the window size,
         * defintion of a 'modern' game.
         */
        fun createModern(camera: Camera): ViewportContext {
            return ViewportContext(ScreenViewport(camera),ScreenViewport())
        }

        /**
         * Creates an context storing two viewports of the game and ui.
         */
        fun createCustom(view: Viewport, uiView: Viewport): ViewportContext {
            return ViewportContext(view,uiView)
        }
    }
}