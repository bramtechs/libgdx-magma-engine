package com.magma.engine.chars

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.GridPoint2
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.utils.Queue
import com.magma.engine.debug.MagmaLogger

class CharacterParty(leader: Character) : Action() {
    private val members: Queue<CharacterMover> = Queue()
    private val trail: Queue<GridPoint2> = Queue()

    init {
        leader.addAction(this)
    }

    fun join(newChar: Character) {
        // inject characterMover
        val mover = CharacterMover(newChar.entity)
        newChar.addAction(mover)
        members.addLast(mover)
        Gdx.app.log("CharacterParty", newChar.javaClass.simpleName + " joined the party!")
    }

    fun joinFirst(newChar: Character) {
        // inject characterMover
        val mover = CharacterMover(newChar.entity)
        newChar.addAction(mover)
        members.addFirst(mover)
        Gdx.app.log("CharacterParty", newChar.javaClass.simpleName + " joined the party in front!")
    }

    fun leave(mover: CharacterMover) {
        MagmaLogger.log(this, mover.actor.javaClass.simpleName + " left the party!")
        members.removeValue(mover, false)
        mover.stop()
    }

    fun leave(curChar: Character) {
        val it: Iterator<CharacterMover> = members.iterator()
        while (it.hasNext()) {
            val mover = it.next()
            if (mover.actor === curChar) {
                break
            }
        }
    }

    /**
     *  Everyone in the party leaves
     */
    fun disband() {
        val it: Iterator<CharacterMover> = members.iterator()
        while (it.hasNext()) {
            val mover = it.next()
            leave(mover)
        }
    }

    override fun act(delta: Float): Boolean {
        // check if moved
        val x = getActor().x.toInt()
        val y = getActor().y.toInt()
        while (trail.size < members.size) {
            trail.addLast(GridPoint2())
        }
        val head = trail.first()
        if (head.x != x || head.y != y) {
            // player moved
            trail.addFirst(GridPoint2(x, y))

            // move all members
            for (i in 0 until trail.size - 1) {
                val pos = trail[i]
                members[i].target(pos)
            }
        }
        while (trail.size > members.size) {
            trail.removeLast()
        }
        return false
    }

    override fun toString(): String {
        var text = "CharacterParty: "
        if (trail.isEmpty) {
            text += "(no pos)"
            return text
        }
        for (i in 0 until trail.size) {
            val pos = trail[i]
            text += " > $pos"
            if (i < members.size) {
                text += " " + members[i].actor.javaClass.simpleName
            }
        }
        return text
    }
}