package com.magma.engine.collision

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Queue
import com.badlogic.gdx.utils.Timer
import com.badlogic.gdx.utils.viewport.Viewport
import com.magma.engine.MagmaGame
import com.magma.engine.stages.ViewportContext
import com.magma.engine.utils.MagmaMath

open class CollisionStage : Stage(ViewportContext.view, MagmaGame.spriteBatch) {
    private val senders // a huge set of all the 'Triggerable' annotated actors or actors implementing
            : HashSet<Actor> = HashSet()

    // TriggerListener that need to interact with each other
    private val receivers: HashSet<Actor> = HashSet()
    private val removeQueue: Queue<Actor> = Queue()
    private var printDebug = false

    init {
        Timer.schedule(object : Timer.Task() {
            override fun run() {
                Gdx.app.log("CollisionStage", "senders: " + senders.size + " listeners: " + receivers.size)
            }
        }, 2f, 10f)
    }

    override fun act(delta: Float) {
        super.act(delta)
        // remove dead objects
        while (removeQueue.notEmpty()) {
            val dead = removeQueue.removeFirst()
            senders.remove(dead)
            receivers.remove(dead)
        }

        // check collision between senders and receivers
        for (sender in senders) {
            val senderShape = MagmaMath.extractShape(sender)
            // check if dead
            if (!sender.hasParent()) {
                removeQueue.addLast(sender)
                continue
            }
            for (receiver in receivers) {
                // check if dead
                if (!receiver.hasParent()) {
                    removeQueue.addLast(receiver)
                    continue
                }
                val receiverShape = MagmaMath.extractShape(receiver)
                if (MagmaMath.shapesOverlap(senderShape, receiverShape)) {
                    if (printDebug) {
                        println(sender.javaClass.simpleName + " --> " + receiver.toString())
                    }
                    (receiver as TriggerListener).onTriggered(sender)
                }
            }
        }
    }

    override fun addActor(actor: Actor) {
        if (actor is Group) {
            for (a in actor.children) {
                registerActor(actor)
            }
        }
        registerActor(actor)
        super.addActor(actor)
    }

    fun registerActor(actor: Actor) {
        if (actor is Triggered) {
            senders.add(actor)
        } else if (actor is TriggerListener) {
            receivers.add(actor)
        }
    }

    fun unregisterActor(actor: Actor) {
        senders.remove(actor)
        receivers.remove(actor)
    }

    override fun toString(): String {
        return "senders: " + senders.size + " listeners: " + receivers.size
    }
}