package com.magma.engine.ui

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.magma.engine.assets.MagmaLoader.debugSkin
import com.magma.engine.assets.Shapes
import com.magma.engine.utils.Time
import space.earlygrey.shapedrawer.ShapeDrawer
import kotlin.math.sin

open class RetroButton(text: String) : Label(text, debugSkin) {

    private val shapes: ShapeDrawer = Shapes.instance
    private var selectTime = 0f

    // arrow vertices
    private val vertexTop: Vector2 = Vector2()
    private val vertexBot: Vector2 = Vector2()
    private val vertexRight: Vector2 = Vector2()

    var isSelected = false
        protected set

    fun select() {
        isSelected = true
        selectTime = Time.time
    }

    fun deselect() {
        isSelected = false
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        // determine the shape of the arrow
        // TODO: do not calculate every frame
        val centerX = x + width * 0.5f
        val centerY = y + height * 0.5f
        var offset = OFFSET_X + 10 * 12 // TODO: fix
        // animate that boi
        val dtime = Time.time - selectTime
        offset += (sin((dtime * ANIM_SPEED).toDouble()) * ANIM_D).toFloat()
        vertexTop[centerX - offset - SIZE] = centerY + SIZE * 0.5f
        vertexBot[centerX - offset - SIZE] = centerY - SIZE * 0.5f
        vertexRight[centerX - offset] = centerY
        if (isSelected) {
            shapes.setColor(color)
            shapes.filledTriangle(vertexTop, vertexRight, vertexBot)
        }
        super.draw(batch, parentAlpha)
    }

    companion object {
        private const val OFFSET_X = -50f
        private const val SIZE = 20f
        private const val ANIM_D = 5f
        private const val ANIM_SPEED = 5f
    }
}