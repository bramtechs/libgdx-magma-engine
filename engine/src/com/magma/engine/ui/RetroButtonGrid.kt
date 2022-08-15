package com.magma.engine.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.magma.engine.assets.Shapes
import space.earlygrey.shapedrawer.ShapeDrawer
import kotlin.math.max
import kotlin.math.sqrt

abstract class RetroButtonGrid(vararg options: String) : Table() {
    private val options: Array<String>
    private val buttons: Array<RetroButton>
    private val gridColumns: Int
    private val gridRows: Int

    private var hasFocus = false
    private var selectedIndex: Int = 0
    private var bgColor: Color = Color.BLACK
    private var fgColor: Color = Color.WHITE

    init {
        this.options = arrayOf(*options)
        this.gridColumns = max(2.0, sqrt(options.size.toDouble())).toInt()
        this.gridRows = options.size / gridColumns
        buttons = Array(gridColumns * gridRows) { i ->
            val option = options[i]
            val butt = RetroButton(option)
            if (i == 0) {
                butt.select()
            } else if (i % gridColumns == 0) {
                row()
            }
            add(butt).expand()
            butt
        }
    }

    fun focus() {
        unfocus() // clear previous user input
        hasFocus = true
        // select the first button
        setCursor(0, 0)
    }

    fun unfocus() {
        hasFocus = false
        deselectAll()
    }

    private fun deselectAll() {
        // deselect all buttons
        for (button in buttons) {
            button.deselect()
        }
    }

    var cursorLocation: Int
        get() = selectedIndex
        set(value) {
            selectedIndex = MathUtils.clamp(value, 0, buttons.size - 1)
            deselectAll()
            buttons[selectedIndex].select()
        }


    fun moveCursor(x: Int, y: Int) {
        selectedIndex += gridColumns * y
        val curX = selectedIndex % gridColumns
        val curY = selectedIndex / gridRows
        setCursor(curX + x, curY + y)
    }

    fun setCursor(x: Int, y: Int) {
        cursorLocation = y * gridColumns + x
    }

    fun setColor(bgColor: Color, fgColor: Color) {
        this.bgColor = bgColor
        this.fgColor = fgColor
        for (button in buttons) {
            button.color = bgColor
        }
    }

    override fun act(delta: Float) {
        if (hasFocus) {
            // TODO: use keymapping
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                pressed(selectedIndex)
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                moveCursor(-1, 0)
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                moveCursor(1, 0)
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                moveCursor(0, -1)
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                moveCursor(0, 1)
            }
        }
        super.act(delta)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        val sh: ShapeDrawer = Shapes.instance
        sh.setColor(bgColor)
        sh.filledRectangle(x, y, width, height)
        sh.setColor(fgColor)
        sh.rectangle(x, y, width, height, 3f)
        super.draw(batch, parentAlpha)
    }

    protected abstract fun pressed(id: Int)

    val selectedButton: RetroButton
        get() = buttons[selectedIndex]
}