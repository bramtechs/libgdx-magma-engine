package com.magma.engine.gfx

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.*
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.Align
import com.magma.engine.maps.MapSession
import com.magma.engine.maps.triggers.CustomTrigger

// TODO this class is confusing and dumb
abstract class SpriteActor(var region: TextureRegion) : Actor(), Drawable, CustomTrigger {
    var isFlipX = false
    var isFlipY = false

    private var mgScaleX = 0f
    private var mgScaleY = 0f
    /**
     * Gives the position and dimensions of this SpriteActor in an Rectangle
     * Cache to avoid GC abuse!
     * @return Rectangle is a reference! Do not edit!
     */
    private val bounds
            : Rectangle

    init {
        setSpriteRegion(region)
        setOrigin(Align.center)

        // resize to world units
        if (AUTO_SCALE) {
            setScale(1f / MapSession.getTileSize().x, 1f / MapSession.getTileSize().y)
        } else {
            setScale(1f)
        }
        bounds = Rectangle()
    }

    fun setSpriteRegion(region: TextureRegion) {
        this.region = region
        val w = region.regionWidth * mgScaleX
        val h = region.regionHeight * mgScaleY
        setSize(w, h)
        setOrigin(Align.center)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        /*
		 * region x y originX originY width height scaleX scaleY rotation
		 */
        bounds.x = x - width * 0.5f
        bounds.y = y - height * 0.5f
        bounds.width = width
        bounds.height = height
        if (isVisible && alpha > 0.01f) {
            batch.color = color
            batch.draw(
                region, bounds.x, bounds.y, originX, originY, bounds.width, bounds.height,
                if (isFlipX) -1f else 1f, if (isFlipY) -1f else 1f, rotation
            )
            batch.color = Color.WHITE
        }
        //Gdx.app.log("Player", ""+getX() + " x " + getY());
        super.draw(batch, parentAlpha)
    }


    val centerX: Float
        get() = bounds.x + bounds.width * 0.5f
    val centerY: Float
        get() = bounds.y + bounds.height * 0.5f

    override fun drawDebug(shapes: ShapeRenderer) {
        shapes.color = Color.CYAN
        shapes.rect(bounds.x, bounds.y, bounds.width, bounds.height)

        // draw collision shape if it has a custom one
        val trig: CustomTrigger = this
        val shape = trig.shape
        shapes.color = Color.YELLOW
        if (shape is Rectangle) {
            val rect = shape
            shapes.rect(rect.x, rect.y, rect.width, rect.height)
        } else if (shape is Circle) {
            val circle = shape
            shapes.circle(circle.x, circle.y, circle.radius)
        }
        shapes.color = Color.YELLOW
        shapes.circle(centerX, centerY, 0.5f)
    }

    // I'M USING MY OWN EASIER SCALING SYSTEM
    override fun setScale(scaleX: Float, scaleY: Float) {
        setScaleX(scaleX)
        setScaleY(scaleY)
    }

    var alpha: Float
        get() = color.a
        set(alpha) {
            setColor(color.r, color.g, color.b, alpha)
        }

    override fun setScale(scaleXY: Float) {
        this.mgScaleX = scaleXY
        this.mgScaleY = scaleXY
    }

    fun setFlip(flipX: Boolean, flipY: Boolean) {
        this.isFlipX = flipX
        this.isFlipY = flipY
    }

    // dumb unwrapping
    fun setPosition(position: GridPoint2) {
        super.setPosition(position.x.toFloat(), position.y.toFloat())
    }

    fun setPosition(position: Vector2) {
        super.setPosition(position.x, position.y)
    }

    companion object {
        const val AUTO_SCALE = true
    }
}