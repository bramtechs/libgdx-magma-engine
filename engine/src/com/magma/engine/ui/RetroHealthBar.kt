package com.magma.engine.ui

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.GridPoint2
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.magma.engine.assets.MagmaLoader
import com.magma.engine.ui.MagmaGfx.getColoredDrawable

class RetroHealthBar(size: GridPoint2, min: Float, max: Float, palette: Palette) :
    ProgressBar(min, max, 0.01f, false, ProgressBarStyle()) {
    private val colors: Palette
    private val skin: Skin = MagmaLoader.debugSkin
    private val font: BitmapFont
    private val size: GridPoint2

    private lateinit var prefixLabel: Label

    init {
        this.size = size
        colors = palette
        font = skin.getFont("default-font") //TODO: fix hacky solution
        applyGraphics()
    }

    private fun applyGraphics() {
        val WIDTH = size.x
        val HEIGHT = size.y
        style.background = getColoredDrawable(WIDTH, HEIGHT, colors.bgColor)
        style.knob = getColoredDrawable(WIDTH, HEIGHT, colors.fgColor)
        style.knobBefore = getColoredDrawable(WIDTH, HEIGHT, colors.fgColor)
        setAnimateDuration(0.0f)
        value = 1f
        setAnimateDuration(0.25f)
    }

    fun addIntoTable(table: Table, name: String) {
        prefixLabel = Label(name, skin)
        prefixLabel.isVisible = this.isVisible
        table.add(prefixLabel).left()
        table.add(this).width(size.x.toFloat()).height(size.y.toFloat())
        table.row()
    }

    private val text: String
        get() = value.toInt().toString() + "/" + maxValue.toInt()

    override fun draw(batch: Batch, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        font.draw(batch, text, x + 3, y + height * 0.85f)
    }

    override fun setVisible(visible: Boolean) {
        prefixLabel.isVisible = visible
        super.setVisible(visible)
    }
}