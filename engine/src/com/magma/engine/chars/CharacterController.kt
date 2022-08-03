package com.magma.engine.chars

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.Action
import com.magma.engine.entities.Entity
import com.magma.engine.input.GameInput
import com.magma.engine.ui.dialog.Dialog
import com.magma.engine.utils.Time
import java.lang.IllegalArgumentException

/**
 * Makes an actor movable with the keyboard. Default keys are WASD but can be
 * changed in keybindings.json Designed for attaching to Characters
 *
 * @param entity   Entity possessing the character
 * @param diagonal Can the entity move in 8 directions (diagonal) or just 4.
 */
class CharacterController(private val entity: Entity, private val diagonal: Boolean = false) : Action() {

    var isMoving = false
        private set

    private val pressTimes = LongArray(4)

    enum class Direction {
        Up, Down, Left, Right, None
    }

    override fun act(delta: Float): Boolean {
        // stop the player from moving when dialog is playing
        if (Dialog.isSpeaking()) {
            return false
        }
        val sp = entity.stats.speed * delta

        // The character will move in the direction of the youngest pressed key.
        val allKeys = intArrayOf(key_up, key_down, key_left, key_right)
        for (i in allKeys.indices) {
            // pressing keys
            if (Gdx.input.isKeyJustPressed(allKeys[i])) {
                pressTimes[i] = Time.getTimeMillis()
                //MagmaLogger.log("Pressed",allKeys[i]);
            }

            //releasing
            if (!Gdx.input.isKeyPressed(allKeys[i])) {
                pressTimes[i] = Long.MIN_VALUE
            }
        }

        // get the most recent pressed key of allKeys[]
        var largest = 0
        for (i in allKeys.indices) {
            if (pressTimes[i] >= pressTimes[largest]) {
                largest = i
            }
        }
        if (pressTimes[largest] > 0) {
            isMoving = true
            when (largest) {
                0 -> getActor().moveBy(0f, sp)
                1 -> getActor().moveBy(0f, -sp)
                2 -> getActor().moveBy(-sp, 0f)
                3 -> getActor().moveBy(sp, 0f)
                else -> { throw IllegalArgumentException("Unknown direction")}
            }
        } else {
            // no key being pressed
            isMoving = true
        }
        return false
    }

    override fun toString(): String {
        return "CharacterController sp:" + entity.stats.speed + " moving? " + isMoving
    }

    companion object {
        val key_up = GameInput.registerKey("move_up", Input.Keys.W) // 0
        val key_down = GameInput.registerKey("move_down", Input.Keys.S) // 1
        val key_left = GameInput.registerKey("move_left", Input.Keys.A) // 2
        val key_right = GameInput.registerKey("move_right", Input.Keys.D) // 3
    }
}