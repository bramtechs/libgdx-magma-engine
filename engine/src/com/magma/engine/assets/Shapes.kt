package com.magma.engine.assets

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.magma.engine.ui.MagmaPixmap
import space.earlygrey.shapedrawer.ShapeDrawer
import java.lang.IllegalStateException

object Shapes {

    val instance: ShapeDrawer
        get() {
        if (drawer == null) {
            throw IllegalStateException("Shape Drawer needs to be initialized first!")
        }
        return drawer!!
    }

    private var drawer: ShapeDrawer? = null

    fun setup(batch: SpriteBatch) {
        val pixel = MagmaPixmap.createColoredRect(1,1, Color.WHITE)
        val region = pixel.bakeRegion()
        this.drawer = ShapeDrawer(batch,region)
    }
}