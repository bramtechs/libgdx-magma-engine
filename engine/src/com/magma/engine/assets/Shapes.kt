package com.magma.engine.assets

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Disposable
import com.magma.engine.MagmaGame
import space.earlygrey.shapedrawer.ShapeDrawer

object Shapes : Disposable {
    lateinit var instance: ShapeDrawer
    private lateinit var batch: SpriteBatch

    init {
        MagmaGame.disposeOnExit(this)
    }

    fun setup(batch: SpriteBatch, pixel: TextureRegion) {
        this.batch = batch;
        this.instance = ShapeDrawer(batch,pixel)
    }

    override fun dispose() {
        batch.dispose()
    }
}