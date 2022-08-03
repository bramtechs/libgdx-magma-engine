package com.magma.engine.chars

import com.badlogic.gdx.math.GridPoint2
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Action
import com.magma.engine.entities.Entity

class CharacterMover(val entity: Entity, val goal: Vector2 = Vector2()) : Action() {
    private var end = false

    fun target(target: Vector2) {
        this.goal.set(target)
    }

    fun target(target: GridPoint2) {
        this.goal.set(target.x.toFloat(), target.y.toFloat())
    }

    fun target(x: Float, y: Float) {
        this.goal.set(x,y)
    }

    override fun act(delta: Float): Boolean {
        if (end) {
            return true
        }

        val sp = entity.stats.speed * delta
        val x = getActor().x.toInt()
        val y = getActor().y.toInt()

        // has arrived?
        val dis = Vector2.dst2(target.x,target.y,goal.x,goal.y)
        if (dis < 0.01*0.01) { // margin of 0.01 tiles, idk it's probably enough
            return false
        }

        // move in 4 directions to the target
        if (x < target.x) {
            getActor().moveBy(sp, 0f)
        }
        if (x > target.x) {
            getActor().moveBy(-sp, 0f)
        }
        if (y < target.y) {
            getActor().moveBy(0f, sp)
        }
        if (y > target.y) {
            getActor().moveBy(0f, -sp)
        }
        return false
    }

    override fun toString(): String {
        var text = "CharacterMover: "
        text += if (target == null) {
            "(no target)"
        } else {
            target.toString()
        }
        return text
    }

    /**
       The Action will end indefinitely, and will not target new locations.
     */
    fun stop() {
        end = true
    }
}