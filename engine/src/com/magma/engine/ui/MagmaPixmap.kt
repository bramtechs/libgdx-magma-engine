package com.magma.engine.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Disposable
import com.magma.engine.MagmaGame

class MagmaPixmap(val pixmap: Pixmap) : Disposable {

    private var texture : Texture? = null;

    init {
        MagmaGame.disposeOnExit(this)
    }

    fun bakeTexture(): Texture {
        if (texture == null){
            texture = Texture(pixmap)
        }
        return texture!!
    }

    fun bakeRegion(): TextureRegion{
        return TextureRegion(bakeTexture())
    }

    fun bakeDrawable(): Drawable{
        val drawable = bakeTexture()
        return TextureRegionDrawable(TextureRegion(drawable))
    }

    fun addBorder(color: Color) {
        pixmap.setColor(color)
        pixmap.drawRectangle(0,0,pixmap.width,pixmap.height)
    }

    override fun dispose() {
        texture?.dispose()
        pixmap.dispose()
    }

    companion object{
        /**
         * Creates an image of determined size filled with determined color.
         * @param width  of an image.
         * @param height of an image.
         * @param color  of an image fill.
         * @return [Drawable] of determined size filled with determined color.
         */
        fun createColoredRect(width: Int, height: Int, color: Color): MagmaPixmap{
            val pixmap = Pixmap(width, height, Pixmap.Format.RGBA8888)
            pixmap.setColor(color)
            pixmap.fill()
            return MagmaPixmap(pixmap)
        }

        fun createTransparentRect(width: Int, height: Int): MagmaPixmap{
            return createColoredRect(width,height,Color.CLEAR)
        }
    }

}