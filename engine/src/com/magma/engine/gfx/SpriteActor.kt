package com.magma.engine.gfx

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.*
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.Align
import com.magma.engine.maps.MapStage
import com.magma.engine.maps.triggers.CustomTrigger

// TODO this class is confusing and dumb
abstract class SpriteActor(var region: TextureRegion) : Actor(), Drawable, CustomTrigger {
    private var hasWarned = false

    /**
     * Gives the position and dimensions of this SpriteActor in an Rectangle
     * Cache to avoid GC abuse!
     * @return Rectangle is a reference! Do not edit!
     */
    protected val bounds: Rectangle

    val centerX: Float
        get() = bounds.x + bounds.width * 0.5f
    val centerY: Float
        get() = bounds.y + bounds.height * 0.5f

    init {
        // resize to world units
        if (AUTO_SCALE) {
            setScale(1f / MapStage.tileSize.x, 1f / MapStage.tileSize.y)
        } else {
            setScale(1f)
        }
        bounds = Rectangle()

        setSpriteRegion(region)
        setOrigin(Align.center)
    }

    fun setSpriteRegion(region: TextureRegion) {
        this.region = region
        width = region.regionWidth*MapStage.scaleFactor.x
        height = region.regionHeight*MapStage.scaleFactor.y
        setOrigin(Align.center)
    }

    override fun act(delta: Float) {
        bounds.set(x,y,width, height)
        super.act(delta)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.draw(region,x,y,width,height)
    }

    override fun drawDebug(shapes: ShapeRenderer) {
        shapes.color = Color.CYAN
        shapes.rect(bounds.x, bounds.y, bounds.width, bounds.height)

        // draw collision shape if it has a custom one
        shapes.color = Color.YELLOW
        if (shape is Rectangle) {
            val rect = shape as Rectangle
            shapes.rect(rect.x, rect.y, rect.width, rect.height)
        } else if (shape is Circle) {
            val circle = shape as Circle
            shapes.circle(circle.x, circle.y, circle.radius)
        }
        shapes.color = Color.YELLOW
        shapes.circle(centerX, centerY, 0.5f)
    }

    var alpha: Float
        get() = color.a
        set(alpha) {
            setColor(color.r, color.g, color.b, alpha)
        }

    // dumb unwrapping
    fun setPosition(position: GridPoint2) {
        super.setPosition(position.x.toFloat(), position.y.toFloat())
    }

    fun setPosition(position: Vector2) {
        super.setPosition(position.x, position.y)
    }

    fun setFlip(flipX: Boolean, flipY: Boolean){
       region.flip(flipX,flipY)
    }

    val isFlipX get() = region.isFlipX
    val isFlipY get() = region.isFlipY

    override val shape: Shape2D
        get() = bounds

    companion object {
        const val AUTO_SCALE = true
    }
}