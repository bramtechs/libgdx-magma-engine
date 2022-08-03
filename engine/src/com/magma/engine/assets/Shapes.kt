package com.magma.engine.assets

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Disposable
import com.magma.engine.MagmaGame
import space.earlygrey.shapedrawer.ShapeDrawer

object Shapes : Disposable {
    private lateinit var drawer: ShapeDrawer
    private lateinit var instance: SpriteBatch

    init {
        MagmaGame.disposeOnExit(this)
    }

    fun setup(batch: SpriteBatch, pixel: TextureRegion) {
        this.instance = batch;
        this.drawer = ShapeDrawer(batch,pixel)
    }

    override fun dispose() {
        instance.dispose()
    }
}