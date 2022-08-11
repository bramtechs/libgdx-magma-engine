package com.magma.engine.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

object MagmaGfx {
    /**
     * Creates an image of determined size filled with determined color.
     * @param width  of an image.
     * @param height of an image.
     * @param color  of an image fill.
     * @return [Drawable] of determined size filled with determined color.
     */
    fun getColoredDrawable(width: Int, height: Int, color: Color): Drawable {
        val pixmap = Pixmap(width, height, Pixmap.Format.RGBA8888)
        pixmap.setColor(color)
        pixmap.fill()
        val drawable = TextureRegionDrawable(TextureRegion(Texture(pixmap)))
        pixmap.dispose()
        return drawable
    }
}