package com.magma.engine

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.magma.engine.assets.Shapes
import com.magma.engine.debug.Debugger
import com.magma.engine.debug.MagmaLogger
import com.magma.engine.stages.StageSwitcher
import java.io.File

abstract class MagmaGame(private val assetFolder: String) : Game() {

    var bgColor: Color = Color.BLACK

    override fun create() {
        instance = this
        spriteBatch = SpriteBatch()
        modelBatch = ModelBatch()

        Gdx.app.logLevel = Application.LOG_INFO
        Shapes.setup(spriteBatch)
        initViewports()
        MagmaLogger.log(this, "Executing at path " + File("").absolutePath)
        setScreen(StageSwitcher)
        initStages()
    }

    protected abstract fun initViewports()
    protected abstract fun initStages()

    override fun render() {
        // update all the things
        val delta = Gdx.graphics.deltaTime
        StageSwitcher.act(delta)

        // render all the things
        ScreenUtils.clear(bgColor.r, bgColor.g, bgColor.b, bgColor.a)
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT or GL30.GL_DEPTH_BUFFER_BIT)

        Debugger.update()
        super.render()
    }

    override fun dispose() {
        super.dispose()
        for (disp in disposables) {
            MagmaLogger.log(this, "Disposing " + disp.javaClass.simpleName + " ...")
            disp.dispose()
        }
        spriteBatch.dispose()
        modelBatch.dispose()
        MagmaLogger.flush()
    }

    companion object {
        lateinit var instance : MagmaGame
        lateinit var spriteBatch: SpriteBatch
        lateinit var modelBatch: ModelBatch

        private val disposables: Array<Disposable> = Array()
        val assetFolder: String
            get() = instance.assetFolder

		fun disposeOnExit(disposable: Disposable) {
            if (disposables.contains(disposable, false)) {
                disposables.add(disposable)
            } else {
                MagmaLogger.log(disposable, "Already marked for disposal ${disposable.javaClass.simpleName}")
            }
        }
    }
}