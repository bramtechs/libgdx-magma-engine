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
import com.magma.engine.debug.MagmaLogger
import com.magma.engine.stages.StageSwitcher
import java.io.File

abstract class MagmaGame(private val assetFolder: String) : Game() {
	protected lateinit var modelBatch: ModelBatch
    protected lateinit var spriteBatch: SpriteBatch
    protected lateinit var stageSwitcher: StageSwitcher
    private val disposables: Array<Disposable> = Array()

    var backgroundColor: Color = Color.BLACK

    init {
        instance = this
    }

    override fun create() {
        Gdx.app.logLevel = Application.LOG_INFO
        spriteBatch = SpriteBatch()
        modelBatch = ModelBatch()
        MagmaLogger.log(this, "Executing at path " + File("").absolutePath)
        MagmaLogger.log(this, "Save files will be saved in " + SaveFile.getPersistentPath())
        setScreen(stageSwitcher)
        initStages()
    }

    protected abstract fun initStages()

    override fun render() {
        // update all the things
        val delta = Gdx.graphics.deltaTime
        StageSwitcher.act(delta)

        // render all the things
        ScreenUtils.clear(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a)
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT or GL30.GL_DEPTH_BUFFER_BIT)
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
    }

    companion object {
        lateinit var instance : MagmaGame
        val assetFolder: String
            get() = instance.assetFolder

		fun disposeOnExit(disposable: Disposable) {
            if (instance.disposables.contains(disposable, false)) {
                instance.disposables.add(disposable)
            } else {
                MagmaLogger.log(disposable, "Already marked for disposal ${disposable.javaClass.simpleName}")
            }
        }
    }
}