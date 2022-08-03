package com.magma.engine.chars

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Shape2D
import com.magma.engine.collision.Triggered
import com.magma.engine.entities.EntityComponent
import com.magma.engine.gfx.AnimSlot
import com.magma.engine.gfx.AnimatedSprite
import com.magma.engine.utils.MagmaMath

abstract class Character(
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

    fun collisionPush(outOf: Rectangle?) {
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
        var moveX = 0f
        var moveY = 0f
        when (direction) {
            Direction.Down -> moveY = -lastIntersect!!.height
            Direction.Left -> moveX = lastIntersect!!.width
            Direction.Right -> moveX =
                -lastIntersect!!.width - 0.01f // prevent launching bugs for some reason aka speedrun glitch
            Direction.Up -> moveY = lastIntersect!!.height
            else -> throw IllegalArgumentException("Direction not programmed")
        }

        // make launch bugs look less ridiculous, if they would occur
        moveX = MathUtils.clamp(-5f, moveX, 5f)
        moveY = MathUtils.clamp(-5f, moveY, 5f)

        // offset to new position
        // dont use setPosition()!
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
            val feetSize = region.regionWidth * 0.8f
            val height = region.regionHeight * 0.5f
            return Rectangle(x - feetSize * 0.5f, y - height, feetSize, feetSize)
        }
}