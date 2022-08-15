package com.magma.engine.chars

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Shape2D
import com.magma.engine.collision.Triggered
import com.magma.engine.debug.MagmaLogger
import com.magma.engine.entities.Entity
import com.magma.engine.entities.EntityComponent
import com.magma.engine.gfx.AnimSlot
import com.magma.engine.gfx.AnimatedSprite
import com.magma.engine.utils.MagmaMath

abstract class Character(
    override val entity: Entity,
    private var animUp: AnimSlot,
    private var animDown: AnimSlot,
    private var animSide: AnimSlot
) : AnimatedSprite(
    animUp, animDown, animSide
), Triggered, EntityComponent {
    private enum class Direction {
        Up, Down, Right, Left
    }

    private var stopFrames = 0
    private var lastIntersect: Rectangle? = null
    private var direction: Direction

    init {
        setPlayMode(PlayMode.LOOP)
        setCurrentAnimation(animDown)
        direction = Direction.Up
    }

    fun collisionPush(outOf: Rectangle) {
        if (lastIntersect == null) {
            lastIntersect = Rectangle()
        }
        lastIntersect = Rectangle()
        val shape = MagmaMath.extractShape(this)
        if (shape is Rectangle) {
            Intersector.intersectRectangles(outOf, shape, lastIntersect)
        } else {
            throw IllegalArgumentException("Characters only support rectangles as collision shape")
        }

        // stop the current animation
        stop()
        // TODO calculate simple direction between two shapes and push that way

        val moveX = 0f
        val moveY = 0f

        x += moveX
        y += moveY
    }

    override fun moveBy(x: Float, y: Float) {
        if (x > 0) { // right
            setFlip(flipX = false, flipY = false)
            setCurrentAnimation(animSide)
            direction = Direction.Right
        } else if (x < 0) { // left
            setFlip(flipX = true, flipY = false)
            setCurrentAnimation(animSide)
            direction = Direction.Left
        } else if (y < 0) { // up
            setCurrentAnimation(animUp)
            direction = Direction.Up
        } else if (y > 0) { // down
            setCurrentAnimation(animDown)
            direction = Direction.Down
        }
        stopFrames = 0
        super.moveBy(x, y)
    }

    override fun act(delta: Float) {
        stopFrames++
        if (stopFrames > 1) {
            stop()
        }
        super.act(delta)
    }

    override val shape : Shape2D
        get() {
            val size = 1f
            return Rectangle(centerX-size*0.5f, centerY-size*0.5f-0.7f, size, size)
        }
}