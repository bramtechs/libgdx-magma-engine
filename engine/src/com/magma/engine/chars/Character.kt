package com.magma.engine.chars

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Shape2D
import com.badlogic.gdx.scenes.scene2d.Actor
import com.magma.engine.assets.Shapes
import com.magma.engine.collision.Triggered
import com.magma.engine.entities.Entity
import com.magma.engine.entities.EntityComponent
import com.magma.engine.gfx.AnimSlot
import com.magma.engine.gfx.AnimatedSprite

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

    private var direction: Direction = Direction.Down;

    // actor for doing hitbox checks
    private val collider: CharacterCollider = CharacterCollider(this)

    init {
        setPlayMode(PlayMode.LOOP)
        setCurrentAnimation(animDown)
    }

    override fun lateInit(){
       stage.addActor(collider)
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
        // movy by from super
        if (collider.canMove(x,y)) {
            this.x += x;
            this.y += y;
        }else{
            stop();
        }
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        collider.draw(batch,parentAlpha)
        super.draw(batch, parentAlpha)
    }

    // cleanup colllider
    override fun remove(): Boolean {
        collider.remove()
        return super.remove()
    }

    override val shape : Shape2D
        get() {
            val size = 1f
            return Rectangle(centerX-size*0.5f, centerY-size*0.5f-0.7f, size, size)
        }
}